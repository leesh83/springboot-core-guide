package kevin.springboot.core.guide.configuration;

import kevin.springboot.core.guide.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Spring Security 설정임을 명시한다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // @PreAuthorize 어노테이션으로 controller api 보안 설정기능을 활성화 한다.
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    //인증없이 접근 가능한 URL 리스트 설정
    public static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**", "/static/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //CSRF, CORS
        http.csrf((csrf) -> csrf.disable()); // csrf 토큰을 사용하지 않으므로 비활성화
        http.cors(Customizer.withDefaults()); // 다른 도메인의 웹페이지에서 리소스에 접근 할 수 있도록 허용 (CORS 방지)

        //세션 관리 상태 없음으로 구성. SpringSecurity가 세션 생성, 사용하지않음.
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //시큐리티 기본 formLogin, BasicHttp 비활성화
        http.formLogin((formLogin) -> formLogin.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        //JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 앞에 추가하여 JwtAuthenticationFilter 를 통해 인증이 이루어지도록 한다.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //인증, 인가 실패 시 사용된 exception handler 추가.
        http.exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedHandler(accessDeniedHandler));

        //권한 규칙 설정
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(AUTH_WHITELIST).permitAll()
                                                         //@PreAuthorization 어노테이션을 통해 각 api별로 권한을 설정할 것이므로 여기서는 모두  permitAll 처리한다.
                                                         .anyRequest().permitAll());

        return http.build();
    }
}
