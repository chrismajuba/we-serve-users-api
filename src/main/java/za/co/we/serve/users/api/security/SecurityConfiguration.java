package za.co.we.serve.users.api.security;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import za.co.we.serve.users.api.exception.handler.GlobalExceptionHandler;
import za.co.we.serve.users.api.security.filter.JWTtokenFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	/*
	 * Attributes
	 */
	private GlobalExceptionHandler globalExceptionHandler;
	private JWTtokenFilter jwTtokenFilter;

	public SecurityConfiguration(GlobalExceptionHandler globalExceptionHandler, JWTtokenFilter jwTtokenFilter) {
		this.globalExceptionHandler = globalExceptionHandler;
		this.jwTtokenFilter = jwTtokenFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());
		// http.authorizeHttpRequests((request) ->
		// request.requestMatchers(HttpMethod.GET,"/we-serve/api/users").permitAll());
		http.authorizeHttpRequests((request) -> request.requestMatchers("/we-serve/auth/api/v1/users/login",
				"/we-serve/auth/api/v1/users/signin", "/swagger-ui/**", "/v3/api-docs/**", "/we-serve/auth/api/v1/users/validate-token").permitAll());
		http.authorizeHttpRequests((request) -> request.anyRequest().authenticated());
		// http.httpBasic(Customizer.withDefaults());
		http.exceptionHandling((handler) -> handler.authenticationEntryPoint(globalExceptionHandler));
		http.sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwTtokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.cors(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("API Documentation").version("1.0.0")
						.description("API documentation with JWT authentication"))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT").in(In.HEADER)
								.name("Authorization")));
	}

	@Bean
	GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("public").pathsToMatch("/**").build();
	}
}
