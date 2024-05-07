package com.example.capstone.controller;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Transactional
public class ChatbotController {
    @GetMapping("/chatgpt/chatbot_main")
    public String chatbot_main() {
        return "/chatgpt/chatbot_main";
    }
    @GetMapping("/chatgpt/chatbot_psychology")
    public String chatbot_psychology() {
        return "/chatgpt/chatbot_psychology";
    }
    @GetMapping("/chatgpt/chatbot_story")
    public String chatbot_story() {
        return "/chatgpt/chatbot_story";
    }
    @GetMapping("/chatgpt/chatbot_image")
    public String chatbot_image() {
        return "/chatgpt/chatbot_image";
    }
    @GetMapping("/chatgpt/chatbot_image_transformation")
    public String chatbot_image_transformation() {
        return "/chatgpt/chatbot_image_transformation";
    }
    @GetMapping("/chatgpt/chatbot_image_text")
    public String chatbot_image_text() {
        return "/chatgpt/chatbot_image_text";
    }
    @GetMapping("/chatgpt/chatbot_image_edit")
    public String chatbot_image_edit() {
        return "/chatgpt/chatbot_image_edit";
    }
}
