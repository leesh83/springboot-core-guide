package kevin.springboot.core.guide.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kevin.springboot.core.guide.dto.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter
 * jwt토큰으로 인증하고, SecurityContextHolder에 인증정보를 저장한다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter : 한번 실행 보장
    private final JwtTokenProvider jwtTokenProvider;
    private static final String authrizationHeaderName = "Authorization";

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilterInternal 시작");
        try {
            String authorizationHeader = request.getHeader(authrizationHeaderName);

            //jwt 토큰이 헤더에 있는 경우
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                //jwt 토큰 유효성 검증 통과 시
                if (jwtTokenProvider.validToken(token)) {
                    //토큰에서 인증정보(유저정보) 조회
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    //현재 request 의 SecurityContext에 인증정보 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("doFilterInternal - SecurityContextHolder에 인증정보 저장");
                }
            }
            filterChain.doFilter(request, response); // 다음 필터로 넘긴다.
        } catch (JwtException e) {
            log.error("doFilterInternal - JwtException 발생  : {},  message : {}", request.getRequestURI(), e, e.getMessage());
            //jwt 토큰 유효성 검사 실패 시 401 error response
            createErrorResponse(response, HttpStatus.UNAUTHORIZED, e);
        } catch (Exception e) {
            log.error("doFilterInternal - Exception 발생  : {},  message : {}", request.getRequestURI(), e, e.getMessage());
            //기타 exception 발생 시 400 error response
            createErrorResponse(response, HttpStatus.BAD_REQUEST, e);
        }
        log.info("doFilterInternal 종료");
    }

    private void createErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Exception e) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage())));
    }


}
