package com.aady.swiftclaimai.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.aady.swiftclaimai.service.FilesStorageService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ImageController {

  @Autowired
  FilesStorageService storageService;

  private static final Logger logger = Logger.getLogger(ImageController.class.getName());

  @PostMapping("/upload")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    FileHandler responseFileHandler = null;
    FileHandler requestFileHandler = null;
    try {
      // Set up the logger to write the response to a file
      responseFileHandler = new FileHandler("api_response.log", true);
      responseFileHandler.setFormatter(new SimpleFormatter());
      logger.addHandler(responseFileHandler);

      // Set up the logger to write the request to a different file
      requestFileHandler = new FileHandler("api_request.log", true);
      requestFileHandler.setFormatter(new SimpleFormatter());
      logger.addHandler(requestFileHandler);

      System.out.println("Received file: " + file.getOriginalFilename());

      // Convert the file to Base64
      String base64Image = encodeFileToBase64(file);
      System.out.println("Base64 encoding completed.");

      // Extract the file name and format
      String originalFilename = file.getOriginalFilename();
      String fileName = originalFilename;
      String fileFormat = "";
      
      if (originalFilename != null && originalFilename.contains(".")) {
        fileFormat = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        // Get the file name without extension
        fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
      }

      // Prepare JSON request with all required tags
      Map<String, String> jsonRequest = new HashMap<>();
      jsonRequest.put("name", fileName);
      jsonRequest.put("file_name", originalFilename);
      jsonRequest.put("file_format", fileFormat);
      jsonRequest.put("file_data", base64Image);

      // Log the request to a file (excluding the actual file data to avoid huge logs)
      Map<String, String> logRequest = new HashMap<>(jsonRequest);
      logRequest.put("file_data", "[BASE64_DATA]");
      logger.info("Request to external service: " + logRequest.toString());

      // Send JSON request to the specified endpoint
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonRequest, headers);

      String response = restTemplate.postForObject("https://claim.aadyserver.tech/api", request, String.class);
      System.out.println("Response from external service: " + response);

      // Log the response to a file
      logger.info("Response from external service: " + response);

      // Return the response from the external service to the user
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      String message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
      System.out.println("Error occurred: " + message);
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    } finally {
      if (responseFileHandler != null) {
        responseFileHandler.close();
      }
      if (requestFileHandler != null) {
        requestFileHandler.close();
      }
    }
  }

  private String encodeFileToBase64(MultipartFile file) throws IOException {
    byte[] fileContent = StreamUtils.copyToByteArray(file.getInputStream());
    return Base64.getEncoder().encodeToString(fileContent);
  }
}
