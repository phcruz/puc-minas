package br.com.phc.pitaco.gateway;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import br.com.phc.pitaco.utilities.Constantes;

@ComponentScan(basePackages = { "br.com.phc.pitaco.gateway", "br.com.phc.pitaco.utilities" })
@EnableEurekaClient
@SpringBootApplication
public class PitacoGatewayApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PitacoGatewayApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PitacoGatewayApplication.class);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone(Constantes.TIME_ZONE_SP_BR));
	}
}
