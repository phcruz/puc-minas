package br.com.phc.pitaco.config.server;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import br.com.phc.pitaco.utilities.Constantes;

@EnableConfigServer
@SpringBootApplication
public class PitacoConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PitacoConfigServerApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone(Constantes.TIME_ZONE_SP_BR));
	}
}
