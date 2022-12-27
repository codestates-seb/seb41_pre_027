package com.codestates.pre027.PreProjectStackOverFlow.rating.entity;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "RATINGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Column(nullable = true)
    private Boolean upRating;

    @Column(nullable = true)
    private Boolean downRating;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "member_id")
//    private Member member;


    @OneToOne(optional = false)
    @JoinColumn(name = "rating_id")
    private Question question;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "answer_id")
//    private Answer answer;
}
