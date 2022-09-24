package com.baiyx.wfwbitest.redis;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.baiyx.wfwbitest.util.SpringContextUtils;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
/**
 * Redis管理器 自定义redis配置
 *
 * @author 小白学习
 */
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
    /**
     * 默认Redis全局配置。（30分钟超时）
     *
     * @param redisConnectionFactory
     * @return
     */
    @Primary
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(instanceConfig(30L)).build();
    }
    /**
     * 永不超时
     */
    @Bean
    public RedisCacheManager noExpire(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(instanceConfig(0L)).build();
    }
    /**
     * 1分钟超时
     * 用于测试
     */
    @Bean
    public RedisCacheManager expire1min(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(instanceConfig(1L)).build();
    }
    /**
     * 2小时超时
     */
    @Bean
    public RedisCacheManager expire2h(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(instanceConfig(60 * 2L)).build();
    }
    /**
     * 一天超时
     */
    @Bean
    public RedisCacheManager expire1day(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(instanceConfig(60 * 24L)).build();
    }
    /**
     * 通用配置
     *
     * @param ttl
     *            超时时间（分钟）
     */
    private RedisCacheConfiguration instanceConfig(Long ttl) {
        // 缓存配置对象
        return RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存的默认超时时间
                .entryTtl(Duration.ofMinutes(ttl))
                // 如果是空值，不缓存（不建议设置，防止缓存穿透）
                // .disableCachingNullValues()
                // 设置key序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer(StandardCharsets.UTF_8)))
                // 设置value序列化器（这里使用阿里巴巴的Fastjson格式化工具）
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));
    }

    /*
    * @Author: 白宇鑫 
    * @Description: 自定义redis缓存的key
    * @Date: 2022-7-13 下午 03:37
    * @Param: 
     * @param null 
    * @return:  
    */
    @Override
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                //自定义缓存key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                //String className = o.getClass().getName();
                //className = className.substring(className.lastIndexOf(".")+1);
                //sb.append(className + "_");

                //追加方法名
                sb.append(method.getName());
                sb.append(":");

                //遍历参数并且追加
                for (Object obj : objects) {
                    if(obj instanceof java.util.Date){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String strObj = sdf.format(obj).toString().replace("-","");
                        sb.append(strObj + "_");
                    }else{
                        sb.append(obj.toString() + "_");
                    }
                }
                System.out.println("Redis缓存的Key : " + sb.toString());
                return sb.toString();
            }
        };
    }
}