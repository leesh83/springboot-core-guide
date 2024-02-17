package kevin.springboot.core.guide.jwt;


import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import kevin.springboot.core.guide.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final UserDetailServiceImpl userDetailService;

    @Value("${jwt.secret}")
    private final String secretKey;
    @Value("${jwt.issuer}")
    private final String issuer;
    private final Long tokenVaildMillisecond = 1000L * 60 * 60;

    //토큰 생성
    public String createToken(String email, List<String> roles) {
        log.info("createToken - 시작");
        Date now = new Date();
        Date expire = new Date(now.getTime() + tokenVaildMillisecond);

        String token = Jwts.builder()
                           .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 : jwt로 설정
                           .setIssuer(issuer)
                           .setIssuedAt(now)
                           .setExpiration(expire)
                           .setSubject(email) //subject 에 유저식별정보 (email)을 입력
                           .claim("roles", roles)
                           .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 : secretKey 로 HS256 방식으로 암호화
                           .compact();
        log.info("createToken - 완료");
        return token;
    }

    //token 에서 인증정보를 가져온다, securityContext에 저장할 Authentication 을 생성, 리턴한다.
    public Authentication getAuthentication(String token) {
        log.info("getAuthentication -  시작");
        UserDetails userDetails = userDetailService.loadUserByUsername(getUsername(token));
        log.info("getAuthentication -  username 조회 완료 username : {}, authorities : {}", userDetails.getUsername(), userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //token 에서 유저식별정보(email) 을 가져온다.
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    //토큰에서 클레임을 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(secretKey)
                   .parseClaimsJwt(token)
                   .getBody();
    }

    //http request 헤더에서 token 값 추출
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH_TOKEN");
    }


    //jwt 토큰 유효성 검증
    public boolean validToken(String token) {
        log.info("validToken -  시작");
        try {
            return getClaims(token).getExpiration()
                                   .before(new Date());
        } catch (Exception e) {
            log.info("validToken - exception occured : {}", e.getMessage());
            return false; //exception 발생시 유효하지 않은 토큰으로 판단.
        }
    }


}
