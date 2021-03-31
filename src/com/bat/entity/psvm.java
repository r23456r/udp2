package com.bat.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/6/24 11:04
 **/
public class psvm {
    public static void main(String[] args) {
        String hit = "{\n" +
                "\t\"attack_desc\": \"INDICATOR-OBFUSCATION-HTTP-header-invalid-entry-evasion-attempt\",\n" +
                "\t\"attack_type\": 301,\n" +
                "\t\"attack_type_name\": \"DDos/Dos攻击\",\n" +
                "\t\"business\": 1222,\n" +
                "\t\"business_name\": \"煤炭加工\",\n" +
                "\t\"cnt\": 8568,\n" +
                "\t\"company\": 499,\n" +
                "\t\"company_name\": \"晋商煤炭公司_探2\",\n" +
                "\t\"dst_city\": 652324,\n" +
                "\t\"dst_city_name\": \"玛纳斯县\",\n" +
                "\t\"dst_ip\": \"192.168.19.3\",\n" +
                "\t\"dst_latitude\": 44.307,\n" +
                "\t\"dst_longitude\": 86.155,\n" +
                "\t\"dst_port\": 56288,\n" +
                "\t\"dst_province\": 650000,\n" +
                "\t\"dst_province_name\": \"新疆维吾尔自治区\",\n" +
                "\t\"dst_region\": 652300,\n" +
                "\t\"dst_region_name\": \"昌吉回族自治州\",\n" +
                "\t\"id\": \"HP-ZJ-00E2690A426D\",\n" +
                "\t\"level\": 3,\n" +
                "\t\"proto\": 299,\n" +
                "\t\"sim_manu\": \"other\",\n" +
                "\t\"sim_model\": \"other\",\n" +
                "\t\"sim_type\": \"other\",\n" +
                "\t\"src_city\": \"\",\n" +
                "\t\"src_country\": \"局域网\",\n" +
                "\t\"src_ip\": \"192.168.21.31\",\n" +
                "\t\"src_latitude\": 0.0,\n" +
                "\t\"src_longitude\": 0.0,\n" +
                "\t\"src_port\": 80,\n" +
                "\t\"timestamp\": \"2020-05-26T00:00:17+0800\",\n" +
                "\t\"uid\": \"AXJMkL7YDZhKRuytyu9I\"\n" +
                "}";
        Event event = JSONObject.parseObject(hit, Event.class);
        System.out.println(event.getDst_region_name());
    }
}
