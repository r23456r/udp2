package com.bat.entity;

import lombok.Data;

@Data
public class IPLocation {

	// 国家或城市
	private String country;

	// 纬度
	private double lat;

	// 经度
	private double lng;
}
