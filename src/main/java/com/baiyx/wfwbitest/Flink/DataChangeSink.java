package com.baiyx.wfwbitest.Flink;

import com.baiyx.wfwbitest.Dao.UserDao;
import com.baiyx.wfwbitest.Utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.springframework.stereotype.Component;

/**
 * @Author: 白宇鑫
 * @Date: 2023年4月27日, 0027 下午 6:51:13
 * @Description: 自定义sink 交由spring管理 处理变更数据
 */
@Component
@Slf4j
public class DataChangeSink extends RichSinkFunction<DataChangeInfo> {

    private static final long serialVersionUID = -74375380912179188L;

    private UserDao userDao;

    /**
     * 在open()方法中动态注入Spring容器的类
     * 在启动SpringBoot项目是加载了Spring容器，其他地方可以使用@Autowired获取Spring容器中的类；
     * 但是Flink启动的项目中，默认启动了多线程执行相关代码，导致在其他线程无法获取Spring容器，
     * 只有在Spring所在的线程才能使用@Autowired，故在Flink自定义的Sink的open()方法中初始化Spring容器
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        userDao = SpringContextUtils.getBean(UserDao.class);
    }

    @Override
    public void invoke(DataChangeInfo dataChangeInfo, Context context) {
        log.info("收到变更原始数据：{}", dataChangeInfo);
        // TODO 开始处理你的数据吧
    }

}
