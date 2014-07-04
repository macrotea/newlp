package com.lesso.newlp;

import java.io.UnsupportedEncodingException;

/**
 * Created by Sean on 6/24/2014.
 */
public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(new DateTime().toString("yyyyMMdd"));
        System.out.println(new String("Í¨Ñ¶Òì³£".getBytes("ISO-8859-1"), "GBK"));

        //8 Í¨Ñ¶Òì³£                             通讯异常
        // 3 ÊÖ»úºÅÂë²»ÕýÈ·             手机号码不正确
        // 102 ÎÞ·¨µ½´ïÊÖ»ú[null]¡£  无法到达手机[null]。
    }
}
