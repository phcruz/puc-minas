package br.com.phc.pitaco.websocket;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.utilities.Constantes;

@ComponentScan(basePackages = { "br.com.phc.pitaco.websocket", "br.com.phc.pitaco.utilities" })
@EntityScan(basePackages = { "br.com.phc.pitaco.utilities.model" })
@EnableJpaRepositories(basePackages = "br.com.phc.pitaco.utilities.repository")
@SpringBootApplication
public class PitacoWebsocketApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PitacoWebsocketApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PitacoWebsocketApplication.class);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone(Constantes.TIME_ZONE_SP_BR));
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
