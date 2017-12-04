package io.diego.tech.config.swagger;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${spring.application.name}")
	private String appName;

	@Value("${app.security.path.private}")
	private String appPrivatePath;

	@Value("${app.security.path.public}")
	private String appPublicPath;

	@Value("${management.context-path}")
	private String managementPath;

	private static List<String> privatePaths = new ArrayList<>();

	static {
		privatePaths.add("pessoas");
	}

	private List<Predicate<String>> paths() {
		ArrayList<Predicate<String>> paths = new ArrayList<>();
		paths.add(regex(String.format("%s(\\/.*)?", appPublicPath)));
		for (String privatePath : privatePaths) {
			paths.add(regex(String.format("%s/%s(\\/.*)?", appPrivatePath, privatePath)));
		}
		return paths;
	}

	@Bean
	public Docket docketApi() {
		Docket docket = getNewDocket();
		docket.groupName("api");
		addApiInfo(docket);
		addFilterPaths(docket, paths());
		return docket;
	}

	private Docket getNewDocket() {
		HashSet<String> mimeTypes = Sets.newHashSet("application/json");
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.produces(mimeTypes);
		docket.consumes(mimeTypes);
		docket.useDefaultResponseMessages(false);
		return docket;
	}

	private void addApiInfo(Docket docket) {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title(String.format("%s - API", appName));
		apiInfoBuilder.description("O acesso a esta api é aberta");
		docket.apiInfo(apiInfoBuilder.build());
	}

	private void addFilterPaths(Docket docket, List<Predicate<String>> paths) {
		ApiSelectorBuilder apiSelectorBuilder = docket.select();
		apiSelectorBuilder.apis(RequestHandlerSelectors.any());
		apiSelectorBuilder.paths(or(paths));
		apiSelectorBuilder.build();
	}

}
