package com.baiyx.wfwbitest.controller;

import com.baiyx.wfwbitest.algorithm.RecursiveAlgorithm;
import com.baiyx.wfwbitest.customAnnotations.*;
import com.baiyx.wfwbitest.entity.QueryRequestVo;
import com.baiyx.wfwbitest.entity.R;
import com.baiyx.wfwbitest.entity.ResultMsg;
import com.baiyx.wfwbitest.entity.User;
import com.baiyx.wfwbitest.service.UserService;
import com.baiyx.wfwbitest.util.ExcelUtil;
import com.baiyx.wfwbitest.util.RowConvertColUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author: 白宇鑫
 * @Date: 2021/6/30 上午 11:35
 * @Description: 控制器-入口
 */
@RestController
@Component("TestTimedTask")
@RequestMapping(value ="userController",produces = "application/json;charset=UTF-8")
@EnableScheduling // 1.开启定时任务
@EnableAsync // 2.开启多线程
public class UserController {

    @Autowired
    UserService UserService;
    
    /**
    * @Author: 白宇鑫 
    * @Description: 测试Aop层面统一打印自定义日志格式
     *              测试RedisCacheConfig缓存管理器
     *              测试@WebLog自定义日志打印的注解
     *              测试自定义定时任务功能
     *              测试Springboot框架自带的定时任务注解 @EnableScheduling @EnableAsync @Async @Scheduled
    * @Date: 2021/6/30 上午 11:43
    * @Param:  
    * @return: java.util.List<com.baiyx.wfwbitest.entity.user> 
    */
    // @Async
    // @Scheduled(fixedDelay = 30000)  //间隔1秒
    @WebLog(description = "查询所有")
    @RequestMapping(value = "findAll")
    public List<User> findAll(){
        return UserService.findAll();
    }

    /***
     * @Author: 白宇鑫
     * @Description: 使用insertOne方法册测试延时双删;因为UserServiceImpl使用findByName方法先查询数据是否存在,
     *               所以UserController的insertOne入口处为@Decrypt(description = "findByName")
     *               会模糊寻找redis缓存key为findByName的缓存
     * @Date: 2022-9-27 下午 05:01
     * @param user
     * @return: com.baiyx.wfwbitest.entity.R
     */
    @WebLog(description = "插入一条")
    @RequestMapping(value = "insertOne",method= RequestMethod.POST,produces = "application/json")
    @Decrypt(description = "findByName")
    @ClearAndReloadCache(name = "findByName")
    public R insertOne(@RequestBody User user){
        return R.ok("ok",UserService.insertOne(user));
    }

    @WebLog(description = "根据名字删除")
    @RequestMapping(value = "deleteByName",method = RequestMethod.GET)
    public void deleteByName(@RequestBody QueryRequestVo queryRequestVo){
        UserService.deleteByName(queryRequestVo);
    }

    @WebLog(description = "更新一条数据")
    @RequestMapping(value = "updateOne",method = RequestMethod.POST)
    @ClearAndReloadCache(name = "findById") //依旧测试延时双删
    public void updateOne(@RequestBody QueryRequestVo queryRequestVo){
        UserService.updateOne(queryRequestVo);
    }

    /*
    * @Author: 白宇鑫
    * @Description: @Nullable 注解可以标注在方法、字段、参数之上，表示对应的值可以为空
    * @Date: 2022-9-15 上午 10:38
    * @Param:
     * @param null
    * @return: User
    */

    @WebLog(description = "根据ID查询")
    @RequestMapping(value = "findById",method = RequestMethod.GET)
    @Encrypt(description = "根据ID查询加密查询结果")
    public R findById(@RequestBody @NullDisable QueryRequestVo queryRequestVo){
        return R.ok("ok",UserService.findById(queryRequestVo));
    }

    /*
    * @Author: 白宇鑫
    * @Description: 测试HttpUtil和MacUtil工具类
    * @Date: 2022-9-15 上午 10:32
    * @Param: QueryRequestVo
    * @return: User
    */

