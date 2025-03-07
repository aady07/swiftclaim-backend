package com.aady.swiftclaimai.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.aady.swiftclaimai.service.AIService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ImageController {

 

  @Autowired
  private AIService AIService;

  @PostMapping("/upload")
public ResponseEntity<String> uploadImage(
    @RequestParam("file") MultipartFile file,
    @RequestParam(value = "apiKey", required = false) String apiKey) {
  
  try {

    System.out.println("API Key: " + (apiKey != null ? apiKey : "Not provided"));

    // Process image file using AI service
    Map<String, String> jsonRequest = AIService.processImageFile(file);

    // Call external API with processed data
    String response = AIService.callExternalApi(jsonRequest);

    return ResponseEntity.status(HttpStatus.OK).body(response);
    
  } catch (ResponseStatusException e) {
    // Keep the original HTTP status from AIService
    return ResponseEntity.status(e.getStatus()).body(e.getReason());
} catch (Exception e) {
    String errorMessage = "Error occurred: " + e.getMessage();
    System.out.println(errorMessage);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
  }
}
}
