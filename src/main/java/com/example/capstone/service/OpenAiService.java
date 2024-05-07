package com.example.capstone.service;

import com.example.capstone.config.ChatGPTConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class OpenAiService {

    @Autowired
    private ChatGPTConfig chatGPTConfig;

    @Autowired
    private RestTemplate restTemplate;

    String apiKey = "";
    public String generateImage(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String apiUrl = "https://api.openai.com/v1/images/generations";
            // ChatGPTConfig에서 API 키 가져오기


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

    public InputStream convertTextToSpeech(String text) throws Exception {
        // OpenAI API를 호출하여 텍스트를 음성으로 변환하고 응답으로 mp3 파일을 받아오는 메서드

        // OpenAI API 엔드포인트
        String endpoint = "https://api.openai.com/v1/audio/speech";

        // OpenAI API에 전송할 요청 바디 생성
        String requestBody = "{\"text\": \"" + text + "\", \"input\": \"" + text + "\", \"voice\": \"nova\", \"model\": \"tts-1\"}";

        // OpenAI API 요청에 필요한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // OpenAI API 키 설정

        // RestTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, byte[].class);

        // API 응답에서 음성 파일을 InputStream으로 반환
        byte[] audioData = responseEntity.getBody();
        return new ByteArrayInputStream(audioData);
    }
}


