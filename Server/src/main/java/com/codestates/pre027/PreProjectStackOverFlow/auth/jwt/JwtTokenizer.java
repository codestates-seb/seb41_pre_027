package com.codestates.pre027.PreProjectStackOverFlow.auth.jwt;


//import com.codestates.pre027.PreProjectStackOverFlow.auth.redis.RedisDao;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenizer {

//    private final RedisDao redisDao;


    //    JWT 생성 및 검증 시 사용되는 Secret Key 정보입니다
    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    //    Access Token 에 대한 만료 시간 정보입니다
    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    //    Refresh Token 에 대한 만료 시간 정보입니다.
    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    //    secretKey 를 Base64 형식의 문자열로 인코딩
    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //    인증된 사용자에게 JWT 를 최초로 발급해주기 위한 JWT 생성 메서드입니다.
    public String generateAccessToken(Map<String, Object> claims,
        String subject,
        Date expiration,
        String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }

    //    Refresh Token 을 생성하는 메서드입니다.
    public String generateRefreshToken(String subject, Date expiration,
        String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }

    // 검증 후, Claims 을 반환 하는 용도
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jws);
        return claims;
    }

    // 단순히 검증만 하는 용도로 쓰일 경우
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jws);
    }

    //    JWT 의 만료 일시를 지정하기 위한 메서드로 JWT 생성 시 사용
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    //     JWT 의 서명에 사용할 Secret Key 를 생성해줍니다.
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    // Access Token 과 Refresh Token 을 생성하는 구체적인 로직
    protected String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("userEmail", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = getTokenExpiration(
            getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = encodeBase64SecretKey(
            getSecretKey());

        String accessToken = generateAccessToken(claims, subject, expiration,
            base64EncodedSecretKey);

        return accessToken;
    }

    //      Access Token 과 Refresh Token 을 생성하는 구체적인 로직
    protected String delegateRefreshToken(Member member) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());

        String subject = member.getEmail();
        Date expiration = getTokenExpiration(
            getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = encodeBase64SecretKey(
            getSecretKey());

        String refreshToken = generateRefreshToken(subject, expiration,
            base64EncodedSecretKey);

        return refreshToken;
    }

    private Claims parseToken(String token) {
        Key key = getKeyFromBase64EncodedKey(encodeBase64SecretKey(this.secretKey));
        String jws = token.replace("Bearer ", "");

        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jws)
            .getBody();
    }

    // token 에서 memberId 추출
    public Long getMemberId(String token) {
        return parseToken(token).get("memberId", Long.class);
    }

//    public void deleteRtk(Member member) throws JwtException {
//        redisDao.deleteValues(member.getEmail());
//    }
//
//    public String reissueAtk(Member member) throws JwtException {
//        String rtkInRedis = redisDao.getValues(member.getEmail());
//        if (Objects.isNull(rtkInRedis)) {
//            throw new JwtException("인증 정보가 만료되었습니다.");
//        }
//
//        String atk = delegateAccessToken(member);
//        return atk;
//    }
}
