package com.baiyx.wfwbitest.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baiyx.wfwbitest.Dao.ProjBaseDao;
import com.baiyx.wfwbitest.Entity.Projbase;
import com.baiyx.wfwbitest.Entity.ResultMsg;
import com.baiyx.wfwbitest.ServiceImpl.ProjBaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: 白宇鑫
 * @Date: 2022-12-5 16:30
 * @Description: 控制器
 */

@RestController
@RequestMapping(value ="ProjbaseController",produces = "application/json;charset=UTF-8")
@Api(tags = "ProjbaseController", description = "分页模块")
public class ProjbaseController {

    // 此处控制器层直接注入dao层,没有注入业务层
    @Autowired
    ProjBaseDao projBaseDao;

//    // 不做任何处理,常规方式读取数据,处理数据,写入数据
//    // @WebLog(description = "分析异常数据")
//    @RequestMapping(value = "AnalyzingAbnormalData",method= RequestMethod.POST,produces = "application/json")
//    public void AnalyzingAbnormalData(@RequestBody Map map) {
//        new ProjBaseServiceImpl(projBaseDao).readProjbase(map);
//    }

    @ApiOperation(value = "分页查询_数据量大")
    @GetMapping("findBypaging1")
    public PageInfo<Projbase> findByPaging1(@ApiParam("页码") Integer pageNum, @ApiParam("每页数量") Integer pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<Projbase> list = projBaseDao.selectProjbase1();
        PageInfo<Projbase> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "分页查询_数据量小")
    @GetMapping("findBypaging2")
    public PageInfo<Projbase> findByPaging2(@ApiParam("页码") Integer pageNum, @ApiParam("每页数量") Integer pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<Projbase> list = projBaseDao.selectProjbase2();
        PageInfo<Projbase> pageInfo = new PageInfo<>(list);
        System.out.println("当前页：" + pageInfo.getPageNum());
        System.out.println("每页显示的数量：" + pageInfo.getPageSize());
        System.out.println("当前页的数量：" + pageInfo.getSize());
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        System.out.println("结果集:" + pageInfo.getList());
        System.out.println("第一页:" + pageInfo.getNavigateFirstPage());
        System.out.println("前一页:" + pageInfo.getPrePage());
        System.out.println("下一页:" + pageInfo.getNextPage());
        System.out.println("最后一页:" + pageInfo.getNavigateLastPage());
        System.out.println("是否为第一页：" + pageInfo.isIsFirstPage());
        System.out.println("是否为最后一页：" + pageInfo.isIsLastPage());
        System.out.println("是否有前一页：" + pageInfo.isHasPreviousPage());
        System.out.println("是否有下一页：" + pageInfo.isHasNextPage());
        System.out.println("导航页码数" + pageInfo.getNavigatePages());
        System.out.println("所有导航页号" + Arrays.toString(pageInfo.getNavigatepageNums()));
        return pageInfo;
    }
}
