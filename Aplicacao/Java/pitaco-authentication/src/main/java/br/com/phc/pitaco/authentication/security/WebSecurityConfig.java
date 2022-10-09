package br.com.phc.pitaco.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import br.com.phc.pitaco.authentication.handler.CustomAuthenticationFailureHandler;
import br.com.phc.pitaco.utilities.config.JwtTokenFilterConfigurer;
import br.com.phc.pitaco.utilities.config.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable CSRF (cross site request forgery)
		http.cors().and().csrf().disable();

		// No session will be created or used by spring security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Entry points
		http.authorizeRequests()//
				.antMatchers("/usuario/login").permitAll()//
				.antMatchers("/usuario/cadastro").permitAll()//
				.antMatchers("/usuario/revalidaToken").permitAll()//
				.antMatchers("/usuario/buscarEmailUsuario").permitAll()//
				.antMatchers("/usuario/recuperaSenhaUsuario").permitAll()//
				.antMatchers("/usuario/ativarUsuario").permitAll()//
				.antMatchers("/tokenFirebase/tokenDispositivo").permitAll()//
				.antMatchers("/appHeroku/ativar").permitAll()//
				.antMatchers("/planoFundo/imagem").permitAll()//
				.antMatchers("/users/signin").permitAll()//
				.antMatchers("/users/signup").permitAll()//
				.antMatchers("/h2-console/**/**").permitAll()//
				.antMatchers("/swagger-ui.html/**").permitAll()
				.antMatchers("/artilharia/**").permitAll()
				// Disallow everything else..
				.anyRequest().authenticated();

		// If a user try to access a resource without having enough permissions
		// http.exceptionHandling().accessDeniedPage("/login");

		// Apply JWT
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

		// Optional, if you want to test the API from a browser
		http.httpBasic();
		// adicao do handler de autenticacao
		http.exceptionHandling().authenticationEntryPoint(customAuthenticationFailureHandler());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Allow swagger to be accessed without authentication
		web.ignoring().antMatchers("/v2/api-docs")//
				.antMatchers("/swagger-resources/**")//
				.antMatchers("/swagger-ui/")//
				.antMatchers("/swagger-ui.html")//
				.antMatchers("/configuration/**")//
				.antMatchers("/webjars/**")//
				.antMatchers("/public")

				// Un-secure H2 Database (for testing purposes, H2 console shouldn't be
				// unprotected in production)
				.and().ignoring().antMatchers("/h2/**/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationEntryPoint customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public FilterRegistrationBean<RegisterPublicFilter> loggingFilter() {
		FilterRegistrationBean<RegisterPublicFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new RegisterPublicFilter());
		registrationBean.addUrlPatterns("/usuario/login");
		registrationBean.addUrlPatterns("/usuario/cadastro");
		registrationBean.addUrlPatterns("/usuario/revalidaToken");
		registrationBean.addUrlPatterns("/usuario/buscarEmailUsuario");
		registrationBean.addUrlPatterns("/usuario/recuperaSenhaUsuario");
		registrationBean.addUrlPatterns("/tokenFirebase/tokenDispositivo");
		registrationBean.addUrlPatterns("/appHeroku/ativar");
		registrationBean.addUrlPatterns("/planoFundo/imagem");

		return registrationBean;
	}
}
