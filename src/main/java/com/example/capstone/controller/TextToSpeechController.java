package com.example.capstone.controller;

import com.example.capstone.service.TextToSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextToSpeechController {

    @Autowired
    private TextToSpeechService textToSpeechService;

    @PostMapping("/v1/audio/speech")
    public ResponseEntity<?> convertTextToSpeech(@RequestBody String text) {
        try {
            byte[] speechData = textToSpeechService.convertTextToSpeech(text).readAllBytes();
            // 음성 파일을 클라이언트에 반환
            return ResponseEntity.ok().body(speechData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate speech");
        }
    }
}
