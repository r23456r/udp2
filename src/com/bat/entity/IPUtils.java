//package com.bat.entity;
//
//import qiniu.ip17mon.LocationInfo;
//import qiniu.ip17mon.Locator;
//
//import java.io.*;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author zhangyuhang
// */
//public class IPUtils {
//	/**
//	 * 城市IP库
//	 */
//	final static String IPBASE_PATH = "ip.datx";
//
//	private static Locator locator;
//
//	public static LocationInfo getLocationForIP(String ip) {
//		return locator.find(ip);
//	}
//
//	/**
//	 * 国家或城市经纬度库
//	 */
//	private static ConcurrentHashMap<String, IPLocation> locations = new ConcurrentHashMap<>(512);
//
//	private static final String LOCATION_FILENAME = "location.dat";
//
//	public static IPLocation getLocationForCityOrCountry(String name) {
//		if (locations.containsKey(name)) {
//			return locations.get(name);
//		}
//		return null;
//	}
//
//	public static void init() throws IOException {
//		// load ip location info.
//		locator = Locator.loadFromLocal(IPBASE_PATH);
//
//		// load locations
//		File file = new File(LOCATION_FILENAME);
//		if (file.exists()) {
//			InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
//			BufferedReader br = new BufferedReader(reader);
//			String str;
//			while ((str = br.readLine()) != null) {
//				String[] splits = str.split(",");
//				IPLocation l = new IPLocation();
//				l.setCountry(splits[0]);
//				l.setLng(Double.valueOf(splits[1]));
//				l.setLat(Double.valueOf(splits[2]));
//				locations.put(l.getCountry(), l);
//			}
//			br.close();
//			reader.close();
//		} else {
//			System.out.println("没找到Location.dat.");
//		}
//	}
//
//	public static void main(String[] args) throws IOException {
//		init();
//		String str = "123.234.207.28";
//		System.out.println(getLocationForIP(str));
//	}
//}
