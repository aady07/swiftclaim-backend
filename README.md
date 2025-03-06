# Spring Boot API Project

This project is a **Spring Boot** application that provides multiple API endpoints for handling various requests. It integrates with external services and processes data efficiently using **PM XML**.

## Features
- RESTful API endpoints
- External integrations
- XML and JSON support
- Secure and scalable architecture

## API Endpoints

### Upload Image
Uploads an image file and sends it to an external API.

#### **Request**
- **Method:** `POST`
- **URL:** `/api/upload`
- **Headers:**
  ```http
  Content-Type: multipart/form-data

  {
  "status": "success",
  "message": "Image processed successfully",
  "response": "<external_api_response>"
}

{
  "status": "error",
  "message": "Could not upload the image: <file_name>. Error: <error_details>"
}

