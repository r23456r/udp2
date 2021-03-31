//package com.bat.entity;
//
//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
//
//import java.io.Console;
//import java.util.ArrayList;
//
///**
// * @author: zhangyuhang
// * @modified By：
// * @date ：Created in 2020/5/26 13:28
// **/
//public class Name {
//
//    public static void main(String[] args) {
//
//        for (int i1 = 0; i1 < 500; i1++) {
//            System.out.println(getName());
//        }
//    }
//
//    private static String getName() {
//
//        String[] famNames = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈",
//                "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
//                "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏",
//                "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章",
//                "云", "苏", "潘", "葛", "奚", "范", "彭", "郎", "鲁", "韦",
//                "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳",
//                "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺",
//                "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常",
//                "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余",
//                "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹"
//        };
//        String[] lastNames = {
//                "子璇", "淼", "国栋", "夫子", "瑞堂", "甜", "敏", "尚", "国贤", "贺祥", "晨涛",
//                "昊轩", "易轩", "益辰", "益帆", "益冉", "瑾春", "瑾昆", "春齐", "杨", "文昊",
//                "东东", "雄霖", "浩晨", "熙涵", "溶溶", "冰枫", "欣欣", "宜豪", "欣慧", "建政",
//                "美欣", "淑慧", "文轩", "文杰", "欣源", "忠林", "榕润", "欣汝", "慧嘉", "新建",
//                "建林", "亦菲", "林", "冰洁", "佳欣", "涵涵", "禹辰", "淳美", "泽惠", "伟洋",
//                "涵越", "润丽", "翔", "淑华", "晶莹", "凌晶", "苒溪", "雨涵", "嘉怡", "佳毅",
//                "子辰", "佳琪", "紫轩", "瑞辰", "昕蕊", "萌", "明远", "欣宜", "泽远", "欣怡",
//                "佳怡", "佳惠", "晨茜", "晨璐", "运昊", "汝鑫", "淑君", "晶滢", "润莎", "榕汕",
//                "佳钰", "佳玉", "晓庆", "一鸣", "语晨", "添池", "添昊", "雨泽", "雅晗", "雅涵",
//                "清妍", "诗悦", "嘉乐", "晨涵", "天赫", "玥傲", "佳昊", "天昊", "萌萌", "若萌"
//        };
//
//        int i = (int) (Math.random() * famNames.length);
//        int j = (int) (Math.random() * lastNames.length);
//        String fam = famNames[i];
//        String last = lastNames[j];
//
//        return fam + last + "   " + getPinYinFirstChar(fam + last) + "    " + getPinYin(fam + last);
//
//    }
//    public static String getPinYin(String str) {
//        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
//        // 默认小写
//        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        // 不显示拼音的声调
//        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        // outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
//
//        StringBuilder sb = new StringBuilder();
//        try {
//            for (char c : str.toCharArray()) {
//                // 如果包含有中文标点除号，需要使用正则表达式
//                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
//                    // if (c > 128) {
//                    sb.append(PinyinHelper.toHanyuPinyinStringArray(c,
//                            outputFormat)[0]);
//                } else {
//                    sb.append(Character.toString(c));
//                }
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 提取每个汉字的首字母
//     *
//     * @param str
//     * @return String
//     */
//    public static String getPinYinFirstChar(String str) {
//        StringBuilder sb = new StringBuilder();
//        for (char c : str.toCharArray()) {
//            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
//            if (pinyinArray != null) {
//                sb.append(pinyinArray[0].charAt(0));
//            } else {
//                sb.append(c);
//            }
//        }
//        return sb.toString();
//    }
//
//
//    /**
//     * 将汉字转 Unicode 码
//     * @return String
//     */
//    public static String toUnicode(String str) {
//        StringBuilder sb = new StringBuilder();
//        int len = str.length();
//        for (int i = 0; i < len; i++) {
//            // 将每个字符转换成ASCII码
//            sb.append(Integer.toHexString(str.charAt(i) & 0xffff) + "\\u");
//        }
//        return sb.toString();
//    }
//}
