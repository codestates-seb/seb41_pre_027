package com.codestates.pre027.PreProjectStackOverFlow.tag.repository;

import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query(value = "SELECT c FROM TAGS c WHERE TAG_NAME = :tagString")
    Optional<Tag> findByTag(String tagString);
}
