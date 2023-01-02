package com.codestates.pre027.PreProjectStackOverFlow.tag.entity;

import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
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
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long questionTagId;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    public Question question;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    public Tag tag;
}
