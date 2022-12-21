package com.codestates.pre027.PreProjectStackOverFlow.auth.userdetails;


import com.codestates.pre027.PreProjectStackOverFlow.auth.utils.CustomAuthorityUtils;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.repository.MemberRepository;
import java.util.Collection;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// 데이터베이스에서 조회한 Member 의 인증 정보를 기반으로 인증을 처리하는 Custom UserDetailsService
@Component
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

//    UserDetailsService 인터페이스를 implements 하는구현 클래스는
//    loadUserByUsername(String username)이라는 추상 메서드를 구현해야 합니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(
            ExceptionCode.MEMBER_NOT_FOUND));

        return new MemberDetails(findMember);
    }

//    데이터베이스에서 조회한 회원 정보를 Spring Security 의 User 정보로
//    변환하는 과정과 Member 의 권한 정보를 생성하는 과정을 캡슐화
    private final class MemberDetails extends Member implements UserDetails {
        MemberDetails(Member member) {
            setMemberId(member.getMemberId());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }

//        데이터베이스에서 조회한 회원의 이메일 정보를 이용해 Role 기반의 권한 정보 컬렉션을 생성
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
