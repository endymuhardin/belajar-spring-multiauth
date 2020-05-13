package com.muhardin.endy.belajar.spring.security.multiauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@SpringBootApplication
public class BelajarMultiauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BelajarMultiauthApplication.class, args);
	}

	@Value("classpath:/authserver.pfx") private Resource keystoreFile;
	@Value("${keystore.password}") private String keystorePassword;
	@Value("${keystore.alias}") private String keypairAlias;

	@Bean
	public KeyPair keyPair() {
		KeyPair keyPair = new KeyStoreKeyFactory(
				keystoreFile, keystorePassword.toCharArray())
				.getKeyPair(keypairAlias);
		return keyPair;
	}
}
