package com.codestates.pre027.PreProjectStackOverFlow.tag.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TAGS")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Column(nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST)
    private List<QuestionTag> questionTags =new ArrayList<>();


}
