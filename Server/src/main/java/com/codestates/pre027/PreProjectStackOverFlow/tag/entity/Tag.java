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
    @Column(nullable = false)
    private String tagName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tagId;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<QuestionTag> questionTags =new ArrayList<>();


}
