package com.hycu.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hycu.model.TrackingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 스마트택배(SweetTracker) 배송 추적 API 클라이언트.
 * 외부 HTTP 호출과 JSON 파싱을 캡슐화하고, 실패 시 ok=false 로 안전하게 반환한다.
 * (기존엔 컨트롤러가 HttpURLConnection 으로 직접 호출/파싱하고 예외 처리도 없었음)
 */
@Component
public class SweetTrackerClient {

    private static final Logger log = LoggerFactory.getLogger(SweetTrackerClient.class);

    private static final String API_URL = "https://info.sweettracker.co.kr/api/v1/trackingInfo";
    private static final String API_KEY = "REPLACE_WITH_SWEETTRACKER_API_KEY";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SweetTrackerClient() {
        // 한글 상품명이 깨지지 않도록 UTF-8 String 변환기로 RestTemplate 구성
        this.restTemplate = new RestTemplate(
                Collections.singletonList(new StringHttpMessageConverter(StandardCharsets.UTF_8)));
    }

    public TrackingResponse fetch(String companyCode, String invoice) {
        TrackingResponse result = new TrackingResponse();
        try {
            String url = API_URL + "?t_code=" + companyCode + "&t_invoice=" + invoice + "&t_key=" + API_KEY;
            String body = restTemplate.getForObject(url, String.class);
            JsonNode node = objectMapper.readTree(body);

            JsonNode codeNode = node.get("code");
            result.setCode(codeNode == null ? null : codeNode.asText());

            JsonNode itemNode = node.get("itemName");
            if (itemNode != null) {
                result.setItemName(itemNode.asText());
            }
            JsonNode lastState = node.get("lastStateDetail");
            if (lastState != null && lastState.get("level") != null) {
                result.setLevel(lastState.get("level").asText());
            }
            result.setOk(true);
        } catch (Exception e) {
            log.warn("SweetTracker 조회 실패 (" + companyCode + "/" + invoice + "): " + e.getMessage());
            result.setOk(false);
        }
        return result;
    }
}
