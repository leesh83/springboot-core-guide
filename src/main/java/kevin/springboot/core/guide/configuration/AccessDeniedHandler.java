package kevin.springboot.core.guide.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kevin.springboot.core.guide.dto.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    //AccessDeniedException : 액세스 권한이 없는 리소스에 접근할 경우 발생하는 예외 발생 시 403 권한없음 을 response 한다
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("AccessDeniedHandler - handle 엑세스 권한 없음");
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage())));
    }
}
