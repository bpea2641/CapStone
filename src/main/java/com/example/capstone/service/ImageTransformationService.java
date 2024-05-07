package com.example.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageTransformationService {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "https://api.openai.com/v1/images/variations"; // OpenAI API URL
    private final String model = "dall-e-2"; // 사용할 OpenAI 모델
    private final String apiKey = ""; // OpenAI API 키
    private final int n = 1; // 이미지 생성 수
    private final String size = "128x128"; // 이미지 크기
    // TODO: 외부 환경 변수로부터 API 키 읽어오기

    // 이미지를 변형하고 변형된 이미지의 URL을 반환하는 메서드
    public String transformImage(MultipartFile image) {
        try {
            // OpenAI API에 전송할 데이터 설정
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", image.getResource());

            // OpenAI API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setBearerAuth(apiKey); // OpenAI API 키 설정

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?model=" + model + "&n=" + n + "&size=" + size, HttpMethod.POST, requestEntity, String.class);

            // 이미지 변형 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                // 응답에서 이미지 URL 추출
                return response.getBody();
            } else {
                // 에러 처리
                throw new RuntimeException("Failed to transform image: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to transform image", e);
        }
    }
}
