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
import org.springframework.data.domain.Pageable;
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

        //  db에 member 저장
        return memberRepository.save(member);
    }

    //    특정 회원 조회
    public Member findMember(long memberId) {
        //    memberId 조회해서 있으면 멤버를 리턴 없으면 MEMBER_NOT_FOUND exception 발생
        return findVerifiedMember(memberId);
    }

    //    전체 회원 조회
    public Page<Member> findMembers(Pageable pageable) {

        //  db 에서 Pageable 기준으로 멤버 전체 조회
        return memberRepository.findAll(pageable);
    }

    //  전체 회원 수 조회
    public long findMemberCount() {

        // db 에서 전체 멤버 조회 후 List 에 담기
        List<Member> members = memberRepository.findAll();

        // db 에서 받아온 리스트의 size 반환
        return members.size();
    }

    //    특정 회원 수정
    public Member updateMember(Member member, long tokenId) {

        //  수정하려는 member 의 memberId 와 접속해있는 token 의 memberId 비교
        if (member.getMemberId() != tokenId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        //  수정하려는 member 가 존재하는지 memberId로 조회
        Member findMember = findVerifiedMember(member.getMemberId());

        //  null 값이 아니면 회원 닉네임 수정
        Optional.ofNullable(member.getName())
            .ifPresent(findMember::setName);

        //  null 값이 아니면 패스워드 암호화후 회원 패스워드 수정
        Optional.ofNullable(passwordEncoder.encode(member.getPassword()))
            .ifPresent(findMember::setPassword);

        //  db에 member 저장
        return  memberRepository.save(findMember);
    }

    //    특정 회원 삭제
    public void deleteMember(long memberId, long tokenId) {

        //  삭제하려는 memberId 와 접속해있는 token 의 memberId 비교
        if (memberId != tokenId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }
        //  memberId 조회해서 있으면 멤버를 리턴 없으면 MEMBER_NOT_FOUND exception 발생
        Member findMember = findVerifiedMember(memberId);

        //  db 에서 member 삭제
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
    //  회원의 닉네임으로 회원 검색하기
    public Page<Member> searchMember(String search , Pageable pageable) {
        Page<Member> memberPage = memberRepository.findByNameContaining(search , pageable);
        return memberPage;
    }

    //  닉네임으로 검색된 회원 수 조회
    public long searchMemberCount(String search) {

        List<Member> memberList = memberRepository.findByNameContaining(search);
        long memberCount = memberList.size();

        return memberCount;
    }
}
