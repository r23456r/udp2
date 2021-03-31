package com.bat.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties()
public class Event {
    // 中京上传字段

    private String uid;

    /**
     * 探针Id
     */
    private String id;

    /**
     * 攻击类型父类
     */
    private int attack_type;

    /**
     * 攻击类型父类名称
     */
    private String attack_type_name;

    /**
     * 攻击描述
     */
    private String attack_desc;

    /**
     * 目的IP (资源IP 探针IP)
     */
    private String dst_ip;

    /**
     * 目的端口
     */
    private int dst_port;

    /**
     * 源IP
     */
    private String src_ip;

    /**
     * 源端口
     */
    private int src_port;

    /**
     * 攻击使用的协议（字典）
     */
    private int proto;

    /**
     * 危害程度
     * 1=>'高',
     * 2=>'中高',
     * 3=>'中',
     * 4=>'中低',
     * 5=>'低'
     */
    private int level;

    /**
     * 仿真型号
     */
    private String sim_model;

    /**
     * 仿真设备厂商
     */
    private String sim_manu;

    /**
     * 仿真类型
     */
    private String sim_type;

    /**
     * 攻击时间
     */
    @JSONField(format = "yyyy-MM-ddTHH:mm:ssZ")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    // 本地补全字段 ----------------------------------------------
    /**
     * 重复攻击（来源，目的，攻击类型）数量，先尝试每批次去重
     */
    private int cnt;

    /**
     * 公司代码
     */
    private int company;

    /**
     * 公司简称
     */
    private String abbr;

    /**
     * 公司名称
     */
    private String company_name;

    /**
     * 行业代码
     */
    private int business;

    /**
     * 行业名称
     */
    private String business_name;

    /**
     * 攻击城市代码
     */
    private int dst_city;

    /**
     * 攻击城市名称
     */
    private String dst_city_name;

    /**
     * 攻击地区代码
     */
    private int dst_region;

    /**
     * 攻击地区名称
     */
    private String dst_region_name;

    /**
     * 攻击省份代码
     */
    private int dst_province;

    /**
     * 攻击省份
     */
    private String dst_province_name;

    /**
     * 攻击目的经度
     */
    private double dst_longitude;

    /**
     * 攻击目的纬度
     */
    private double dst_latitude;

    /**
     * 攻击来源国家
     */
    private String src_country;

    /**
     * 攻击来源城市
     */
    private String src_city;

    /**
     * 攻击来源经度
     */
    private double src_longitude;

    /**
     * 攻击来源纬度
     */
    private double src_latitude;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAttack_type() {
        return attack_type;
    }

    public void setAttack_type(int attack_type) {
        this.attack_type = attack_type;
    }

    public String getDst_ip() {
        return dst_ip;
    }

    public void setDst_ip(String dst_ip) {
        this.dst_ip = dst_ip;
    }

    public int getDst_port() {
        return dst_port;
    }

    public void setDst_port(int dst_port) {
        this.dst_port = dst_port;
    }

    public String getSrc_ip() {
        return src_ip;
    }

    public void setSrc_ip(String src_ip) {
        this.src_ip = src_ip;
    }

    public int getSrc_port() {
        return src_port;
    }

    public void setSrc_port(int src_port) {
        this.src_port = src_port;
    }

    public int getProto() {
        return proto;
    }

    public void setProto(int proto) {
        this.proto = proto;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSim_model() {
        return sim_model;
    }

    public void setSim_model(String sim_model) {
        this.sim_model = sim_model;
    }

    public String getSim_manu() {
        return sim_manu;
    }

    public void setSim_manu(String sim_manu) {
        this.sim_manu = sim_manu;
    }

    public String getSim_type() {
        return sim_type;
    }

    public void setSim_type(String sim_type) {
        this.sim_type = sim_type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public int getDst_city() {
        return dst_city;
    }

    public void setDst_city(int dst_city) {
        this.dst_city = dst_city;
    }

    public int getDst_region() {
        return dst_region;
    }

    public void setDst_region(int dst_region) {
        this.dst_region = dst_region;
    }

    public String getDst_region_name() {
        return dst_region_name;
    }

    public void setDst_region_name(String dst_region_name) {
        this.dst_region_name = dst_region_name;
    }

    public int getDst_province() {
        return dst_province;
    }

    public void setDst_province(int dst_province) {
        this.dst_province = dst_province;
    }

    public String getDst_province_name() {
        return dst_province_name;
    }

    public void setDst_province_name(String dst_province_name) {
        this.dst_province_name = dst_province_name;
    }

    public double getDst_longitude() {
        return dst_longitude;
    }

    public void setDst_longitude(double dst_longitude) {
        this.dst_longitude = dst_longitude;
    }

    public double getDst_latitude() {
        return dst_latitude;
    }

    public void setDst_latitude(double dst_latitude) {
        this.dst_latitude = dst_latitude;
    }

    public String getSrc_country() {
        return src_country;
    }

    public void setSrc_country(String src_country) {
        this.src_country = src_country;
    }

    public String getSrc_city() {
        return src_city;
    }

    public void setSrc_city(String src_city) {
        this.src_city = src_city;
    }

    public double getSrc_longitude() {
        return src_longitude;
    }

    public void setSrc_longitude(double src_longitude) {
        this.src_longitude = src_longitude;
    }

    public double getSrc_latitude() {
        return src_latitude;
    }

    public void setSrc_latitude(double src_latitude) {
        this.src_latitude = src_latitude;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getDst_city_name() {
        return dst_city_name;
    }

    public void setDst_city_name(String dst_city_name) {
        this.dst_city_name = dst_city_name;
    }

    public String getAttack_type_name() {
        return attack_type_name;
    }

    public void setAttack_type_name(String attack_type_name) {
        this.attack_type_name = attack_type_name;
    }

    public String getAttack_desc() {
        return attack_desc;
    }

    public void setAttack_desc(String attack_desc) {
        this.attack_desc = attack_desc;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
