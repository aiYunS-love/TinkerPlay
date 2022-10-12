package com.baiyx.wfwbitest;

import com.baiyx.wfwbitest.algorithm.RecursiveAlgorithm;
import com.baiyx.wfwbitest.builderModel.JDBCConfig;
import com.baiyx.wfwbitest.builderModel.JDBCConfig.JDBCBuilder;
import com.baiyx.wfwbitest.dao.UserDao;
import com.baiyx.wfwbitest.entity.ExcelPOJO;
import com.baiyx.wfwbitest.entity.User;
import com.baiyx.wfwbitest.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 白宇鑫
 * @Date: 2022-7-7 下午 04:57
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class junitTest {
    @Autowired
    private UserDao userRepository;
    // private UserDaoTwo userRepository2;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void test1() throws Exception {
        // 创建1条记录
        //userRepository.insertOne(new User(100,"AAA","海南岛","男",simpleDateFormat.parse("1993-01-01")));
        long startTime1 = System.currentTimeMillis();
        List<User> users = userRepository.findAll();
        long endTime1 = System.currentTimeMillis();
        System.out.println("第一次查询耗时: " + (endTime1-startTime1)+"ms");

//        long startTime2 = System.currentTimeMillis();
//        User u2 = userRepository.findAll("AAA");
//        long endTime2 = System.currentTimeMillis();
//        System.out.println("第二次查询：" + u2 + " 耗时: " + (endTime1-startTime1)+"ms");
    }

    @Test
    public void test2() throws Exception {
        for(int i=101;i<=10100;i++){
            userRepository.insertOne(new User(i,"AAA","海南岛","男",simpleDateFormat.parse("1993-01-01")));
        }
    }

    @Test
    public void test3(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = simpleDateFormat.parse("1993-01-01");
            d2 = simpleDateFormat.parse("1995-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<User> u = userRepository.findByTime(d1,d2);
        System.out.println("默认形式: " + u);
        String[] fixedColumn = {"id","username","address","sex","birthday"};
        String[] fixedColumnName = {"序号","姓名","地址","性别","生日"};
        try {
            RowConvertColUtil.ConvertData lists = RowConvertColUtil.doConvertReturnObj(u, "username", fixedColumn, "sex", true,fixedColumnName,"0");
            System.out.println("行转列后: " + lists);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //构建者模式构建复杂对象之JDBC配置类对象
    @Test
    public void test4(){
        JDBCConfig jdbcConfig = new JDBCBuilder().setHost("192.168.119.129")
                .setPort(3306)
                .setUser("root")
                .setPassword("19930218")
                .setDatabase("heima")
                .setCharacterEncoding("utf8")
                .setInitialTimeout(6000)
                .JDBCBuild();
        System.out.println("jdbcConfig = " + jdbcConfig);
    }

    //测试JDBC工具类JDBCUtils
    @Test
    public void test5() throws Exception {

    }

    //测试JDBC工具类DBUtil读取db.properties配置文件创建jdbc连接
    @Test
    public void test6() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User s = null;
        conn = DBUtil.getConnection();
        String sql = "select * from user";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {// 当结果集中有就一直进行执行
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String birthday = rs.getString("birthday");
            String address = rs.getString("address");
            String sex = rs.getString("sex");
            // 只要有数据每读取一行就创建一个Student对象
            s = new User();
            // 将从表中获取的数据存储到一个Student类对象中
            s.setId(id);
            s.setUsername(username);
            s.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
            s.setAddress(address);
            s.setSex(sex);
            System.out.println("===========================================================================");
            System.out.println(s);
            System.out.println("===========================================================================");
        }
        DBUtil.release(conn,ps,null);
    }

    //测试 通过AOP 进行日志打印
    @Test
    public void test7() throws Exception {
        User u = userRepository.findById(67);
        System.out.println(u);
    }

    //测试resolveTokenUtil工具类解析token
    @Test
    public void test8() {
        Map<String,Object> o = ResolveTokenUtil.resolveToken("Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnRfaWQiOiIzMyIsInVzZXJfdGVsIjoiIiwidXNlcl9uYW1lIjoicXQtaHkxIiwidG9rZW5fcHdkX3N0cm9uZ19mbGFnIjoiMSIsImF1dGhvcml0aWVzIjpbIkRFTEVURSMvKioiLCJQT1NUIy8qKiIsIkdFVCMvKioiLCJQVVQjLyoqIl0sInVzZXJfaWRfbnVtYmVyIjoiIiwiY2xpZW50X2lkIjoiZ2lzcS02MmM2NGNkZS02NmZlLTExZTktOTM3YS0yYzRkNTRmMDFiYTQiLCJ1c2VyX2tpbmQiOjEsInRlbmFudF91aWQiOiI3MzNlNTI2ZS1jZDI5LTExZWMtOWU3OC0wMjQyMTBkOGE5ZjMiLCJ1c2VyX2lkIjoiOTkwY2QxMTItMDc3YS00ZjBiLThkYTktNjZlMjQ5Mzk1OTViIiwic2NvcGUiOlsiYWxsIl0sInVzZXJfbmlja25hbWUiOiLkvZXotZ8iLCJ0b2tlbl9wd2Rfb3ZlcmR1ZV9mbGFnIjoiMSIsImV4cCI6MTY1OTk3NDY1MSwianRpIjoiNTYxNzZjYTgtMWY2Ni00MzUzLWEwMDktZTVlMWI1NzJhZmI5In0.mUaeLwj6HdLtyMy3hbducirjAGX8_kvOR-Qhlbu6dTqf_QqqefWhYKOxAfixjza39rKkJtF6rmad3zrmsxJD_FMnLbO6DcLuJ9CaluQ2liAsXtjFdd1dAfnNLfK5ZqKWjCc0GlaTCooPu6f0ajxT3kGfYXFyWm8Pj8Pi0CDJ8durd_iJZMfCmHwEZ-bBKLbPNnC1BnIqOdHwiE6-RyYzxnkc7m2_gBkFCBSioASUaWI7VAGcBE-9Wf29di6_WhqPgV0-uEJZAGtTZ7ih4sq5SJTqnGUUQHyOvdz77RIKaeSyRAY4aCG_A1s5ziYcq677qEiVMwzfn2kDxKyi8EWk2g");
        System.out.println("=========================================================");
        System.out.println("测试解析Token = " + o);
        System.out.println("=========================================================");
    }

    //测试单表批量插入,springboot框架的性能
    //结论: 10万条数据3s,100万条数据约40s
    @Test
    public void test9() {
        long startTime = System.currentTimeMillis();
        List<User> users = new ArrayList<User>();
        for(int i=7;i<1000007;i++){
         User user = new User();
         user.setId(i);
         user.setUsername("test" + i);
         if(i%2 == 0){
            user.setSex("男");
         }else{
             user.setSex("女");
         }
         user.setAddress("地址" + i);
            try {
                user.setBirthday(simpleDateFormat.parse("2020-08-26"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            users.add(user);
        }
        userRepository.insertAll(users);
        long endTime = System.currentTimeMillis();
        System.out.println(" 耗时: " + (endTime-startTime)/1000+"s");
    }

    //测试readTXTUtil工具类
    @Test
    public void test10() {
        System.out.println(ReadTXTtoJsonObjUtil.readTXTtoObj(""));
    }

    @Test
    public void test11(){
        //TreeSet<String> filePath = RecursiveAlgorithm.fileSearch(new File("D:\\Users\\lenovo\\Desktop"),"nginx_BI.conf");
        TreeSet<String> filePath = RecursiveAlgorithm.fileSearch(new File("E:\\"),"config.js");
        System.out.println("该文件路径在: " +filePath);
    }

    @Test
    public void test12(){
        /** 方法一
         * fileName:Excel文件路径
         * StatrRow：读取的开始行数（默认填0）
         * EndRow：读取的结束行数（填-1为全部）
         * ExistTop:是否存在头部（如存在则读取数据时会把头部拼接到对应数据作为KEY，若无则KEY为当前列数）
         */
        List<List<Map<String,Object>> >result = ReadExcelUtil.ReadExcelByRC("D:\\Users\\lenovo\\Desktop\\每㎡大于10万.xlsx",0,-1,true,"-1");
        System.out.println(result.size());
        System.out.println(result);

        /**
         * 方法二
         * ReadExcelByPOJO(String fileName, int StatrRow, int EndRow, Class t)
         * fileName:Excel文件路径
         * StatrRow：读取的开始行数（默认填0）
         * EndRow：读取的结束行数（填-1为全部）
         * Class<T>：传过来的实体类类型
         */
        List<List<Object>> result2 = null;
        try {
            result2 = ReadExcelUtil.ReadExcelByPOJO("D:\\Users\\lenovo\\Desktop\\每㎡大于10万.xlsx",0,-1, ExcelPOJO.class,"-1");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println(result2.size());
        System.out.println(result2);
    }

      //测试springboot框架集成rabbitmq消息中间件
//    @Autowired
//    DirectProvider directProvider;
//    @Test
//    public void test10() throws InterruptedException {
//        for(int i=1;i<10000;i++){
//            directProvider.send(i);
//        }
//    }

      //测试多数据源配置
//    @Test
//    public void test9() {
//        List<User> users = userRepository2.findAllTwo();
//        System.out.println(users);
//    }
}
