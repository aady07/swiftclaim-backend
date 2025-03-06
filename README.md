SwiftClaimAI Backend
====================

Overview
--------

SwiftClaimAI Backend is a Spring Boot application that provides an API for processing insurance claim images. The application receives image uploads, encodes them to Base64, and forwards them to an external claim processing service.

Technology Stack
----------------

*   Java 17
    
*   Spring Boot 2.7.6
    
*   Maven
    

API Endpoints
-------------

### Image Upload API

*   **URL**: /api/upload
    
*   **Method**: POST
    
*   **Content-Type**: multipart/form-data
    
*   **Request Parameter**:
    
    *   file: The image file to be processed
        

#### Request Format

The API accepts image files through a multipart form data request.

#### Response

The API forwards the response received from the external claim processing service.

#### Error Handling

In case of errors, the API returns an appropriate HTTP status code along with an error message.

External API Integration
------------------------

The application communicates with an external API endpoint at https://claim.aadyserver.tech/api by sending a JSON payload with the following structure:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   jsonCopy{    "name": "filename_without_extension",    "file_name": "original_filename.extension",    "file_format": "extension",    "file_data": "base64_encoded_content"  }   `

Features
--------

*   Cross-origin resource sharing (CORS) enabled
    
*   Image to Base64 conversion
    
*   Request and response logging to separate files
    
*   Error handling and reporting
    

Logging
-------

The application logs both requests and responses to:

*   api\_request.log: Contains details of requests sent to the external API
    
*   api\_response.log: Contains responses received from the external API
    

Configuration
-------------

The application uses default Spring Boot configuration. Key properties can be set in the application.properties file.

Building the Application
------------------------

To build the application:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   bashCopymvn clean install   `

Running the Application
-----------------------

To run the application:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   bashCopymvn spring-boot:run   `

Or after building:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   bashCopyjava -jar target/spring-boot-upload-image-0.0.1-SNAPSHOT.jar   `

Dependencies
------------

*   spring-boot-starter-web: For REST controller implementation
    
*   spring-boot-devtools: For development utilities
    
*   spring-boot-starter-test: For testing
    

Project Structure
-----------------

*   ImageController.java: REST controller handling image upload and processing
    
*   FilesStorageService: Service for file storage (referenced in the controller)
    

Error Handling
--------------

The application provides detailed error messages when image processing fails, including the original filename and the specific error encountered.

Version 1 of 2