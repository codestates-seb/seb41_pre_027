package com.codestates.pre027.PreProjectStackOverFlow.member.controller;


import com.codestates.pre027.PreProjectStackOverFlow.member.dto.MemberDto;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.mapper.MemberMapper;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.dto.MultiResponseDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Validated
public class MemberController {

    //    memberMapper 인터페이스 DI
    private final MemberMapper memberMapper;

    //    memberService 클래스 DI
    private final MemberService memberService;


    //    전체 회원 조회하기
    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
        @Positive @RequestParam int size) {

        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(
            new MultiResponseDto<>(memberMapper.membersToMemberResponseDtos(members),
                pageMembers),
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
    public ResponseEntity postMember(@RequestBody MemberDto.Post requestBody) {

        Member member = memberMapper.memberPostDtoToMember(requestBody);

        Member createdMember = memberService.createMember(member);

        MemberDto.Response response = memberMapper.memberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //    회원정보 수정하기
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
        @Valid @RequestBody MemberDto.Patch requestBody) {

        requestBody.setMemberId(memberId);

        Member member =
            memberService.updateMember(memberMapper.memberPatchDtoToMember(requestBody));

        return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(member),HttpStatus.OK);
    }

    //    회원탈퇴 하기
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.noContent().build();
    }
}
