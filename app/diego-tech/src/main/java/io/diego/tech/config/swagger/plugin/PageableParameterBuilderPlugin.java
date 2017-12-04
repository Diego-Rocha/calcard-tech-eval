package io.diego.tech.config.swagger.plugin;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static springfox.documentation.schema.ResolvedTypes.modelRefFactory;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
@RequiredArgsConstructor(
	onConstructor = @__(@Autowired))
public class PageableParameterBuilderPlugin implements ParameterBuilderPlugin {

	private final TypeNameExtractor nameExtractor;
	private final TypeResolver resolver;

	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	private Function<ResolvedType, ? extends ModelReference> createModelRefFactory(ParameterContext context) {
		ModelContext modelContext = inputParam(context.getGroupName(), context.resolvedMethodParameter().getParameterType(), context.getDocumentationType(), context.getAlternateTypeProvider(), context.getGenericNamingStrategy(), context.getIgnorableParameterTypes());
		return modelRefFactory(modelContext, nameExtractor);
	}

	public void apply(ParameterContext context) {
		ResolvedMethodParameter parameter = context.resolvedMethodParameter();
		ResolvedType type = parameter.getParameterType();
		if (type != null && type.isInstanceOf(Pageable.class)) {
			Function<ResolvedType, ? extends ModelReference> factory = createModelRefFactory(context);

			ModelReference intModel = factory.apply(resolver.resolve(Integer.TYPE));
			ModelReference stringModel = factory.apply(resolver.resolve(List.class, String.class));

			List<Parameter> parameters = new ArrayList<Parameter>() {

				{
					add(context.parameterBuilder().parameterType("query").name("page").modelRef(intModel).description("Results page you want to retrieve (0..N)").build());
					add(context.parameterBuilder().parameterType("query").name("size").modelRef(intModel).description("Number of recods per page").build());
					add(context.parameterBuilder().parameterType("query").name("sort").modelRef(stringModel).description("Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.").allowMultiple(true).build());
				}
			};

			context.getOperationContext().operationBuilder().parameters(parameters);
		}
	}

}