package com.aiyuns.tinkerplay.Utils;

import com.alibaba.excel.EasyExcel;
import com.aiyuns.tinkerplay.Entity.User;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: aiYunS
 * @Date: 2022-9-1 下午 05:32
 * @Description: 导出excel工具类
 */
public class ExcelUtil {
    public static void download(HttpServletResponse response, Class t, List<User> list, String excelName, String fileType) throws IOException, IllegalAccessException,InstantiationException {
        response.setContentType("application/vnd.ms-excel");// 设置文本内省
        response.setCharacterEncoding("utf-8");// 设置字符编码
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + "." + fileType); // 设置响应头
        EasyExcel.write(response.getOutputStream(), t).sheet(excelName).doWrite(list); //用io流来写入数据
    }
}
