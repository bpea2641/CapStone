package com.example.capstone.controller;

import com.example.capstone.service.ImageTransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageTransformationController {

    private final ImageTransformationService imageTransformationService;

    // 생성자 주입 사용
    @Autowired
    public ImageTransformationController(ImageTransformationService imageTransformationService) {
        this.imageTransformationService = imageTransformationService;
    }

    @PostMapping("/transform-image")
    public ResponseEntity<String> transformImage(@RequestParam("image") MultipartFile image) {
        String transformedImageUrl = imageTransformationService.transformImage(image);
        return ResponseEntity.ok(transformedImageUrl);
    }
}
