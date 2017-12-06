package io.diego.tech.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Log
@EnableWebSecurity
@RequiredArgsConstructor(
	onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public final static String ROLE_PREFIX = "io.diego.tech.auth.";;

	private final static String SUPER_USER_ROLE = ROLE_PREFIX + "admin";

	private final Environment environment;

	@Value("${app.security.path.private}")
	private String appPrivatePath;

	@Value("${app.security.path.public}")
	private String appPublicPath;

	@Value("${management.context-path}")
	private String managementPath;

	private static List<String> privateCrudPaths = new ArrayList<>();

	static {
		privateCrudPaths.add("pessoas");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.csrf().disable();
		httpSecurity.headers().cacheControl().disable();

		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authRules = httpSecurity.authorizeRequests();
		addPermissiveRules(authRules);
		addRestritiveRules(authRules);
	}

	private void addPermissiveRules(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authRules) {
		boolean isAmbienteProducao = Arrays.stream(environment.getActiveProfiles()).anyMatch("producao"::equals);
		if (!isAmbienteProducao) {
			authRules.antMatchers("/webjars/springfox-swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs").permitAll();
		}
		authRules.antMatchers("/js/**", "/css/**").permitAll();
		authRules.antMatchers("/favicon.ico").permitAll();
		authRules.antMatchers(String.format("%s/**", appPublicPath)).permitAll();
		authRules.antMatchers("/").permitAll();
	}

	private void addRestritiveRules(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authRules) {
		for (String privateCrudPath : privateCrudPaths) {
			addCrudRules(authRules, privateCrudPath);
		}
		authRules.antMatchers(String.format("%s/**", managementPath)).hasAuthority(SUPER_USER_ROLE);
		authRules.anyRequest().hasAuthority(SUPER_USER_ROLE);
	}

	private void addCrudRules(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authRules, String resource) {
		String url = String.format("%s/%s/**", appPrivatePath, resource);

		String readAuthority = ROLE_PREFIX + resource;
		String createAndUpdateAuthority = String.format("%s.alterar", resource);
		String deleteAuthority = String.format("%s.excluir", resource);

		authRules.antMatchers(HttpMethod.GET, url).hasAnyAuthority(readAuthority, SUPER_USER_ROLE);
		authRules.antMatchers(HttpMethod.POST, url).hasAnyAuthority(createAndUpdateAuthority, SUPER_USER_ROLE);
		authRules.antMatchers(HttpMethod.PUT, url).hasAnyAuthority(createAndUpdateAuthority, SUPER_USER_ROLE);
		authRules.antMatchers(HttpMethod.DELETE, url).hasAnyAuthority(deleteAuthority, SUPER_USER_ROLE);
	}
}
