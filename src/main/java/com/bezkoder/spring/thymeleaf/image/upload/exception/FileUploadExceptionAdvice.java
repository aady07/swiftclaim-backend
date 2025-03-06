package com.bezkoder.spring.thymeleaf.image.upload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.bezkoder.spring.thymeleaf.image.upload.model.ResponseMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException e) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
        .body(new ResponseMessage("File is too large!"));
  }
}
