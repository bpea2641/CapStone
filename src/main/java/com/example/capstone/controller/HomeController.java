package com.example.capstone.controller;

import com.example.capstone.dto.BoardDTO;
import com.example.capstone.entity.BoardEntity;
import com.example.capstone.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class HomeController {
    private final BoardService boardService;
    @GetMapping("/")
    public String index() {
        return "LoginSignup/index";
    }

    @GetMapping("/Resi/index")
    public String Resi_index(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        List<BoardEntity> latestPosts = boardService.getLatestPosts(3); // 최신 게시물 3개 가져오기
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("boardList", boardDTOList);
        return "/Resi/index.html";
    }

    @GetMapping("/Resi/inner_page")
    public String Resi_inner_page() {
        return "/Resi/inner_page.html";
    }
}