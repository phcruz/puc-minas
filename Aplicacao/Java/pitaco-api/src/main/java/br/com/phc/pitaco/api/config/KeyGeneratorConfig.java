package br.com.phc.pitaco.api.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyGeneratorConfig {

	@Bean("customKeyGenerator")
	public KeyGenerator keyGenerator() {
		return new CustomKeyGeneratorConfig();
	}
}
