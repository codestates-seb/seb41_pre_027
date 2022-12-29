package com.codestates.pre027.PreProjectStackOverFlow.member.entity;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.entity.Favorite;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long memberImage;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Favorite> favoriteList = new ArrayList<>();

    public void addFavorite(Favorite favorite) {
        favoriteList.add(favorite);
    }

    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answerList.add(answer);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Question> questionList = new ArrayList<>();

    public void addQuestion(Question question) {
        questionList.add(question);
    }

    public Member(Long memberId, String email, String password, String name, long memberImage) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberImage = memberImage;
    }
}
