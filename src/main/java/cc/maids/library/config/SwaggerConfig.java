package cc.maids.library.config;



import org.springdoc.core.customizers.OpenApiCustomizer;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenApiCustomizer openAPI() {

    String basicAuth = "Basic Authentication";

    return openApi -> {
      openApi.addSecurityItem(new SecurityRequirement().addList(basicAuth))
          .getPaths().values().forEach(this::responses);

      openApi.getComponents()
          .addSecuritySchemes(basicAuth, new SecurityScheme()
              .type(SecurityScheme.Type.HTTP)
              .scheme("basic")
              .name(basicAuth));
    };
  }

  private void responses(PathItem pathItem) {
    pathItem.readOperations().
        forEach(operation -> {
          ApiResponses apiResponses = operation.getResponses();
          ApiResponse apiResponse201 = new ApiResponse().description("Created");
          apiResponses.addApiResponse("201", apiResponse201);
          ApiResponse apiResponse400 = new ApiResponse().description("Bad Request");
          apiResponses.addApiResponse("400", apiResponse400);
          ApiResponse apiResponse401 = new ApiResponse().description("Unauthorized");
          apiResponses.addApiResponse("401", apiResponse401);
          ApiResponse apiResponse403 = new ApiResponse().description("Forbidden");
          apiResponses.addApiResponse("403", apiResponse403);
          ApiResponse apiResponse404 = new ApiResponse().description("Not Found");
          apiResponses.addApiResponse("404", apiResponse404);
          ApiResponse apiResponse500 = new ApiResponse().description("Internal Server Error");
          apiResponses.addApiResponse("500", apiResponse500);
        });
  }

}

