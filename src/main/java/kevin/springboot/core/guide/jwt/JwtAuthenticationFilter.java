package kevin.springboot.core.guide.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal 시작");

        String authorizationHeader = request.getHeader(authrizationHeaderName);

        //jwt 토큰이 헤더에 있는 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            //jwt 토큰 유효성 검증 통과 시
            if (jwtTokenProvider.validToken(token)) {
                //토큰에서 인증정보 불러옴
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                //현재 request 의 SecurityContext에 인증정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("doFilterInternal - SecurityContextHolder에 인증정보 저장");
            }
        }
        filterChain.doFilter(request, response); // 다음 필터로 넘긴다.
        log.info("doFilterInternal 종료");
    }
}
