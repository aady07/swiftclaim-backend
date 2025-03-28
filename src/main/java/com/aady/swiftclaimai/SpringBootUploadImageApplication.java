package com.aady.swiftclaimai;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aady.swiftclaimai.service.FilesStorageService;

@SpringBootApplication
public class SpringBootUploadImageApplication implements CommandLineRunner {

  @Resource
  FilesStorageService storageService;

  public static void main(String[] args) {
    SpringApplication.run(SpringBootUploadImageApplication.class, args);
  }

  @Override
  public void run(String... arg) throws Exception {
//    storageService.deleteAll();
    storageService.init();
  }
}
