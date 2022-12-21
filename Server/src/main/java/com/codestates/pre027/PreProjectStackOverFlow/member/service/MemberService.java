package com.codestates.pre027.PreProjectStackOverFlow.member.service;

import com.codestates.pre027.PreProjectStackOverFlow.auth.utils.CustomAuthorityUtils;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    //    MemberRepository 인터페이스 DI
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;


    //    회원 생성
    public Member createMember(Member member) {
        //    회원의 email 을 조회하여 존재하는지 확인 없으면 MEMBER_EXISTS exception 발생
        verifyExistsEmail(member.getEmail());

        //  Password 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        //  DB에 User Role 저장
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        return memberRepository.save(member);
    }

    //    특정 회원 조회
    public Member findMember(long memberId) {
        //    memberId 조회해서 있으면 멤버를 리턴 없으면 MEMBER_NOT_FOUND exception 발생
        return findVerifiedMember(memberId);
    }

    //    전체 회원 조회
    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
            Sort.by("memberId").descending()));
    }

    //    특정 회원 수정
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        findMember.setName(member.getName());
        findMember.setPassword(passwordEncoder.encode(member.getPassword()));

        return  memberRepository.save(findMember);
    }

    //    특정 회원 삭제
    public void deleteMember(long memberId) {
        //    memberId 조회해서 있으면 멤버를 리턴 없으면 MEMBER_NOT_FOUND exception 발생
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }

    //    memberId 조회해서 있으면 멤버를 리턴 없으면 MEMBER_NOT_FOUND exception 발생
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    //    회원의 email 을 조회하여 존재하는지 확인 없으면 MEMBER_EXISTS exception 발생
    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
