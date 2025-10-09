package com.example.javaIO.service;


//import com.planbow.utility.MultipartInputStreamFileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
//
//@Service
//public class UnstractApiService {
//
////    private FileStorageServices storageServices;
////
////    @Autowired
////    public void setStorageServices(FileStorageServices storageServices) {
////        this.storageServices = storageServices;
////    }
//
//    private  RestTemplate restTemplate;
//
//    @Autowired
//    public void setRestTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
///*
//    @Autowired
//    public void setRestTemplate() {
//        // RestTemplate with Apache HttpComponents factory
//        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//    } */
//
//
//    private static final String UNSTRACT_URL = "https://us-central.unstract.com/deployment/api/org_kSuAb3rXXvOxXFMJ/poc_2_wo_1759300408741/";
//    private static final String AUTH_TOKEN = "Bearer 1a283057-9915-47a2-bbb0-a895a8e104f9";
//
////    public String callUnstractApi(MultipartFile file) throws IOException {
////        if (file == null || file.isEmpty()) {
////            throw new IllegalArgumentException("File is missing or empty");
////        }
////        System.out.println("file: " + file);
////        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
////       /* body.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));*/
//    ///*
////        //  MultipartFile ko temp file me write karo
////        File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
////        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
////            fos.write(file.getBytes());
////        }
////        body.add("files", new FileSystemResource(tempFile));
////*/
////
////        body.add("files", new ByteArrayResource(file.getBytes()) {
////            @Override
////            public String getFilename() {
////                return file.getOriginalFilename();
////            }
////        });
////        System.out.println("filename: " + file.getOriginalFilename());
////        body.add("timeout", "300");
////        body.add("include_metadata", "False");
////        body.add("include_metrics", "False");
////        System.out.println("body: " + body);
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", AUTH_TOKEN);
////        // Don't set Content-Type explicitly
////
////        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
////        System.out.println("request: " + request);
////        ResponseEntity<String> response = restTemplate.exchange(UNSTRACT_URL, HttpMethod.POST, request, String.class);
////        System.out.println("response: " + response);
////
////        // 5️⃣ Temp file delete
////      /*  tempFile.delete(); */
////        return response.getBody();
////    }
//
//    public String callUnstractApi(MultipartFile file) throws IOException {
//        if (file == null || file.isEmpty()) {
//            throw new IllegalArgumentException("File is missing or empty");
//        }
//
//        File tempFile = storageServices.convertFromMultiPartToFile(file);
//        //1️⃣ MultipartFile ko temp file me likho
////    File tempFile = File.createTempFile("upload-", "-" + file.getOriginalFilename());
////    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
////        fos.write(file.getBytes());
////    }
//
////    final ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
////        @Override
////        public String getFilename() {
////            // filename MUST be provided for many servers
////            return file.getOriginalFilename();
////        }
////
////        @Override
////        public long contentLength() {
////            try {
////                return file.getSize();
////            } catch (Exception ex) {
////                return super.contentLength();
////            }
////        }
////    };
//
//        // 2️⃣ Body prepare karo
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("files", new FileSystemResource(tempFile)); // ✅ guaranteed headers
//        body.add("timeout", "300");
//        body.add("include_metadata", "False");
//        body.add("include_metrics", "False");
//
//        // 3️⃣ Headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", AUTH_TOKEN);
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
//
//        // 4️⃣ Custom RestTemplate with timeout
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setConnectTimeout(300000); // still int in millis
//
////    RestTemplate restTemplate = new RestTemplate(factory);
//
//        // 5️⃣ Call API
//        ResponseEntity<String> response =
//                restTemplate.exchange(UNSTRACT_URL, HttpMethod.POST, request, String.class);
//
//        // 6️⃣ Temp file cleanup
////    tempFile.delete();
//
//        return response.getBody();
//    }
//
//
//
////    public String callUnstractApiHC(File file) throws IOException {
////        if (!file.exists()) {
////            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
////        }
////        System.out.println("file: " + file.getAbsolutePath());
////        // 1️⃣ Body prepare karo
////        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
////        body.add("files", new FileSystemResource(file)); // ✅ exactly curl behaviour
////        body.add("timeout", "300"); // ✅ curl me bhi empty string hai
////        body.add("include_metadata", "False");
////        body.add("include_metrics", "False");
////        System.out.println("body: " + body);
////        // 2️⃣ Headers
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", AUTH_TOKEN);
////        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
////        System.out.println("File exists? " + file.exists() + " | Path: " + file.getAbsolutePath());
////
////        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
////        System.out.println("request: " + request);
////        // 3️⃣ RestTemplate with timeout
////        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
////        factory.setConnectTimeout(300000);
////
////
////        RestTemplate restTemplate = new RestTemplate(factory);
////
////        // 4️⃣ Call API
////        ResponseEntity<String> response = restTemplate.exchange(UNSTRACT_URL, HttpMethod.POST, request, String.class);
////        System.out.println("response: " + response);
////        return response.getBody();
////    }
//
//
//    public String callUnstractApiHC(File file) throws IOException {
//        if (!file.exists()) {
//            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
//        }
//        System.out.println("file: " + file.getAbsolutePath());
//
//        // ✅ File ko byte[] me convert karo
//        byte[] fileContent = Files.readAllBytes(file.toPath());
//
//        // ✅ Body prepare karo (sirf content + filename)
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("files", new ByteArrayResource(fileContent) {
//            @Override
//            public String getFilename() {
//                return file.getName(); // sirf filename, path nahi
//            }
//        });
//        body.add("timeout", "300");
//        body.add("include_metadata", "False");
//        body.add("include_metrics", "False");
//
//        // ✅ Headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", AUTH_TOKEN);
////        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
//
//        // ✅ RestTemplate with timeout
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setConnectTimeout(300000);
//
//        RestTemplate restTemplate = new RestTemplate(factory);
//
//        // ✅ Call API
//        ResponseEntity<String> response =
//                restTemplate.exchange(UNSTRACT_URL, HttpMethod.POST, request, String.class);
//
//        System.out.println("response: " + response);
//        return response.getBody();
//    }
//}
//-------------- controller ------------------
//@GetMapping("/upload-unstract-file-hc")
//    public ResponseEntity<String> uploadFile() {
//        try {
//            // Hardcoded file path
//           // File file = new File("D:/workspace/Unstract_poc/FLHQ Renew_BTMM WO_2019 SOW.pdf");
//
//            // Hardcoded file path with File.separator
//            String filePath = "D:" + File.separator + "workspace" + File.separator + "Unstract_poc"
//                    + File.separator + "FLHQ Renew_BTMM WO_2019 SOW.pdf";
//            File file = new File(filePath);
//            String response = unstractApiService.callUnstractApiHC(file);
//            System.out.println("controller response: " +response);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
//        }
//    }