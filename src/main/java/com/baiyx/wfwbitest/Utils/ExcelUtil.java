package com.baiyx.wfwbitest.Utils;

import com.alibaba.excel.EasyExcel;
import com.baiyx.wfwbitest.Entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-1 下午 05:32
 * @Description: 导出excel工具类
 */
public class ExcelUtil {
    public static void download(HttpServletResponse response, Class t, List<User> list) throws IOException, IllegalAccessException,InstantiationException {
        response.setContentType("application/vnd.ms-excel");// 设置文本内省
        response.setCharacterEncoding("utf-8");// 设置字符编码
        response.setHeader("Content-disposition", "attachment;filename=projbase.xlsx"); // 设置响应头
        EasyExcel.write(response.getOutputStream(), t).sheet("sheet").doWrite(list); //用io流来写入数据
    }
}