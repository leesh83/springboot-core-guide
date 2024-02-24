package kevin.springboot.core.guide.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kevin.springboot.core.guide.entity.User;
import kevin.springboot.core.guide.service.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtTokenProvider
 * jwt토큰을 생성하는데 필요한 정보를 userDetails 에서 가져와서 토콘을 생성하고,
 * 토큰을 받아와서 검증하고, 토큰에서 유저정보를 조회한 다음 security context에 저장할 인증정보를 생성한다.
 */
@Component
@Slf4j
public class JwtTokenProvider {
    private final UserDetailServiceImpl userDetailService;
    private final String secretKey;
    private final String issuer;
    private final Long tokenVaildMillisecond;

    public JwtTokenProvider(UserDetailServiceImpl userDetailService,
                            @Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.issuer}") String issuer,
                            @Value("${jwt.expire}") Long tokenVaildMillisecond) {
        this.userDetailService = userDetailService;
        this.secretKey = secretKey;
        this.issuer = issuer;
        this.tokenVaildMillisecond = tokenVaildMillisecond;
    }

    //토큰 생성
    public String createToken(User user) {
        log.info("createToken - 시작 secretKey = {}", secretKey);
        Date now = new Date();
        Date expire = new Date(now.getTime() + tokenVaildMillisecond);

        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        //claims.put("role", user.getAuthorities());

        String token = Jwts.builder()
                           .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 : jwt로 설정
                           .setIssuer(issuer)
                           .setIssuedAt(now)
                           .setExpiration(expire)
                           .setClaims(claims)
                           .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 : secretKey 로 HS256 방식으로 암호화
                           .compact();
        log.info("createToken - 완료, token = {}", token);
        return token;
    }

    //token 에서 인증정보를 가져온다, securityContext에 저장할 Authentication 을 생성, 리턴한다.
    public Authentication getAuthentication(String token) {
        log.info("getAuthentication -  시작");
        UserDetails userDetails = userDetailService.loadUserByUsername(getUsername(token));
        log.info("getAuthentication -  조회 완료 username : {}, authorities : {}", userDetails.getUsername(), userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //token 에서 유저식별정보(email) 을 가져온다.
    public String getUsername(String token) {
        return getClaims(token).get("email", String.class);
    }

    //토큰에서 클레임을 추출
    private Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(secretKey)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        log.info("getClaims - claims: {} ", claims);
        return claims;
    }


    //jwt 토큰 유효성 검증
    public boolean validToken(String token) {
        log.info("validToken -  시작 secretKey = {}, token = {}", secretKey, token);
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token); // parseClaimsJwt(token) 시 UnsupportedJwtException "Signed Claims JWSs are not supporte" 가 발생한다.
        return true;
    }
}
