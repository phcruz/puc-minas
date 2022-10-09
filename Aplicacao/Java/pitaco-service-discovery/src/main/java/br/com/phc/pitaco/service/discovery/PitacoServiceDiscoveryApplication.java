package br.com.phc.pitaco.service.discovery;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import br.com.phc.pitaco.utilities.Constantes;

@EnableEurekaServer
@SpringBootApplication
public class PitacoServiceDiscoveryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PitacoServiceDiscoveryApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PitacoServiceDiscoveryApplication.class);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone(Constantes.TIME_ZONE_SP_BR));
	}

}
