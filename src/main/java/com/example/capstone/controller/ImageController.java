package com.example.capstone.controller;

import com.example.capstone.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    private OpenAiService openAiService;

    @CrossOrigin
    @GetMapping("/image")
    public ResponseEntity<String> generateImage(@RequestParam String prompt) {
        String imageUrl = openAiService.generateImage(prompt);
        return ResponseEntity.ok(imageUrl);
    }
}


