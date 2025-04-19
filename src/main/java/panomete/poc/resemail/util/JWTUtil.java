package panomete.poc.resemail.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtil implements Serializable {
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_ID = "id";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.user.expiration}")
    private Long USER_EXPIRATION;

    @Value("${jwt.admin.expiration}")
    private Long ADMIN_EXPIRATION;

    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_EXPIRATION;

//    public String generateAccessToken(Auth user) {
//        List<String> roles = user.getSimpleAuthorities();
//        String role = roles.getFirst();
//        boolean isAdmin = role.equals("ROLE_ADMIN") || role.equals("ROLE_SUPER_ADMIN");
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(CLAIM_KEY_ID, user.getId().toString());
//        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
//        claims.put(CLAIM_KEY_ROLE, simplifyRole(roles));
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        return generateToken(claims, isAdmin ? ADMIN_EXPIRATION : USER_EXPIRATION);
//    }

//    public String generateRefreshToken(String token, Auth user) {
//        Claims claims = getClaimsFromToken(token);
//        List<String> roles = user.getSimpleAuthorities();
//        claims.put(CLAIM_KEY_ID, user.getId().toString());
//        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
//        claims.put(CLAIM_KEY_ROLE, simplifyRole(roles));
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        return generateToken(claims, REFRESH_EXPIRATION);
//    }

//    public String mockGenerateAccessToken(Auth user, Long expiration) {
//        List<String> roles = user.getSimpleAuthorities();
//        String role = roles.getFirst();
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(CLAIM_KEY_ID, user.getId().toString());
//        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
//        claims.put(CLAIM_KEY_ROLE, simplifyRole(roles));
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        return generateToken(claims, expiration);
//    }

    private String simplifyRole(List<String> role) {
        return role.getFirst().substring(5);
    }

    public String generateToken(Map<String, Object> claims, Long expiration) {
        final Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(generateExpirationDate(expiration))
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        final Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date generateExpirationDate(Long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public Date getCreatedDateFromToken(String token) {
        return new Date((Long) getClaimsFromToken(token).get(CLAIM_KEY_CREATED));
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    public Boolean isTokenValid(String token, Auth user) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(user.getUsername()) && !isTokenExpired(token));
//    }

    public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return created.before(lastPasswordReset);
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return (!isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token));
    }
}
