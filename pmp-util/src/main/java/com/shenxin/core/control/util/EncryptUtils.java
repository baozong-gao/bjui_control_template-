package com.shenxin.core.control.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.crypto.hash.Sha384Hash;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
@Slf4j
public class EncryptUtils {

	private static final String KEY_MD5 = "md5";
	private static final String CHARS = "utf8";

	private static final String deskey = "E2a0s1y6";

	public static String encryptMD5(String data) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
			md5.update(data.getBytes(CHARS));
			byte[] digest = md5.digest();
			return new String(digest, CHARS);
		} catch (Exception e) {
			log.error("md5加密错误", e);
		}
		return null;
	}

	public static String encryptPwd(String strIn, String mobno) throws Exception {
		return encryptDES(strIn, getPwdKey(mobno));
	}

	private static String getPwdKey(String mobno) {
		char[] str = mobno.substring(3, 7).toCharArray();
		String pwdKey = String.format("c%sa%ss%sh%s", str[0], str[1], str[2], str[3]);
		return pwdKey;
	}

	public static String encryptDES(String strIn, String newdesKey) throws Exception {
		if (strIn == null) {
			return null;
		}
		SecureRandom sr = new SecureRandom();
		Key key = getKey(newdesKey == null ? deskey : newdesKey);

		Cipher encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key, sr);

		byte[] enc = encryptCipher.doFinal(strIn.getBytes());

		return new String(Base64.encodeBase64(enc));
	}

	private static Key getKey(String strKey) throws Exception {

		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[8];
		for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); ++i) {
			arrB[i] = arrBTmp[i];
		}
		Key key = new SecretKeySpec(arrB, "DES");
		return key;
	}

	public static String encryptPwd(String data){
		data = EncryptUtils.encryptMD5(data);
		String sha384Hex = new Sha384Hash(data).toBase64();
		System.out.println(data + ":" + sha384Hex);
		return sha384Hex;
	}

}
