package com.codestates.pre027.PreProjectStackOverFlow.answer.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column(nullable = true)
    private String imgURL;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    /*@ManyToOne
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
    }*/
}
