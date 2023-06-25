package com.baiyx.wfwbitest.Listener;

import com.baiyx.wfwbitest.Dao.ISysJobRepository;
import com.baiyx.wfwbitest.Entity.SysJobPO;
import com.baiyx.wfwbitest.Flink.DataChangeInfo;
import com.baiyx.wfwbitest.Flink.DataChangeSink;
import com.baiyx.wfwbitest.Flink.MysqlDeserialization;
import com.baiyx.wfwbitest.TimedTask.CronTaskRegistrar;
import com.baiyx.wfwbitest.TimedTask.SchedulingRunnable;
import com.baiyx.wfwbitest.TimedTask.SysJobRunner;
import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2023年4月27日, 0027 下午 6:37:10
 * @Description: MySQL CDC变更监听器
 * 由于项目中添加了mysql的监听类MysqlEventListener,导致程序启动只执行实现ApplicationRunner接口的run方法,不执行实现CommandLineRunner接口的run方法
 * 所以把程序启动要扫描的定时任务方法与监听mysql的方法都放到实现ApplicationRunner接口的run方法里面.
 */
@Component
@Slf4j
public class MysqlEventListener implements ApplicationRunner{

    private static final Logger logger = LoggerFactory.getLogger(MysqlEventListener.class);

    @Autowired
    private ISysJobRepository sysJobRepository;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    /**
     * CDC数据源配置
     */
    @Value("${CDC.DataSource.host}")
    private String host;
    @Value("${CDC.DataSource.port}")
    private String port;
    @Value("${CDC.DataSource.database}")
    private String database;
    @Value("${CDC.DataSource.tableList}")
    private String tableList;
    @Value("${CDC.DataSource.username}")
    private String username;
    @Value("${CDC.DataSource.password}")
    private String password;

    private final DataChangeSink dataChangeSink;

    public MysqlEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        // 程序启动先执行扫描定时任务的方法
        this.scanTimedTask();
        log.info("开始启动Flink CDC获取ERP变更数据......");
        // 流式数据处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 批处理环境
        // ExecutionEnvironment env2 = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DebeziumSourceFunction<DataChangeInfo> dataChangeInfoMySqlSource = buildDataChangeSource();
        DataStream<DataChangeInfo> streamSource = env
                .addSource(dataChangeInfoMySqlSource, "mysql-source")
                .setParallelism(1);
        streamSource.addSink(dataChangeSink);
        env.execute("mysql-cdc");
    }

    /**
     * 构造CDC数据源
     */
    private DebeziumSourceFunction<DataChangeInfo> buildDataChangeSource() {
        String[] tables = tableList.replace(" ", "").split(",");
        return MySqlSource.<DataChangeInfo>builder()
                .hostname(host)
                .port(Integer.parseInt(port))
                .databaseList(database)
                .tableList(tables)
                .username(username)
                .password(password)
                /**initial初始化快照,即全量导入后增量导入(检测更新数据写入)
                 * latest:只进行增量导入(不读取历史变化)
                 * timestamp:指定时间戳进行数据导入(大于等于指定时间错读取数据)
                 */
                .startupOptions(StartupOptions.latest())
                .deserializer(new MysqlDeserialization())
                .serverTimeZone("GMT+8")
                .build();
    }

    // 扫描定时任务方法
    public void scanTimedTask() {
        // 初始加载数据库里状态为正常的定时任务
        // List<SysJobPO> jobList = sysJobRepository.getSysJobListByStatus(SysJobStatus.NORMAL.ordinal());
        List<SysJobPO> jobList = sysJobRepository.getSysJobListByStatus(1);
        // 程序启动时,一次性加载数据库里面状态为1的正常的定时任务
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (SysJobPO job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }

            logger.info("定时任务已加载完毕...");
        }
    }

}
