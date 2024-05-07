package com.example.capstone.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ImageEditController {

    @PostMapping("/editImage")
    @ResponseBody
    public String handleFormUpload(@RequestParam("imageFile") MultipartFile imageFile,
                                   @RequestParam("maskFile") MultipartFile maskFile,
                                   @RequestParam("prompt") String prompt,
                                   @RequestParam("n") int n,
                                   @RequestParam("size") String size) {
        String apiUrl = "https://api.openai.com/v1/images/edits";
        String apiKey = ""; // your OpenAI API key
        String result = "";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);

            // Create multipart request entity
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("image", imageFile.getBytes(), ContentType.DEFAULT_BINARY, imageFile.getOriginalFilename());
            builder.addBinaryBody("mask", maskFile.getBytes(), ContentType.DEFAULT_BINARY, maskFile.getOriginalFilename());
            builder.addTextBody("prompt", prompt, ContentType.TEXT_PLAIN);
            builder.addTextBody("n", String.valueOf(n), ContentType.TEXT_PLAIN);
            builder.addTextBody("size", size, ContentType.TEXT_PLAIN);

            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            // Execute HTTP request
            HttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            // Get response body as string
            result = EntityUtils.toString(responseEntity);
        } catch (IOException e) {
            e.printStackTrace();
            result = "Error occurred while processing image editing";
        }

        return result; // 이미지 편집 결과를 반환
    }
}
