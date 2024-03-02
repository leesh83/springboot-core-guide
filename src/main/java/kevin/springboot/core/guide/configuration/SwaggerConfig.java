package kevin.springboot.core.guide.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        //Swagger에서 api 요청시 header에 "Authorization:Bearer 토큰"을  추가하여 요청하기 위한 설정
        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token");

        return new OpenAPI().components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                            .addSecurityItem(securityRequirement)
                            .info(apiInfo());

//        return new OpenAPI().components(new Components())
//                            .info(apiInfo());
    }


    private Info apiInfo() {
        return new Info().title("springdoc-openapi-starter-webmvc-ui")
                         .description("springdoc-openapi-starter-webmvc-ui 을 이용한 Swagger UI")
                         .version("1.0.0");
    }
}
