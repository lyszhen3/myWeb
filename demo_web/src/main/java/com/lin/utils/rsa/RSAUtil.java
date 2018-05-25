package com.lin.utils.rsa;

import org.springframework.core.io.ClassPathResource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * Created by lys on 5/25/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class RSAUtil {

	private static Cipher cipher;

	private final static String ALGORITHM = "RSA";

	static {
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException ignored) {
		}
	}

	/**
	 * 生成密钥对
	 *
	 * @param path 输出地址
	 */
	public static void getKeyPair(String path) {
		KeyPairGenerator keyPairGenerator = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGenerator.initialize(512, new SecureRandom());
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PrivateKey aPrivate = keyPair.getPrivate();
		PublicKey aPublic = keyPair.getPublic();

		try (FileOutputStream fileOutputStreamPrivate = new FileOutputStream(path + "private.key");
			 FileOutputStream fileOutputStreamPublic = new FileOutputStream(path + "public.key")
		) {
			fileOutputStreamPrivate.write(Base64.getEncoder().encode(aPrivate.getEncoded()));
			fileOutputStreamPrivate.flush();
			fileOutputStreamPublic.write(Base64.getEncoder().encode(aPublic.getEncoded()));
			fileOutputStreamPublic.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param publicKey 公钥
	 * @param pass 明文数据
	 * @return base64.encode
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] pass) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(pass);
		} catch (InvalidKeyException | BadPaddingException ignored) {
		} catch (IllegalBlockSizeException e) {
			System.out.println("长度非法");
		}
		return null;
	}

	public static byte[] decrypt(PrivateKey privateKey, byte[] cipherDataBase64) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] bytes = cipher.doFinal(cipherDataBase64);
			return  bytes;
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException ignored) {
		}
		return null;

	}

	public static byte[] encryptBase64(byte[] pass) {
		String publickEncode = getPublickEncode();
		byte[] decode = Base64.getDecoder().decode(publickEncode.getBytes());
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return Base64.getEncoder().encode(Objects.requireNonNull(encrypt(publicKey, pass)));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptBase64(byte[] cipherdateBase64){
		byte[] cipher = Base64.getDecoder().decode(cipherdateBase64);
		String privateEncode = getPrivateEncode();
		byte[] decode = Base64.getDecoder().decode(Objects.requireNonNull(privateEncode).getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return decrypt(privateKey,cipher);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getPublickEncode() {
		ClassPathResource classPathResource = new ClassPathResource("/rsakey/public.key");
		String readLine;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
			while ((readLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(readLine);
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static String getPrivateEncode(){
		ClassPathResource classPathResource = new ClassPathResource("/rsakey/private.key");
		String readLine;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
			while ((readLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(readLine);
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
//		getKeyPair("d:\\");
		byte[] encypt = encryptBase64("49c532f347c99fd4b5f7f72d4414b507".getBytes());
		System.out.println(new String(encypt));

		byte[] decrypt = decryptBase64(encypt);
		System.out.println(new String(decrypt));

	}


}
