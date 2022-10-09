package br.com.phc.pitaco.crawler;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.utilities.Constantes;

@ComponentScan(basePackages = { "br.com.phc.pitaco.crawler", "br.com.phc.pitaco.utilities" })
@EntityScan(basePackages = { "br.com.phc.pitaco.utilities.model" })
@EnableJpaRepositories(basePackages = "br.com.phc.pitaco.utilities.repository")
@EnableCaching
@SpringBootApplication
public class PitacoCrawlerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PitacoCrawlerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PitacoCrawlerApplication.class);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone(Constantes.TIME_ZONE_SP_BR));
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
