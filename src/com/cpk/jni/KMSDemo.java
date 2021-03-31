package com.cpk.jni;

import com.nsec.exception.*;
import com.nsec.hardware.CPKDevice;
import com.nsec.hardware.CPKVKey;
import com.nsec.hardware.Identification;
import com.nsec.hardware.UserType;
import com.nsec.software.Tool;

/**
 * 密钥申请、生产和分发流程示例
 * 
 * @author weigang-lee
 * @version 1.0
 */
public class KMSDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 用户虚拟Key的数据模板文件(尚未申请密钥)
		String userVkd = "D:\\data\\kms\\cpktest.vkd";
		// 公钥矩阵文件
		String pubmatrix = "D:\\data\\kms\\nsec.bj.cn.pkm";
		// 密钥生产的虚拟Key数据文件，内含私钥矩阵与foton.cn.pkm对应
		String serverVkd = "D:\\data\\kms\\foton.cn.vkd";
		// 超级用户口令(默认值)
		byte[] surPin = { (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80,
				(byte) 0x80 };
		// 用户口令(默认值)
		String userPin = "11111111";
		// 服务器生产Key实名位的密钥标识
		String serverId = "foton";
		// 用户申请密钥的标识
		String userId = "13900001234";

		// ----------1.客户端产生密钥申请----------------------------

		CPKDevice userVKey = null;
		try {
			userVKey = new CPKVKey(userVkd);
			System.out.println("Open Device success.");
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		System.out.println(userVKey.getJNIVersion());
		try {
			userVKey.verifyPin(UserType.NORMAL_USER, userPin);
			System.out.println("Verify PIN success.");
		} catch (CPKPinException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		// 产生密钥申请，缓存会话密钥
		byte[] env = null;
		try {
			env = userVKey.applyKey(pubmatrix, serverId);
			System.out.println("Generate digital envelope success.");
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		userVKey.close();

		// -----------2.服务器打开数字信封取出会话密钥--------------
		CPKDevice serverVKey = null;
		try {
			serverVKey = new CPKVKey(serverVkd);
			System.out.println("Open Device success.");
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		try {
			serverVKey.verifyPin(UserType.NORMAL_USER, userPin);
			System.out.println("Verify PIN success.");
		} catch (CPKPinException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		// 打开数字信封，取出会话密钥
		byte[] skey = null;
		try {
			skey = serverVKey.openEnvelope(Identification.REAL_NAME, env, true);
			System.out.println("Open digital envelope success.");
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}

		// -------------3.计算标识密钥并内部加密输出---------------------------
		// 密钥生产需要双口令验证
		try {
			serverVKey.verifyPin(UserType.SUPER_USER, surPin);
			System.out.println("Verify PIN success.");
		} catch (CPKPinException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		// 计算标识密钥并加密
		byte[] keyCard = null;
		try {
			userId = "18800001202";
			keyCard = serverVKey.genKeyCard(userId, 5, skey);
			System.out.println("Generate keycard success.");
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		// 如果没有后续密钥申请，可以关闭设备
		serverVKey.close();

		// --------------4.客户端写入密钥------------------------------
		// 写密钥需要双口令验证，步骤1已通过用户口令验证，需要再验证超级口令
		try {
			userVKey = new CPKVKey(userVkd);
			System.out.println("Open Device success.");
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}

		try {
			userVKey.verifyPin(UserType.NORMAL_USER, userPin);
			System.out.println("Verify PIN success.");
		} catch (CPKPinException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		try {
			userVKey.verifyPin(UserType.SUPER_USER, surPin);
			System.out.println("Verify PIN success.");
		} catch (CPKPinException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		// 写密钥
		try {
			System.out.println(Tool.Bytes2Hex(keyCard));
			long t1 = System.currentTimeMillis();
			userVKey.writeKeyCard(Identification.CELL_PHONE, keyCard);
			System.out.println(System.currentTimeMillis() - t1);
			System.out.println("Write keycard success.");
		} catch (CPKException e) {
			System.out.println(e.getMessage() + ":" + e.getCode());
			return;
		}
		// 关闭客户端设备
		userVKey.close();
	}

}
