package com.lesso.newlp;

import java.io.IOException;

/**
 * Created by Sean on 6/24/2014.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        System.out.println(new DateTime().toString("yyyyMMdd"));
//        System.out.println(new String("Í¨Ñ¶Òì³£".getBytes("ISO-8859-1"), "GBK"));

        //8 Í¨Ñ¶Òì³£                             通讯异常
        // 3 ÊÖ»úºÅÂë²»ÕýÈ·             手机号码不正确
        // 102 ÎÞ·¨µ½´ïÊÖ»ú[null]¡£  无法到达手机[null]。

//        String str = "{\"creditId\":1,\"active\":true,\"type\":1,\"amount\":10000333,\"percent\":0.2333,\"creditAmount\":200333,\"insertDate\":\"2014-07-03T02:47:53.000+0000\",\"validDate\":\"2014-07-05T04:00:00.000Z\",\"expiryDate\":\"2014-07-09T12:00:00.000+0000\",\"description\":\"管道123\",\"client\":{\"clientId\":21165,\"clientNum\":\"LSZJHD-001\",\"clientName\":\"广州恒大材料设备有限公司\"},\"links\":[],\"editable\":true,\"validDateOpened\":false} ";
//
//
//        String s = "{\"inserDate\":\"2014-07-10T04:00:00.000Z\"}";
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.registerModule(new JodaModule());
////        objectMapper.setDateFormat((new ISO8601DateFormat()));
////        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//////        objectMapper.setDateFormat(dateFormat);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
////        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
////        StdDateFormat dateFormat = new StdDateFormat();
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        objectMapper.setDateFormat(dateFormat);
////        objectMapper.setConfig(objectMapper.getDeserializationConfig().with(dateFormat));
//        Test test = objectMapper.readValue(s, Test.class);
//
//
//
//        System.out.println(objectMapper.writeValueAsString(test));
    }
}
