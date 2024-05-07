package com.example.capstone.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;

public class SpeechToTextService {

    private static final String OPENAI_API_KEY = "";

    // 오디오를 텍스트로 변환하는 메서드
    public static String transcribeSpeech(byte[] audioData) {
        String url = "https://api.openai.com/v1/audio/transcriptions"; // OpenAI API 엔드포인트 URL
        String model = "whisper-1"; // 사용할 모델

        // OpenAI API 키 설정
        String apiKey = "Bearer " + OPENAI_API_KEY;

        // HttpHeaders를 사용하여 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", apiKey);

        // 요청 본문 데이터 구성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // ByteArrayResource로 변환하여 추가
        ByteArrayResource resource = new ByteArrayResource(audioData) {
            @Override
            public String getFilename() {
                return "audio.wav";
            }
        };
        body.add("file", resource);

        // 모델 매개변수 추가
        body.add("model", model);

        // HttpEntity를 사용하여 헤더와 요청 본문을 함께 설정
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // RestTemplate을 사용하여 HTTP POST 요청 전송
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException.BadRequest e) {
            // Bad Request 오류 처리
            return "400 Bad Request: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            // 예외 처리
            return "Error: " + e.getMessage();
        }

        // 변환된 텍스트 반환
        return response.getBody();
    }
}
