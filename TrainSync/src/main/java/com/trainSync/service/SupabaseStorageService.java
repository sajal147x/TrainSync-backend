


package com.trainSync.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * Author: Sajal Gupta
 * Date: Nov 8, 2025
 */
@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket.name}")
    private String bucketName;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Uploads a Base64 image to Supabase storage.
     *
     * @param base64Data Base64-encoded image string
     * @param fileName   Name of the file to store
     * @return public URL of the uploaded file
     */
    public String uploadBase64Image(String base64Data, String fileName) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

            String url = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set("apikey", supabaseKey);
            headers.set("Authorization", "Bearer " + supabaseKey);

            HttpEntity<byte[]> entity = new HttpEntity<>(decodedBytes, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Construct public URL
                return supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + fileName;
            } else {
                throw new RuntimeException("Failed to upload image: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error uploading image to Supabase", e);
        }
    }
}

