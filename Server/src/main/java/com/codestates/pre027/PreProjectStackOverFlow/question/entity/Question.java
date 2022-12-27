package com.codestates.pre027.PreProjectStackOverFlow.question.entity;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.QuestionTag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "QUESTIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

//    private Object image;

    @Column(nullable = false)
    private int views = 0;

    @Column(nullable = false)
    private int rating = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "quest", cascade = CascadeType.PERSIST)
    private List<Answer> answerList = new ArrayList<>();

    @OneToMany(mappedBy = "quest", cascade = CascadeType.PERSIST)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<QuestionTag> questionTags =new ArrayList<>();

    public void addAnswer(Answer answer){
        answerList.add(answer);
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;


    public Question(long questionId, String title, String text, int views, int rating, LocalDateTime createdAt , LocalDateTime modifiedAt) {
        this.questionId = questionId;
        this.title = title;
        this.text = text;
        this.views = views;
        this.rating = rating;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
