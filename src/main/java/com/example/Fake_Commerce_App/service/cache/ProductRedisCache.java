package com.example.Fake_Commerce_App.service.cache;

import com.example.Fake_Commerce_App.dtos.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRedisCache {
    private static final String KEY_SUMMARY = "product:summary:";
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public Optional<ProductResponseDto> getSummary(Long id) {
        try {
            String responseJson = redisTemplate.opsForValue().get(KEY_SUMMARY + id);

            if (responseJson == null) {
                return Optional.empty();
            }

            ProductResponseDto dto = objectMapper.readValue(responseJson, ProductResponseDto.class);
            return Optional.ofNullable(dto);

        } catch (Exception e) {

            log.error("Error in parsing the string to json for product id {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public void putProductSummary(Long id, ProductResponseDto response) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(response);
            redisTemplate.opsForValue().set(KEY_SUMMARY + id, jsonResponse, Duration.ofMinutes(1));

        } catch (Exception e) {
            log.error("Error while setting the product summary to redis cache for id {}: {}", id, e.getMessage(), e);
        }
    }
}