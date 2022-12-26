package com.codestates.pre027.PreProjectStackOverFlow.auth.filter;


import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.auth.dto.LoginDto;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 클라이언트의 로그인 인증 요청을 처리하는 엔트리포인트(Entrypoint)의 역할
// UsernamePasswordAuthenticationFilter 는 폼로그인 방식에서 사용하는 디폴트 Security Filter
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    //  메서드 내부에서 인증을 시도하는 로직을 구현
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) {

        //  Username 과 Password 를 DTO 클래스로 역직렬화하기 위해 ObjectMapper 인스턴스를 생성
        ObjectMapper objectMapper = new ObjectMapper();

        //  ServletInputStream 을 LoginDto 클래스의 객체로 역직렬화
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        //  Username 과 Password 정보를 포함한 UsernamePasswordAuthenticationToken 을 생성
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        //      UsernamePasswordAuthenticationToken 을 AuthenticationManager 에게 전달
        return authenticationManager.authenticate(authenticationToken);
    }

    //  클라이언트의 인증 정보를 이용해 인증에 성공할 경우 호출
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws ServletException, IOException {

        //  authResult.getPrincipal()로 Member 엔티티 클래스의 객체를 얻습니다.
        Member member = (Member) authResult.getPrincipal();

        //  delegateAccessToken(member) 메서드를 이용해 Access Token 을 생성
        String accessToken = delegateAccessToken(member);

        //  delegateRefreshToken(member) 메서드를 이용해 Refresh Token 을 생성
        String refreshToken = delegateRefreshToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        //  AuthenticationSuccessHandler 의 onAuthenticationSuccess() 메서드를 호출
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    // Access Token 과 Refresh Token 을 생성하는 구체적인 로직
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("userEmail", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(
            jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
            jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration,
            base64EncodedSecretKey);

        return accessToken;
    }

    //      Access Token 과 Refresh Token 을 생성하는 구체적인 로직
    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(
            jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
            jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration,
            base64EncodedSecretKey);

        return refreshToken;
    }
}