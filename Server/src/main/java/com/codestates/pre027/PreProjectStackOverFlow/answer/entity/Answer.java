package com.codestates.pre027.PreProjectStackOverFlow.answer.entity;

import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Column(nullable = false)
    private long questionId;

    @ManyToOne
    @JoinColumn(name = "member-id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "question-id")
    private Question question;

    public void addMember(Member member){
        this.member = member;
    }

    public void addQuestion(Question question){
        this.question = question;
    }
}
