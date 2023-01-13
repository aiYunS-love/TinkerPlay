package com.baiyx.wfwbitest.JdbcConnectionPool;

/**
 * @Author: 白宇鑫
 * @Date: 2022-8-11 下午 04:14
 * @Description:
 */
public class PoolManager {
    /**
     * 静态内部类实现连接池的单例
     * */
    private static class CreatePool{
        private static JdbcPool pool = new JdbcPool();
    }

    public static JdbcPool getInstance(){
        return CreatePool.pool;
    }
}
