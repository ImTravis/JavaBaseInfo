package com.example.demo.baiduAI;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class Base64Util {
    /**
     * 编码字符串
     * @param str
     * @return
     */
    public static String encode(String str){
        if(StringUtils.isEmpty(str)) return "";
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * 解码字符串
     * @param str
     * @return
     */
    public static String decode(String str){
        if(StringUtils.isEmpty(str)) return "";
        return new String(Base64.decodeBase64(str));
    }


    /**
     * 编码字符串
     * @param byte[]
     * @return
     */
    public static String encode(byte[] imgData){
        return Base64.encodeBase64String(imgData);
    }
}
