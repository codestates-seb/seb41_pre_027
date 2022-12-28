package com.codestates.pre027.PreProjectStackOverFlow.rating.repository;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.rating.entity.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByMemberAndQuestion(Member member, Question question);
    List<Rating> findAllByQuestion(Question question);
    List<Rating> findAllByMemberAndAnswer(Member member, Answer answer);
    List<Rating> findAllByAnswer(Answer answer);
}
