package com.baiyx.wfwbitest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-15 下午 05:22
 * @Description: 读取txt文件内容,转为JSONObject工具类
 */
public class ReadTXTtoJsonObjUtil {

    private static String filePath = "D:/Users/lenovo/Desktop/jsonObj.txt";

    public static JSONObject[] readTXTtoObj(String filePath){
        if(filePath == "" || filePath == null){
            filePath = ReadTXTtoJsonObjUtil.filePath;
        }
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return stringToObj(result.toString());
    }

    public static JSONObject[] stringToObj(String s){
        s = s.replaceAll("\r|\n|\\s*","");
        JSONObject jsonObject = null;
        JSONObject[] jsonObjects = null;
        if(!"".equals(s) && s != null){
            String[] arr = s.split("=====baiyx=====");
            jsonObjects = new JSONObject[arr.length];
            for(int i=0;i<arr.length;i++){
                String strObj = StringEscapeUtils.unescapeJava(arr[i]).replaceAll("\r|\n|\\s*","");
                if(!"".equals(strObj) && "\"}".equals(strObj.substring(strObj.length()-2,strObj.length()))){
                    strObj = strObj.substring(0,strObj.length()-2);
                }
                //System.out.println(strObj);
                if(strObj != null && !"".equals(strObj)){
                    jsonObject = JSON.parseObject(strObj);
                    jsonObjects[i] = jsonObject;
                }
            }
        }
        return jsonObjects;
    }
}
