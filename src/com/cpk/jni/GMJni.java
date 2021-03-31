package com.cpk.jni;

/**
 * 国密算法接口
 *
 * @author wgli
 * @version 1.0
 * @since 2020-12-05
 */
public class GMJni {
    static {
        System.loadLibrary("GMJni");
//		System.load("D:\\WorkArea\\Com\\library\\gmjni\\x64\\bin\\GMJni.dll");
    }

    /**
     * 得到JNI动态库的版本号
     *
     * @return JNI库版本号
     */
    public native static byte[] GetJNIVersion();

    /**
     * 计算SM2预处理E值(国密检测)
     *
     * @param userId     用户标识
     * @param pubKey     公钥
     * @param signedData 待签名数据
     * @return E值
     */
    public native static byte[] SM2GetE(byte[] userId, byte[] pubKey, byte[] signedData);

    /**
     * SM2数字签名
     *
     * @param priKey 私钥
     * @param E      SM2的预处理值
     * @return 签名值(r, s)
     */
    public native static byte[] SM2Sign(byte[] priKey, byte[] E);

    /**
     * SM2签名验证(国密检测)
     *
     * @param pubKey  公钥
     * @param E       E值
     * @param signVal 签名信息
     * @return 验证通过返回0，否则返回错误号
     */
    public native static int SM2Verify(byte[] pubKey, byte[] E, byte[] signVal);

    /**
     * 通过标识和公钥矩阵计算公钥
     *
     * @param pubMatrix 公钥矩阵文件
     * @param keyId     密钥标识
     * @return 公钥
     */
    public native static byte[] CalPubKey(byte[] pubMatrix, byte[] keyId);

    /**
     * 从密钥数据结构中得到私钥
     *
     * @param keycard 密钥数据结构
     * @return 私钥明文
     */
    public native static byte[] GetPriKey(byte[] keycard);

    /**
     * 从密钥数据结构中得到标识
     *
     * @param keycard 密钥数据结构
     * @return 密钥标识
     */
    public native static byte[] GetKeyId(byte[] keycard);
}
