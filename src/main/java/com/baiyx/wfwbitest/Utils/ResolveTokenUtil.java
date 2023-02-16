package com.baiyx.wfwbitest.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baiyx.wfwbitest.Entity.TokenAccess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * @Author: 白宇鑫
 * @Date: 2022-8-1 上午 11:01
 * @Description: 公司解析Token工具类
 */
public class ResolveTokenUtil {

    // 秘钥文件
    private static String keyFileName = "token.jks";
    // jwt 别名 mytest
    private static String keyAliasName = "mytest";
    // jwt 秘钥文件密码
    private static String  keyFilePassword = "123456";

    //解析token
    public static TokenAccess resolveToken(String idToken){
        if(StringUtils.isEmpty(idToken) || !idToken.startsWith("Bearer")){
            return null;
        }
        // 用于兼容测试,小过姐账号的Token
        if(idToken.length() == "Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNpMi10KgCAQhO-yzwmaq1Y36BiZKxj0QxYE0d0TK2hgmBmY74S4W2igc2OYoIDYzwv9Nh0LNELrqpQSFS9gj7S2Lj34I_Ea3_wkEhy6LcNKo8jwsIVEplrVaD3rHUmGZCyznDgzxiuPUjlTe7huAAAA__8.H-W7aYzgFQK8-81ZYaYGV918g7UEKLPJBQ8y1RjsVLI".length()
        || idToken.length() == "Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNpMi1EKgzAQRO-y3wZ247oab9BjxGSFCLbSKAji3RushT4YhoF5B-RtgB58nNMTKsjhtejf1n2BnkQ6y9ZRW8GW9f2I5YFf6A7f_YOKnPx6yY00eMnTmooZ1DOxWlOLBsNak_F2RCOujQN2jiKOcH4AAAD__w.N9BdPYJ2_DqXv9A10Fc5kjcelARdxO8Xe7Hh74PrQI4".length()){
            idToken = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnRfaWQiOiIzMyIsInVzZXJfdGVsIjoiIiwidXNlcl9uYW1lIjoiZGYyMDcwMzg1OTg2NiIsInRva2VuX3B3ZF9zdHJvbmdfZmxhZyI6IjEiLCJhdXRob3JpdGllcyI6WyJERUxFVEUjLyoqIiwiUE9TVCMvKioiLCJHRVQjLyoqIiwiUFVUIy8qKiJdLCJ1c2VyX2lkX251bWJlciI6IiIsImNsaWVudF9pZCI6Imdpc3EtNjJjNjRjZGUtNjZmZS0xMWU5LTkzN2EtMmM0ZDU0ZjAxYmE0IiwidXNlcl9raW5kIjoxLCJ0ZW5hbnRfdWlkIjoiZWQ0YzU4MjAtY2QxYS0xMWVjLTllNzgtMDI0MjEwZDhhOWYzIiwidXNlcl9pZCI6ImRkMDQxZDQ4LTBlZTQtMTFlZC1iZGQ0LTAyNDIxMGQ4YTlmMyIsInNjb3BlIjpbImFsbCJdLCJ1c2VyX25pY2tuYW1lIjoi6L-H5LiA5YekIiwidG9rZW5fcHdkX292ZXJkdWVfZmxhZyI6IjEiLCJleHAiOjE2NjU2NjE5MjIsImp0aSI6IjNiYjhmNjEyLWJhYmItNDkwMS1hMzMyLTQ1MmQ2NzUyNGJlOCJ9.w65GcLT_yGkhkFQr66ackzravacE-ifU40eF0sSgo-0x_8UegZunQDq_HwzwaAQXWgMuZQxk2xzGgKy4tvq2-yMz5-GOYgNT07KXoeLQL9KeJAN0zwW3KGUUEeLzItDoTh1jcxla2nkKATmkiLNQ_F5L35HCnjRlNCCiRaiTpMP1GXwnvzu54i1yjUW3dE9qglfLBVQfJwexX8rz7AEj6zxe39ohysS2HJ6tWDHia2igdi2LE3647Bks0M5wsjbVKYNNGuN8fL5lNddZ3mOjr4cQvm6yRWEXAgJPes2QfxOcDMaliTm7ne9zpkJAT06QMJpv95j0i80VQY0HXtwVjw";
        }
        String token = idToken.substring("bearer".length() + 1);
//      KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(oAuth2Properties.getKeyFileName()),
//                                                oAuth2Properties.getKeyFilePassword().toCharArray());
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyFileName),keyFilePassword.toCharArray());
//      KeyPir keyPir = keyStoreKeyFactory.getKeyPair(oAuth2Properties.getKeyAliasName());
        KeyPair keyPir = keyStoreKeyFactory.getKeyPair(keyAliasName);
        RSAPublicKey publicKey = (RSAPublicKey) keyPir.getPublic();
        SignatureVerifier verifier = new RsaVerifier(publicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(token, verifier);
        String content = jwt.getClaims();
        // Map<String,Object> tokenMap = new HashMap();
        TokenAccess tokenAccess = new TokenAccess();
        JSONObject jsonObject =  JSON.parseObject(content);
        String user_id = jsonObject.getString("user_id");
        String username = jsonObject.getString("user_name");
        String staff_name = jsonObject.getString("user_nickname");
        tokenAccess.setUser_id(user_id);
        tokenAccess.setUsername(username);
        tokenAccess.setStaff_name(staff_name);
//        tokenMap.put("user_id",user_id);
//        tokenMap.put("username",username);
//        tokenMap.put("staff_name",staff_name);
//        return tokenMap;
        return tokenAccess;
    }
}
