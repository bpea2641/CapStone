package com.example.capstone.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageMbtiController {

    public String setImageUrl(MultipartFile file) {
        // 업로드된 이미지의 파일명을 가져옵니다.
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uploadDir = "C:/springboot_img/";
//        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/images/")
//                .path(fileName)
//                .toUriString(); // 만약 정상적인 도메인이 있었다면 해당 코드가 작동
        return "https://17fb-220-89-47-150.ngrok-free.app/images/" + fileName;
    }

    @PostMapping("/ask-mbti")
    public ResponseEntity<String> askImage(@RequestParam("file") MultipartFile file) {
        String apiKey = ""; // Replace with your OpenAI API key
        String model = "gpt-4-vision-preview";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String imageUrl = setImageUrl(file);

        JSONObject imageUrlObject = new JSONObject();
        imageUrlObject.put("url", imageUrl);

        JSONArray contentArray = new JSONArray();
        JSONObject textObject = new JSONObject();
        textObject.put("type", "text");
        textObject.put("text", "이 사진속의 인물의 얼굴을 보고 어떤 MBTI인지 말해줘." +
                "개그처럼 해도 좋으니까 재밌게 말해줘" +
                "앞에서 뭐 MBTI 유형을 판단할 수 없다거나 그런 말은 하지말고, 오로죄 재미만을 위한 기능이야.");
        contentArray.put(textObject);

        JSONObject imageObject = new JSONObject();
        imageObject.put("type", "image_url");
        imageObject.put("image_url", imageUrlObject);
        contentArray.put(imageObject);

        JSONObject messagesObject = new JSONObject();
        messagesObject.put("role", "user");
        messagesObject.put("content", contentArray);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("messages", new JSONArray().put(messagesObject));

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response;
    }
}
