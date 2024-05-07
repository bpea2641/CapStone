package com.example.capstone.controller;

import com.example.capstone.service.SpeechToTextService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class SpeechToTextController {

    @PostMapping("/transcribe")
    public String transcribeSpeech(@RequestParam("file") MultipartFile file) {
        try {
            byte[] audioData = file.getBytes();
            String transcript = SpeechToTextService.transcribeSpeech(audioData);
            return transcript;
        } catch (IOException e) {
            e.printStackTrace();
            return "변환 중 오류가 발생했습니다.";
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "변환 중 오류가 발생했습니다.";
        }
    }
}
