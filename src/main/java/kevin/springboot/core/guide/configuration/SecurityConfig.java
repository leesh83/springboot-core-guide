package kevin.springboot.core.guide.configuration;

import kevin.springboot.core.guide.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Spring Security 설정임을 명시하고 security 기능을 활성화 한다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // api controller에 @PreAuthorize 어노테이션을 붙여서 허용 권한을 설정 할 수 있는기능을 활성화 한다.
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    //인증없이 접근 가능한 URL 리스트 설정
    public static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**", "/static/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //CSRF, CORS
        http.csrf((csrf) -> csrf.disable()); // csrf 토큰을 사용하지 않으므로 비활성화
        http.cors(Customizer.withDefaults()); // 다른 도메인의 웹페이지에서 리소스에 접근 할 수 있도록 허용 (CORS 발생 방지)

        //세션 관리 상태 없음으로 구성. SpringSecurity가 세션을 사용 하지 않음.
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //시큐리티 기본 formLogin, BasicHttp 비활성화
        http.formLogin((formLogin) -> formLogin.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        //JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 앞에 추가하여 JwtAuthenticationFilter 를 통해 인증이 이루어지도록 한다.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //인증, 인가 실패 시 사용될 exception handler 추가.
        http.exceptionHandling((exceptionHandling) -> exceptionHandling
                .authenticationEntryPoint((customAuthenticationEntryPoint)) // 토큰이 없어 인증실패시 401을 리턴하기 위한 핸들러
                .accessDeniedHandler(customAccessDeniedHandler)); // 권한이 없는 api 호출시 403 을 리턴하기 위한 핸들러

        //권한 규칙 설정
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(AUTH_WHITELIST).permitAll()
                                                         //@PreAuthorization 어노테이션을 통해 각 api별로 따로 권한을 설정할 것이므로 여기서는 모두  permitAll 로 설정한다.
                                                         .anyRequest().permitAll());

        return http.build();
    }
}
