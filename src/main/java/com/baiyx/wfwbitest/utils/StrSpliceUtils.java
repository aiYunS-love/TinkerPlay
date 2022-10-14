package com.baiyx.wfwbitest.utils;

import java.util.ArrayList;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-22 下午 04:18
 * @Description:
 */
public class StrSpliceUtils {

    // 数组元素
    public static String strSplice(ArrayList arrayList){
        String str = "";
        StringBuffer stringBuffer = new StringBuffer();
        if(arrayList.size() > 1){
            for(int i=0;i<arrayList.size();i++){
                if(i == arrayList.size()-1){
                    stringBuffer.append(arrayList.get(i).toString());
                }else{
                    stringBuffer.append(arrayList.get(i).toString());
                    stringBuffer.append(", ");
                }
            }
            str = stringBuffer.toString();
        }else if (arrayList.size() == 1){
            str = arrayList.get(0).toString();
        }else{
            return str;
        }
        return str;
    }
}
