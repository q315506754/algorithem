package com.jiangli.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Provider;
import java.security.Security;


/**
 * @author zhaoxiang
 */
public class EncryptionUtil {
	private static SecretKeySpec skspec;
	private final static String COOKIE_SIGN = "COOS";
	static {
		try {
			Provider sunJce = new com.sun.crypto.provider.SunJCE();
			Security.addProvider(sunJce);
			byte[] passwordInBytes = COOKIE_SIGN.getBytes("UTF-8");
			skspec = new SecretKeySpec(passwordInBytes, "Blowfish");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * cookiePassport加密
	 *
	 * @param str
	 * @return
	 */
	public static String encryptCookie(String str) {
		try {
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skspec);
			byte[] arr = cipher.doFinal(str.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(arr), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * cookiePassport解密
	 * 
	 * @param b64str
	 * @return
	 */
	public static String decryptCookie(String str) {
		try {
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skspec);
			// byte[] arr = decoder.decodeBuffer(str);
			byte[] arr = Base64.decodeBase64(str.getBytes("UTF-8"));
			String result = new String(cipher.doFinal(arr), "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}

}
