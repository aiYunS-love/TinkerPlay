package com.baiyx.wfwbitest.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: baiyx
 * @Date: 2023年3月16日, 0016 下午 1:59:46
 * @Description: 搜索人员管理Controller
 */
@Controller
@Tag(name = "EsUserController", description = "搜索人员管理")
@RequestMapping("/esUser")
public class EsUserController {

//    @Autowired
//    private EsUserService esUserService;
//
//    @Operation(summary = "导入所有数据库中人员信息到ES")
//    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult<Integer> importAllList() {
//        int count = esUserService.importAll();
//        return CommonResult.success(count);
//    }
//
//    @Operation(summary = "根据id删除人员")
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<Object> delete(@PathVariable Long id) {
//        esUserService.delete(id);
//        return CommonResult.success(null);
//    }
//
//    @Operation(summary = "根据id批量删除人员信息")
//    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids) {
//        esUserService.delete(ids);
//        return CommonResult.success(null);
//    }
//
//    @Operation(summary = "根据id创建人员")
//    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult<EsUser> create(@PathVariable Long id) {
//        EsUser esProduct = esUserService.create(id);
//        if (esProduct != null) {
//            return CommonResult.success(esProduct);
//        } else {
//            return CommonResult.failed();
//        }
//    }
//
//    @Operation(summary = "根据性别简单搜索")
//    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<CommonPage<EsUser>> search(@RequestParam(required = false) String sex,
//                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
//                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
//        Page<EsUser> esUserPage = esUserService.searchBySex(sex, pageNum, pageSize);
//        return CommonResult.success(CommonPage.restPage(esUserPage));
//    }
//
//    @Operation(summary = "根据姓名或关键字简单搜索")
//    @RequestMapping(value = "/search/searchNameOrKeyword", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<CommonPage<EsUser>> searchNameOrKeyword(@RequestParam(required = false) String keyword,
//                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
//                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
//        Page<EsUser> esUserPage = esUserService.searchByNameOrKeywords(keyword, pageNum, pageSize);
//        return CommonResult.success(CommonPage.restPage(esUserPage));
//    }
//
//    @Operation(summary = "根据姓名或证件号或关键字简单搜索")
//    @RequestMapping(value = "/search/searchByNameOrIDCARDOrKeywords", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<CommonPage<EsUser>> searchByNameOrCARDOrKeywords(@RequestParam(required = false) String keyword,
//                                                                @RequestParam(required = false, defaultValue = "0") Integer pageNum,
//                                                                @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
//        Page<EsUser> esUserPage = esUserService.searchByNameOrCARDOrKeywords(keyword, pageNum, pageSize);
//        return CommonResult.success(CommonPage.restPage(esUserPage));
//    }
//
//    @Operation(summary = "关键字搜索")
//    @RequestMapping(value = "/search/searchKeyword", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<CommonPage<EsUser>> searchKeyword(@RequestParam(required = false) String keyword,
//                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
//                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
//        Page<EsUser> esUserPage = esUserService.searchByKeyword(keyword, pageNum, pageSize);
//        return CommonResult.success(CommonPage.restPage(esUserPage));
//    }

}
