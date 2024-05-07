package com.example.capstone.repository;

import com.example.capstone.entity.BoardEntity;
import com.example.capstone.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
    // select * from comment_table where board_id=? order by id desc;
}