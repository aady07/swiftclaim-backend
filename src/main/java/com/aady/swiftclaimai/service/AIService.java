package com.aady.swiftclaimai.service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AIService {

    public String callExternalApi(Map<String, String> request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> httpRequest = new HttpEntity<>(request, headers);

        return restTemplate.postForObject("https://ai.aadyserver.tech/predict", httpRequest, String.class);
    }

    public Map<String, String> processImageFile(MultipartFile file, String make, String model) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileFormat = getFileFormat(originalFilename);

        if (!isValidImageFormat(fileFormat)) {
            
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported file format. Only JPG, JPEG, and PNG are allowed.");
        }

        String base64Image = encodeFileToBase64(file);

        Map<String, String> jsonRequest = new HashMap<>();
        jsonRequest.put("file_name", originalFilename);
        jsonRequest.put("file_format", fileFormat);
        jsonRequest.put("file_data", base64Image);
        
        jsonRequest.put("make", make);
        jsonRequest.put("model", model);

        return jsonRequest;
    }

    private String encodeFileToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = StreamUtils.copyToByteArray(file.getInputStream());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private String getFileFormat(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }

    private boolean isValidImageFormat(String format) {
        return format.equals("jpg") || format.equals("jpeg") || format.equals("png");
    }
}