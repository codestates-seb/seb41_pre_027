package com.codestates.pre027.PreProjectStackOverFlow.favorite.service;

import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.entity.Favorite;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.repository.FavoriteRepository;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberService memberService;

    public Favorite createFavorite(Favorite favorite,long memberId, long tokenId) {
        System.out.println("###########################서비스 도착");

        if (memberId != tokenId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        Member member = memberService.findMember(memberId);
        favorite.setMember(member);

//        for(int i=0; i<favorite.getMember().getFavoriteList().size(); i++) {
//            if(favorite.getMember().getFavoriteList().get(i) == favorite){
//                favoriteRepository.delete(favorite);
//            }
//        }

        //  db에 favorite 저장
        return favoriteRepository.save(favorite);
    }
}
