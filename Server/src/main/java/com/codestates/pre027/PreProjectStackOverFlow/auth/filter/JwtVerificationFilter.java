package com.codestates.pre027.PreProjectStackOverFlow.auth.filter;



import com.codestates.pre027.PreProjectStackOverFlow.auth.utils.CustomAuthorityUtils;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        //  verifyJws() = JWT 를 검증하는데 사용되는 메서드
        //  setAuthenticationToContext() = Authentication 객체를 SecurityContext 에 저장하기 위한 메서드
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    //  verifyJws() = JWT 를 검증하는데 사용되는 메서드
    private Map<String, Object> verifyJws(HttpServletRequest request) {

        //  request 의 header 에서 JWT 를 얻어오기
        String jws = request.getHeader("Authorization").replace("Bearer ", "");

        //  JWT 서명(Signature)을 검증하기 위한 Secret Key 를 얻습니다.
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
            jwtTokenizer.getSecretKey());

        //  JWT 에서 Claims 를 파싱합니다.
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    //  setAuthenticationToContext() = Authentication 객체를 SecurityContext 에 저장하기 위한 메서드
    private void setAuthenticationToContext(Map<String, Object> claims) {

        //  JWT 에서 파싱한 Claims 에서 username 을 얻습니다.
        String username = (String) claims.get("userEmail");

        //  JWT 의 Claims 에서 얻은 권한 정보를 기반으로 List<GrantedAuthority 를 생성합니다.
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(
            (List) claims.get("roles"));

        //  username 과 List<GrantedAuthority 를 포함한 Authentication 객체를 생성합니다.
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
            authorities);

        //  SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
