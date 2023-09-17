package com.baiyx.wfwbitest.Config;

import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.event.DataSourceInitEvent;
import com.baomidou.dynamic.datasource.event.EncDataSourceInitEvent;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.YmlDynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: baiyx
 * @Date: 2023年9月7日, 0007 上午 10:59:40
 * @Description: 单独写个类获取baomidou多数据源存储好的数据源信息
 */
@Configuration
@Component("YmlDataSourceConfig")
@RequiredArgsConstructor
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class YmlDataSourceConfig {

    private final DynamicDataSourceProperties properties;

    public DynamicDataSourceProperties getProperties() {
        return this.properties;
    }

}
