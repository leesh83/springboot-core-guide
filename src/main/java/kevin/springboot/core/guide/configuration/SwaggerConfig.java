package kevin.springboot.core.guide.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("springdoc-openapi-starter-webmvc-ui")
                .description("springdoc-openapi-starter-webmvc-ui 을 이용한 Swagger UI")
                .version("1.0.0");
    }
}
