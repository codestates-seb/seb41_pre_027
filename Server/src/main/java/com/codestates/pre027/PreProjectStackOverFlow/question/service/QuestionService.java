package com.codestates.pre027.PreProjectStackOverFlow.question.service;

import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.repository.QuestionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository,
        MemberService memberService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question, long tokenId) {
        Member member = memberService.findMember(tokenId);
        question.setMember(member);

        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question, long tokenId) {
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());
        Member findMember = findQuestion.getMember();

        if(findMember.getMemberId() != tokenId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        Optional.ofNullable(question.getTitle())
            .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getText())
            .ifPresent(findQuestion::setText);

        findQuestion.setModifiedAt(LocalDateTime.now());
        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(long questionId) {
        Question findQuestion = findVerifiedQuestion(questionId);
        //  찾은 질문의 views 값에 1을 더함
        int findViews = findQuestion.getViews() + 1;

        // 찾은 질문의 views 값을 할당
        findQuestion.setViews(findViews);

        // repository 에 저장
        questionRepository.save(findQuestion);

        return findQuestion;
    }

    public Page<Question> findQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }



    public Page<Question> searchQuestion(String search, Pageable pageable) {
        Page<Question> questionPage = questionRepository.findByTitleContainingOrTextContaining(search, search, pageable);

        return questionPage;
    }

    public void deleteQuestion(long questionId, long tokenId) {
        Question findQuestion = findVerifiedQuestion(questionId);
        Member findMember = findQuestion.getMember();

        if(findMember.getMemberId() != tokenId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        questionRepository.delete(findQuestion);
    }

    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND)
        );

        return findQuestion;
    }

    public long findQuestionCount() {
        List<Question> questions = questionRepository.findAll();

        return questions.size();
    }

    public long searchQuestionCount(String search) {
        List<Question> questionList = questionRepository.findByTitleContaining(search);
        long questionCount = questionList.size();

        return questionCount;
    }
}
