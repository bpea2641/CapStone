package com.example.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TextToSpeechService {

    @Autowired
    private OpenAiService openAiService;

    public InputStream convertTextToSpeech(String text) {
        try {
            // OpenAI API 호출을 openAiService에 위임하여 처리
            return openAiService.convertTextToSpeech(text);
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 처리
            return null;
        }
    }
}