    @WebLog(description = "根据名字查询")
    @RequestMapping(value = "findByName",method = RequestMethod.GET)
    public User findByName(@RequestBody QueryRequestVo queryRequestVo) {
        return UserService.findByName(queryRequestVo);
    }

    /*
    * @Author: 白宇鑫
    * @Description: 测试BI组件与后端交互
    *               @CrossOrigin注解用于解决跨域问题
    * @Date: 2022-9-15 上午 10:39
    * @Param: QueryRequestVo
    * @return: List<User>
    */

    @WebLog(description = "根据时间段查询")
    @RequestMapping(value = "findByTime",method = RequestMethod.POST)
    @CrossOrigin(value = "http://localhost:10104") // 该注解解决跨域问题
    public List<User> findByTime(@RequestBody QueryRequestVo queryRequestVo) {
            List<User> users = UserService.findByTime(queryRequestVo);
            return users;
    }

    /*
    * @Author: 白宇鑫
    * @Description: 测试行转列RowConvertColUtil工具类
    * @Date: 2022-9-15 上午 10:40
    * @Param: QueryRequestVo
    * @return: RowConvertColUtil.ConvertData
    */

    @WebLog(description = "行转列Test")
    @RequestMapping(value = "RowConvertCol",method = RequestMethod.POST)
    public RowConvertColUtil.ConvertData RowConvertCol(@RequestBody QueryRequestVo queryRequestVo) {
        RowConvertColUtil.ConvertData users = UserService.RowConvertCol(queryRequestVo);;
        return users;
    }
    
    /*
    * @Author: 白宇鑫 
    * @Description: 测试批量插入功能和单表插入的效率
    *               @Param的作用就是给参数命名，比如在mapper里面某方法A（int id），当添加注解后A（@Param("userId") int id），
    *               也就是说外部想要取出传入的id值，只需要取它的参数名userId就可以了。将参数值传如SQL语句中，通过#{userId}进行取值给SQL的参数赋值。
    * @Date: 2022-9-15 上午 10:41
    * @Param: List<User>
    * @return: void
    */
    
    @WebLog(description = "批量插入")
    @RequestMapping(value = "insertAll",method = RequestMethod.POST)
    public void insertAll(@Param("User") @RequestBody List<User> userList) {
        UserService.insertAll(userList);
    }

    /*
    * @Author: 白宇鑫
    * @Description: 测试ExcelUtil工具类
    *               测试导出查询数据为Excel功能
    *               测试阿里开源工具包easyExcle
    * @Date: 2022-9-15 上午 10:44
    * @Param: HttpServletResponse
    * @return: String
    */

    //导出为Excel
    @WebLog(description = "导出为Excel")
    @RequestMapping("/downloadexcel")
    public String getExcel(HttpServletResponse response) throws IllegalAccessException, IOException,
            InstantiationException {
        List<User> list = UserService.findAll();
        ExcelUtil.download(response,User.class,list);
        return null;
    }

    @WebLog(description = "获取IP或MAC地址")
    @RequestMapping(value = "getIPorMACaddress")
    public Map getIPorMACaddress(HttpServletRequest request) {
        try {
            return UserService.getIPorMACaddress(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @WebLog(description = "消除转义")
    @RequestMapping(value = "removeESC", method = RequestMethod.POST)
    public ResultMsg removeESC() {
        return UserService.removeESC();
    }

    @WebLog(description = "递归_文件搜索")
    @RequestMapping(value = "recursion", method = RequestMethod.GET)
    public TreeSet recursion(String path, String searchContent) {
        //先行判断上一次查询的内容与本次查询的是否一致
        RecursiveAlgorithm.clean(searchContent);
        TreeSet<String> filePath = RecursiveAlgorithm.fileSearch(new File(path),searchContent);
        return filePath;
    }
}
