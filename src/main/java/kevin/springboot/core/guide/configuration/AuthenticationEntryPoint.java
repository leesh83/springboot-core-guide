package kevin.springboot.core.guide.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kevin.springboot.core.guide.dto.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * jwt 토큰 인증 실패 시에도 정상적으로 호출되지 않아서 이클래스는 사용하지않음
 * 대신 jwt 토큰 인증 실패시 JwtAuthenticationFilter 에서 직접 401 response를 만들어서 응답한다.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("AuthenticationEntryPoint - commence 인증 실패");
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage())));
    }
}
