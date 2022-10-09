package br.com.phc.pitaco.utilities.config;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import br.com.phc.pitaco.utilities.Constantes;
import io.swagger.models.Scheme;
import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ConditionalOnProperty(name = Constantes.ENABLE_SWAGGER, havingValue = "true", matchIfMissing = false)
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger.base.package}")
	private String basePackage;

	@Value("${swagger.api.title:Pitaco API}")
	private String apiTitle;

	@Value("${swagger.api.description:REST API for pitaco app}")
	private String apiDescription;

	@Value("${swagger.api.version:1.0.0}")
	private String apiVersion;

	@Value("${swagger.api.contact.name:Paulo Henrique da Cruz}")
	private String apiContactName;

	@Value("${swagger.api.contact.github:https://github.com/phcruz}")
	private String apiContactGithub;

	@Value("${swagger.api.contact.mail:henryque_phc@yahoo.com.br}")
	private String apiContactMail;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)//
				.select()//
				.apis(basePackage(basePackage))//
				.paths(PathSelectors.any())//
				.build()//
				.protocols(Sets.newHashSet(Scheme.HTTP.name()))//
				.apiInfo(this.buildApiInfo()).securityContexts(Lists.newArrayList(securityContext()))//
				.securitySchemes(Lists.newArrayList(apiKey()));
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()//
				.title(apiTitle)//
				.description(apiDescription)//
				.version(apiVersion)//
				.contact(new Contact(apiContactName, apiContactGithub, apiContactMail))//
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("jwt", HttpHeaders.AUTHORIZATION, In.HEADER.name());
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(authDefinition()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> authDefinition() {
		final AuthorizationScope[] scopes = new AuthorizationScope[0];
		return Lists.newArrayList(SecurityReference.builder().reference("jwt").scopes(scopes).build());
	}
}
