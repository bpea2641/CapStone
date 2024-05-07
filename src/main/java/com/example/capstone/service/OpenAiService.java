package com.example.capstone.service;

import com.example.capstone.config.ChatGPTConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {

    @Autowired
    private ChatGPTConfig chatGPTConfig;

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "https://api.openai.com/v1/images/generations";

    public String generateImage(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // ChatGPTConfig에서 API 키 가져오기
            String apiKey = "";

            // OpenAI API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Prompt를 JSON으로 래핑하여 요청 본문에 추가
            String requestBody = "{\"prompt\": \"" + prompt + "\", \"n\": 1, \"size\": \"512x512\"}";
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, new HttpEntity<>(requestBody, headers), String.class);

            // 이미지 URL 추출
            String imageUrl = response.getBody();

            JSONObject json = new JSONObject();
            json.put("url", imageUrl);
            return json.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating image";
        }
    }
}


