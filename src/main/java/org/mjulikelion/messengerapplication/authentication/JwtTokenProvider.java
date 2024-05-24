package org.mjulikelion.messengerapplication.authentication;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mjulikelion.messengerapplication.exception.ForbiddenException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;    //JWT Token의 SECRET KEY
    private final long validityInMilliseconds;  //Token 유효시간

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.expire-length}") final long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(final String payload) {
        //Jwt Token 만들기

        Date now = new Date();  //현재 시간
        Date expiration = new Date(now.getTime() + validityInMilliseconds); //만료 시간

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(final String token) {
        //Jwt Token 받아서 검증 후 userId가 담긴 payload를 반환

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            throw new ForbiddenException(ErrorCode.TOKEN_INVALID, e.getMessage());
        }
    }
}
