package com.baiyx.wfwbitest.Flink;

import com.baiyx.wfwbitest.Dao.UserDao;
import com.baiyx.wfwbitest.Utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.stereotype.Component;

/**
 * @Author: 白宇鑫
 * @Date: 2023年4月27日, 0027 下午 6:51:13
 * @Description: 自定义sink 交由spring管理 处理变更数据
 */
@Component
@Slf4j
public class DataChangeSink implements SinkFunction<DataChangeInfo> {

    @Override
    public void invoke(DataChangeInfo dataChangeInfo, Context context) {
        log.info("收到变更原始数据：{}", dataChangeInfo);
        // TODO 开始处理你的数据吧
        System.out.println("开始处理你的数据");
    }

}
