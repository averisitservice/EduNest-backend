package com.edunest.configuration;

import com.edunest.entity.Teacher;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtHelper {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Teacher teacher) {
        Date expiration = new Date(System.currentTimeMillis() + jwtExpiration * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("teacherId",   teacher.getTeacherId());
        claims.put("tenantId",    teacher.getTenantId());
        claims.put("roleId",      teacher.getRoleId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(teacher.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(signingKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Teacher parseToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Teacher teacher = new Teacher();
        teacher.setEmail(body.getSubject());
        teacher.setTeacherId(Long.parseLong(body.get("teacherId").toString()));
        teacher.setTenantId(Integer.parseInt(body.get("tenantId").toString()));
        teacher.setRoleId(Long.parseLong(body.get("roleId").toString()));
        return teacher;
    }

    public String cleanToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    public String extractTeacherId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long teacherId = claims.get("teacherId", Long.class);
        return teacherId != null ? teacherId.toString() : null;
    }

    public Integer extractTenantId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("tenantId", Integer.class);
    }
}