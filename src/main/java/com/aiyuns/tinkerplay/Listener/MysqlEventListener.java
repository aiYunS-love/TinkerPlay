package com.aiyuns.tinkerplay.Listener;

import com.aiyuns.tinkerplay.Config.FlinkCDCConfig;
import com.aiyuns.tinkerplay.Controller.Service.ServiceImpl.Dao.ISysJobRepository;
import com.aiyuns.tinkerplay.Entity.SysJobPO;
import com.aiyuns.tinkerplay.Flink.DataChangeInfo;
import com.aiyuns.tinkerplay.Flink.DataChangeSink;
import com.aiyuns.tinkerplay.Flink.MysqlDeserialization;
import com.aiyuns.tinkerplay.TimedTask.CronTaskRegistrar;
import com.aiyuns.tinkerplay.TimedTask.SchedulingRunnable;
import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: aiYunS
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
    @Resource
    FlinkCDCConfig flinkCDCConfig;
    @Value("${timedtask.enabled}")
    private boolean timedtaskenabled = true;
    private final DataChangeSink dataChangeSink;

    public MysqlEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        // 程序启动先执行扫描定时任务的方法
        if (timedtaskenabled) {
            this.scanTimedTask();
        }
        if (flinkCDCConfig.isEnabled()){
            log.info("开始启动Flink CDC获取ERP变更数据......");
            // 流式数据处理环境
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            // 批处理环境
            // ExecutionEnvironment env2 = ExecutionEnvironment.getExecutionEnvironment();
            DebeziumSourceFunction<DataChangeInfo> dataChangeInfoMySqlSource = buildDataChangeSource();
            DataStream<DataChangeInfo> streamSource = env
                    .addSource(dataChangeInfoMySqlSource)
                    .name("mysql-source")
                    .setParallelism(1);
            streamSource.addSink(dataChangeSink);
            env.execute("mysql-cdc");
        }
    }

    /**
     * 构造CDC数据源
     */
    private DebeziumSourceFunction<DataChangeInfo> buildDataChangeSource() {
        return MySqlSource.<DataChangeInfo>builder()
                .hostname(flinkCDCConfig.getHostname())
                .port(flinkCDCConfig.getPort())
                .databaseList(flinkCDCConfig.getDatabase())
                .tableList(flinkCDCConfig.getTableList())
                .username(flinkCDCConfig.getUsername())
                .password(flinkCDCConfig.getPassword())
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
