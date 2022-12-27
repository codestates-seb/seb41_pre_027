package com.codestates.pre027.PreProjectStackOverFlow.comment.repository;

import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
