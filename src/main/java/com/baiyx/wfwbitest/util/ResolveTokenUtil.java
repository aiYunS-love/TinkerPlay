package com.baiyx.wfwbitest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 白宇鑫
 * @Date: 2022-8-1 上午 11:01
 * @Description: 解析Token工具类
 */
public class ResolveTokenUtil {

    // 秘钥文件
    private static String keyFileName = "token.jks";
    // jwt 别名 mytest
    private static String keyAliasName = "mytest";
    // jwt 秘钥文件密码
    private static String  keyFilePassword = "123456";

    //解析token
    public static Map<String,Object> resolveToken(String idToken){
        if(StringUtils.isEmpty(idToken) || !idToken.startsWith("Bearer")){
            return null;
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
        Map<String,Object> tokenMap = new HashMap();
        JSONObject jsonObject =  JSON.parseObject(content);
        String user_id = jsonObject.getString("user_id");
        String username = jsonObject.getString("user_name");
        String staff_name = jsonObject.getString("user_nickname");
        tokenMap.put("user_id",user_id);
        tokenMap.put("username",username);
        tokenMap.put("staff_name",staff_name);
        return tokenMap;
    }
}
