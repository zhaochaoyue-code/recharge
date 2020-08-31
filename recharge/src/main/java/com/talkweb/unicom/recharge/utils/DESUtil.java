package com.talkweb.unicom.recharge.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

public class DESUtil {

	private static final String SECRET_KEY_TYPE = "DES";
	private static final String ECB_MOB = "DES/ECB/PKCS5Padding";
	private static final String CHAESET_NAME = "UTF-8";

	private static Key getKey(final String password) throws Exception {
		byte[] DESkey = password.getBytes(CHAESET_NAME);
		DESKeySpec keySpec = new DESKeySpec(DESkey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KEY_TYPE);
		return keyFactory.generateSecret(keySpec);
	}

	public static String encode(final String data, final String password) throws Exception {
		Cipher enCipher = Cipher.getInstance(ECB_MOB);
		Key key = getKey(password);
		enCipher.init(1, key);
		byte[] pasByte = enCipher.doFinal(data.getBytes(CHAESET_NAME));
		return Base64.encodeBase64String(pasByte);
	}

	public static String decode(final String data, final String password) throws Exception {
		Cipher deCipher = Cipher.getInstance(ECB_MOB);
		Key key = getKey(password);
		deCipher.init(2, key);
		byte[] pasByte = deCipher.doFinal(Base64.decodeBase64(data.getBytes(CHAESET_NAME)));
		return new String(pasByte, CHAESET_NAME);
	}
}
