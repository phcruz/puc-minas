package br.com.phc.pitaco.details.config;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

public class CustomKeyGeneratorConfig implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return target.getClass().getSimpleName() + "_" //
				+ method.getName() + "_" //
				+ StringUtils.arrayToDelimitedString(params, "_");
	}

	@Bean("customKeyGenerator")
	public KeyGenerator keyGenerator() {
		return new CustomKeyGeneratorConfig();
	}
}
