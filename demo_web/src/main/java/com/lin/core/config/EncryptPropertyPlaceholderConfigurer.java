package com.lin.core.config;

import com.lin.utils.rsa.RSAUtil;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import java.util.Objects;

/**
 * Created by lys on 5/25/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private List<String> encryptPropNames;
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if(encryptPropNames.contains(propertyName)){
			byte[] bytes = RSAUtil.decryptBase64(propertyValue.getBytes());
			propertyValue = new String(Objects.requireNonNull(bytes));
		}
		return super.convertProperty(propertyName, propertyValue);
	}

	public List<String> getEncryptPropNames() {
		return encryptPropNames;
	}

	public void setEncryptPropNames(List<String> encryptPropNames) {
		this.encryptPropNames = encryptPropNames;
	}
	static class EncryptUtil{
		static String ii="11";

	}

}
