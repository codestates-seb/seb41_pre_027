package com.codestates.pre027.PreProjectStackOverFlow.member.controller;


import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.auth.redis.RedisDao;
import com.codestates.pre027.PreProjectStackOverFlow.dto.CountMultiResponseDto;
import com.codestates.pre027.PreProjectStackOverFlow.member.dto.MemberDto;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.mapper.MemberMapper;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Validated
public class MemberController {

    //    memberMapper 인터페이스 DI
    private final MemberMapper memberMapper;

    //    memberService 클래스 DI
    private final MemberService memberService;

    //    JwtTokenizer 클래스 DI
    private final JwtTokenizer jwtTokenizer;
    private final RedisDao redisDao;


    //    전체 회원 조회하기
    @GetMapping
    public ResponseEntity getMembers(
        @PageableDefault(size = 20, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Member> pageMembers = memberService.findMembers(pageable);
        List<Member> members = pageMembers.getContent();
        long memberCount = memberService.findMemberCount();

        return new ResponseEntity<>(
            new CountMultiResponseDto<>(memberMapper.membersToMemberResponseDtos(members),
                memberCount),
            HttpStatus.OK);
    }

    //    특정 회원 조회하기
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(member), HttpStatus.OK);
    }

    //    회원가입 하기
    @PostMapping
    public ResponseEntity postMember(@RequestBody @Valid MemberDto.Post requestBody) {

        Member member = memberMapper.memberPostDtoToMember(requestBody);

        Member createdMember = memberService.createMember(member);

        MemberDto.Response response = memberMapper.memberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //    회원정보 수정하기
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@RequestHeader(name = "Authorization") String token,
        @PathVariable("member-id") @Positive long memberId,
        @Valid @RequestBody MemberDto.Patch requestBody) {

        requestBody.setMemberId(memberId);

        Member member = memberService.updateMember(memberMapper.memberPatchDtoToMember(requestBody),
            jwtTokenizer.getMemberId(token));

        return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(member), HttpStatus.OK);
    }

    //    회원탈퇴 하기
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@RequestHeader(name = "Authorization") String token,
        @PathVariable("member-id") @Positive long memberId) {

        memberService.deleteMember(memberId, jwtTokenizer.getMemberId(token));

        return ResponseEntity.noContent().build();
    }

    // 회원 닉네임으로 검색하기
    @GetMapping("/search")
    public ResponseEntity searchMember(@RequestParam String search,
        @PageableDefault(size = 20, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Member> searchMemberPage = memberService.searchMember(search, pageable);
        List<Member> searchMember = searchMemberPage.getContent();
        long searchMemberCount = memberService.searchMemberCount(search);

        return new ResponseEntity<>(new CountMultiResponseDto<>(
            memberMapper.membersToMemberResponseDtos(searchMember), searchMemberCount),
            HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        long memberId = jwtTokenizer.getMemberId(token);
        String email = memberService.findMember(memberId).getEmail();
        redisDao.deleteValues(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reissue")
    public ResponseEntity reissue(@RequestHeader("Refresh") String token) throws JwtException {

        long memberId = jwtTokenizer.getMemberId(token);
        Member member = memberService.findMember(memberId);
        String rtk = redisDao.getValues(member.getEmail());

        if(Objects.isNull(rtk)){
            throw new JwtException("토큰 만료");
        }

        jwtTokenizer.deleteRtk(member);
        String atk = jwtTokenizer.reissueAtk(member);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + atk);

        return ResponseEntity.ok().headers(headers).build();
    }
}

