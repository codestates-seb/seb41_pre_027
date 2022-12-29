package com.codestates.pre027.PreProjectStackOverFlow.favorite.service;

import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.entity.Favorite;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.repository.FavoriteRepository;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberService memberService;
    private final QuestionService questionService;

    public Favorite createFavorite(Favorite favorite,long memberId, long tokenId) {
        System.out.println("###########################서비스 도착");

        if (memberId != tokenId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        questionService.findVerifiedQuestion(favorite.getFavorite());

        Member member = memberService.findMember(memberId);

        favorite.setMember(member);

        Favorite searchFavorite = favoriteRepository.findByFavoriteAndMember(favorite.getFavorite(), tokenId);

        if(searchFavorite != null){
            favoriteRepository.delete(searchFavorite);
        }

        return favoriteRepository.save(favorite);
    }
}
