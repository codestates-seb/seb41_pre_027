package com.codestates.pre027.PreProjectStackOverFlow.member.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.member.dto.MemberDto;
import com.codestates.pre027.PreProjectStackOverFlow.member.dto.MemberDto.Response;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    //    controller 에서 받아오는 MemberDto.Post 를 Member 로 변환
    Member memberPostDtoToMember(MemberDto.Post requestBody);

    //    controller 에서 받아오는 MemberDto.Patch 를 Member 로 변환
    Member memberPatchDtoToMember(MemberDto.Patch requestBody);

    //    member 클래스를 MemberDto.Response 클래스로 변환
    MemberDto.Response memberToMemberResponseDto(Member member);

    //    member List 를 Response List 로 변환
    List<Response> membersToMemberResponseDtos(List<Member> members);
}
