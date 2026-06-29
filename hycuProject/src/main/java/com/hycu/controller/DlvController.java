package com.hycu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hycu.mapper.DlvMapper;
import com.hycu.model.DlvVO;
import com.hycu.service.DlvService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class DlvController {

    private static final Logger log = LoggerFactory.getLogger(DlvController.class);

    private static final String TRACKING_API_URL = "https://info.sweettracker.co.kr/api/v1/trackingInfo";
    private static final String TRACKING_API_KEY = "REPLACE_WITH_SWEETTRACKER_API_KEY";

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletResponse response;

    private final DlvMapper dlvMapper;

    @Autowired
    private DlvService dlvService;

    public DlvController(DlvMapper dlvMapper) {
        this.dlvMapper = dlvMapper;
    }

    @PostMapping("/track")
    public String trackDelivery(@RequestParam("trckNum") String trckNum,
                                @RequestParam("dlvCompanyCode") String dlvCompanyCode,
                                @RequestParam("goodsNm") String goodsNm,
                                @RequestParam("shopCode") String shopCode) throws IOException {

        String urlStr = TRACKING_API_URL + "?t_code=" + dlvCompanyCode + "&t_invoice=" + trckNum + "&t_key=" + TRACKING_API_KEY;
        log.debug("tracking request: {}", urlStr);

        URL url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.setRequestProperty("Content-type", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(sb.toString());
        JsonNode lastStateDetailNode = jsonNode.get("lastStateDetail");
        String code = (jsonNode.get("code") == null) ? "404" : jsonNode.get("code").asText();

        String userId = (String) session.getAttribute("userId");
        DlvVO dlvVO = new DlvVO();

        if (Integer.parseInt(code) == 104) { // 104 = 등록되지 않은 운송장 번호
            dlvVO.setTrckNum(trckNum);
            dlvVO.setDlvStat("00");
            dlvVO.setGoodsNm(goodsNm);
            dlvVO.setShopCode(shopCode);
            dlvVO.setDlvCompanyCode(dlvCompanyCode);
            dlvVO.setUsrId(userId);
            dlvMapper.insertDlvInfo(dlvVO);
            return "redirect:/dlvInfo";
        }

        if (urlConn.getResponseCode() == 200) {
            String itemName = jsonNode.get("itemName").asText();
            String level = lastStateDetailNode.get("level").asText();
            dlvVO.setTrckNum(trckNum);
            dlvVO.setDlvStat("0" + level);
            dlvVO.setGoodsNm(itemName);
            dlvVO.setShopCode(shopCode);
            dlvVO.setDlvCompanyCode(dlvCompanyCode);
            dlvVO.setUsrId(userId);
            dlvMapper.insertDlvInfo(dlvVO);
            br.close();
            urlConn.disconnect();
            return "dlvInfo";
        } else {
            br.close();
            urlConn.disconnect();
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('배송저장에 실패했습니다.');</script>");
            w.flush();
            w.close();
            return "dlvInfo";
        }
    }

    @PostMapping("/trackList")
    public String trackDeliveryList(HttpSession session) throws IOException {

        String usrId = (String) session.getAttribute("userId");
        List<DlvVO> trckNumList = dlvService.getTrckNum(usrId); // 사용자의 추적 대상 운송장

        for (DlvVO dlvVO : trckNumList) {
            String dlvCompanyCode = dlvVO.getDlvCompanyCode();
            String trckNum = dlvVO.getTrckNum();
            String urlStr = TRACKING_API_URL + "?t_code=" + dlvCompanyCode + "&t_invoice=" + trckNum + "&t_key=" + TRACKING_API_KEY;

            URL url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Content-type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(sb.toString());
            JsonNode lastStateDetailNode = jsonNode.get("lastStateDetail");
            // 성공 응답에는 code 필드가 없음 → 0(에러 아님)으로 처리 (이전엔 "success" → parseInt 예외)
            String code = (jsonNode.get("code") == null) ? "0" : jsonNode.get("code").asText();

            if (Integer.parseInt(code) != 104 && urlConn.getResponseCode() == 200) {
                String itemName = jsonNode.get("itemName").asText();
                String level = lastStateDetailNode.get("level").asText();
                dlvVO.setDlvStat("0" + level);
                dlvVO.setGoodsNm(itemName);
                dlvVO.setUsrId(usrId);
                dlvMapper.insertDlvInfo(dlvVO);
            }
            br.close();
            urlConn.disconnect();
        }
        return "dlvInfo";
    }

    @GetMapping("/trackList")
    public String trackList() throws IOException {
        trackDeliveryList(session);
        return "/main";
    }
}
