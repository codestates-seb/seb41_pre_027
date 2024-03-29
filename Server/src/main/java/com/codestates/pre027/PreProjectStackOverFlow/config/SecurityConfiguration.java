package com.codestates.pre027.PreProjectStackOverFlow.config;

import static org.springframework.security.config.Customizer.withDefaults;


import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtAuthenticationFilter;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtVerificationFilter;
import com.codestates.pre027.PreProjectStackOverFlow.auth.handler.MemberAccessDeniedHandler;
import com.codestates.pre027.PreProjectStackOverFlow.auth.handler.MemberAuthenticationEntryPoint;
import com.codestates.pre027.PreProjectStackOverFlow.auth.handler.MemberAuthenticationFailureHandler;
import com.codestates.pre027.PreProjectStackOverFlow.auth.handler.MemberAuthenticationSuccessHandler;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.auth.redis.RedisDao;
import com.codestates.pre027.PreProjectStackOverFlow.auth.utils.CustomAuthorityUtils;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisDao redisDao;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().disable()
            .and()
            .csrf().disable()
            .cors(withDefaults())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling()
            .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
            .accessDeniedHandler(new MemberAccessDeniedHandler())
            .and()
            .apply(new CustomFilterConfigurer())
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers(HttpMethod.GET, "/member").permitAll()
                .antMatchers(HttpMethod.POST, "/member").permitAll()
                .antMatchers(HttpMethod.PATCH, "/member/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/member/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/logout").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/questions").permitAll()
                .antMatchers(HttpMethod.POST, "/questions").hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/questions/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/questions/**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/answers/comments").permitAll()
                .antMatchers(HttpMethod.GET,"/questions/comments").permitAll()
                .antMatchers(HttpMethod.GET,"/members/answers").permitAll()
                .antMatchers(HttpMethod.GET,"/questions/answers").permitAll()
                .antMatchers(HttpMethod.POST,"/questions/answers").hasRole("USER")
                .antMatchers(HttpMethod.PATCH,"/answers/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,"/answers/**").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/questions/tags").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/questions/comments").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/answers/comments").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,"/comments/**").hasRole("USER")
                .anyRequest().permitAll()
            );
        return http.build();
    }

    //    PasswordEncoder Bean 객체를 생성합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //    CorsConfigurationSource Bean 생성을 통해 구체적인 CORS 정책을 설정합니다.
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://pre-project-group-27.s3-website.ap-northeast-2.amazonaws.com/");
        configuration.addAllowedOriginPattern("http://localhost:3000");
        configuration.addExposedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //     CustomFilterConfigurer 는 우리가 구현한 JwtAuthenticationFilter 를 등록하는 역할
    public class CustomFilterConfigurer extends
        AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {

            // AuthenticationManager 의 객체를 얻을 수 있습니다.
            AuthenticationManager authenticationManager = builder.getSharedObject(
                AuthenticationManager.class);

            // Spring Security 의 설정을 구성하는 SecurityConfigurer 간에 공유되는 객체 얻기
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                authenticationManager, jwtTokenizer, redisDao);


            // default url 설정
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

            // 인증 성공 후, 로그를 기록하거나 사용자 정보를 response 로 전송할 수 있다.
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(
                new MemberAuthenticationSuccessHandler());

            // 인증 실패 시, 에러 로그를 기록하거나 error response 를 전송할 수 있다.
            jwtAuthenticationFilter.setAuthenticationFailureHandler(
                new MemberAuthenticationFailureHandler());

            // JwtVerificationFilter 에서 사용되는 객체들을 생성자로 DI
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer,
                authorityUtils);

            builder
                // jwtAuthenticationFilter 를 Spring Security Filter Chain 에 추가
                .addFilter(jwtAuthenticationFilter)
                //  인증 과정이 수행된 이후에 JwtVerificationFilter 가 동작
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
