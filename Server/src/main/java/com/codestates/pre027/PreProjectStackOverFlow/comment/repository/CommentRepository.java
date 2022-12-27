package com.codestates.pre027.PreProjectStackOverFlow.comment.repository;

import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "SELECT c FROM COMMENTS c WHERE QUEST_QUESTION_ID = :questId")
    List<Comment> findByQuest(long questId);

    @Query(value = "SELECT c FROM COMMENTS c WHERE ANSWER_ANSWER_ID = :answerId")
    List<Comment> findByAnswer(long answerId);
}
