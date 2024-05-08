package com.example.capstone.controller;

// LatestPostsController.java

import com.example.capstone.entity.BoardEntity;
import com.example.capstone.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LatestPostsController {

    private final BoardService boardService;

    @Autowired
    public LatestPostsController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/latest-posts")
    public String getLatestPosts(Model model) {
        // 최신 게시물을 가져오는 비즈니스 로직을 수행합니다. (예: 최신 N개의 게시물을 가져오는 메서드)
        List<BoardEntity> latestPosts = boardService.getLatestPosts(5); // 최신 5개의 게시물을 가져옴

        // 최신 게시물을 모델에 추가합니다.
        model.addAttribute("latestPosts", latestPosts);

        // Thymeleaf 템플릿 파일의 경로를 반환합니다. (예: latest-posts.html)
        return "latest-posts";
    }
}


