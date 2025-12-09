package com.example;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CurlRunner {
    public static void main(String[] args) {
        try {
            String[] command = {
                    "curl", "--location",
                    "https://us-central.unstract.com/deployment/api/org_kSuAb3rXXvOxXFMJ/poc_2_wo_1759300408741/",
                    "--header", "Authorization: Bearer 1a283057-9915-47a2-bbb0-a895a8e104f9",
                    "--form", "files=@C:/Users/Lenovo/Downloads/FLHQ Renew_BTMM WO_2019 SOW.pdf",
                   // "--form", "timeout=",
                    "--form", "include_metadata=false",
                    "--form", "include_metrics=false"
            };

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }


            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        String url = "https://us-central.unstract.com/deployment/api/org_kSuAb3rXXvOxXFMJ/poc_2_wo_1759300408741/";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setBearerAuth("1a283057-9915-47a2-bbb0-a895a8e104f9");
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("files", new FileSystemResource("C:/Users/Lenovo/Downloads/FLHQ Renew_BTMM WO_2019 SOW.pdf"));
//        body.add("timeout", "300");
//        body.add("include_metadata", "False");
//        body.add("include_metrics", "False");
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//
//        System.out.println("Response: " + response.getBody());
    }
}
