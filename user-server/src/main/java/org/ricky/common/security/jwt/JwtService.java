package org.ricky.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.ricky.common.properties.JwtProperties;
import org.ricky.common.security.MyAuthenticationToken;
import org.ricky.core.user.domain.User;
import org.ricky.core.user.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.ricky.common.context.UserContext.createUser;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/11
 * @className JwtService
 * @desc
 */
@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    public String generateJwt(String userId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtProperties.getExpire() * 60L * 1000L);
        return generateJwt(userId, expirationDate);
    }

    public String generateJwt(String userId, Date expirationDate) {
        Claims claims = Jwts.claims().setSubject(userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    public MyAuthenticationToken tokenFrom(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(jwt).getBody();
        String userId = claims.getSubject();
        User user = userRepository.cachedById(userId);
        long expiration = claims.getExpiration().getTime();
        return new MyAuthenticationToken(createUser(userId, user.getNickname(), user.getRole()), expiration);
    }

}
