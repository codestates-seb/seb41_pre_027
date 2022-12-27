package com.codestates.pre027.PreProjectStackOverFlow.comment.entity;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean isInheritQuest;

    @ManyToOne
    private Member writer;

    @ManyToOne
    private Question quest;

    @ManyToOne
    private Answer answer;
}
