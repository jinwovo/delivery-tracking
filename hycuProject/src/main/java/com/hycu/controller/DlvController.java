package com.hycu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hycu.mapper.DlvMapper;
import com.hycu.model.DlvVO;
import com.hycu.service.DlvService;
import com.hycu.service.SweetTrackerResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class DlvController {
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
    	
		BufferedReader br = null;

        String apiUrl = "https://info.sweettracker.co.kr/api/v1/trackingInfo";
        String apiKey = "REPLACE_WITH_SWEETTRACKER_API_KEY";

        String urlStr = apiUrl + "?t_code=" + dlvCompanyCode + "&t_invoice=" + trckNum + "&t_key=" + apiKey;
        
        System.out.println("urlStr : " + urlStr );
        
		URL url=new URL(urlStr);
		
		HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();
		
		urlConn.setRequestMethod("GET"); // 대문자로
		
		urlConn.setRequestProperty("Content-type", "application/json");

		System.out.println("Response code: "+urlConn.getResponseCode()); //200
		
		br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line=br.readLine())!=null) {
			sb.append(line);
		}
		String jsonString = sb.toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(jsonString);
		JsonNode lastStateDetailNode = jsonNode.get("lastStateDetail");
		String code = "";
		if(jsonNode.get("code")== null) {
			 code = "404";
		}else {
			 code = jsonNode.get("code").asText();
		}
		String userId = (String) session.getAttribute("userId");
        DlvVO dlvVO = new DlvVO();
        dlvVO.setTrckNum(trckNum);
        dlvVO.setDlvCompanyCode(dlvCompanyCode);
        int existingInfoCount = dlvMapper.selectDlvInfo(dlvVO);

		if(existingInfoCount != 0) {
			System.out.println("이미 똑같은 배송상태가 등록되어있습니다.");
		}
		if(Integer.parseInt(code)== 104) {
            dlvVO.setTrckNum(trckNum);
            dlvVO.setDlvStat("00");
            dlvVO.setGoodsNm(goodsNm);
            dlvVO.setShopCode(shopCode);
            dlvVO.setDlvCompanyCode(dlvCompanyCode);
            dlvVO.setUsrId(userId);
            dlvMapper.insertDlvInfo(dlvVO);
            
            return "redirect:/dlvInfo";  
		}
		
        if (Integer.parseInt(code)!= 104&& urlConn.getResponseCode() == 200) { //104가 등록되지 않는 운송장번호
        	
    		String itemName = jsonNode.get("itemName").asText();
    		String level = lastStateDetailNode.get("level").asText();
    		
            dlvVO.setTrckNum(trckNum);
            dlvVO.setDlvStat("0"+level);
            dlvVO.setGoodsNm(itemName);
            dlvVO.setShopCode(shopCode);
            dlvVO.setDlvCompanyCode(dlvCompanyCode);
            dlvVO.setUsrId(userId);
            dlvMapper.insertDlvInfo(dlvVO);
    		// 연결 해제
    		br.close();
    		urlConn.disconnect();

            return "dlvInfo"; 
        } else {
    		// 연결 해제
    		br.close();
    		urlConn.disconnect();

    		response.setContentType("text/html; charset=utf-8");
    		PrintWriter w = response.getWriter();
    		w.write("<script>alert('"+"배송저장에 실패했습니다."+"');</script>");
    		w.flush();
    		w.close();
            return "dlvInfo"; 
        }
   
    }
    
    @PostMapping("/trackList")
    public String trackDeliveryList(HttpSession session) throws IOException {
    	
		BufferedReader br = null;

        String apiUrl = "https://info.sweettracker.co.kr/api/v1/trackingInfo";
        String apiKey = "REPLACE_WITH_SWEETTRACKER_API_KEY";
		String usrId = (String) session.getAttribute("userId");

		List<DlvVO> trckNumList = dlvService.getTrckNum(usrId); // 택배사 정보 가져오기

		for (DlvVO dlvVO : trckNumList) {
		    String dlvCompanyCode = dlvVO.getDlvCompanyCode();
		    String trckNum = dlvVO.getTrckNum();
       	   	 String urlStr = apiUrl + "?t_code=" + dlvCompanyCode + "&t_invoice=" + trckNum + "&t_key=" + apiKey;
       	   	 URL url=new URL(urlStr);
		
		HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();
		
		urlConn.setRequestMethod("GET"); // 대문자로
		
		urlConn.setRequestProperty("Content-type", "application/json");

		System.out.println("Response code: "+urlConn.getResponseCode()); //200
		
		br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line=br.readLine())!=null) {
			sb.append(line);
		}
		String jsonString = sb.toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(jsonString);
		JsonNode lastStateDetailNode = jsonNode.get("lastStateDetail");
		String code = "";
		if(jsonNode.get("code")== null) {
			 code = "success";
		}else {
			 code = jsonNode.get("code").asText();
		}
		String userId = (String) session.getAttribute("userId");
		System.out.println(code);
		
		
        if (Integer.parseInt(code)!= 104 && urlConn.getResponseCode() == 200) { //104가 등록되지 않는 운송장번호
        	
    		String itemName = jsonNode.get("itemName").asText();
    		String level = lastStateDetailNode.get("level").asText();

            // DlvVO 생성 및 저장
            dlvVO.setTrckNum(trckNum);
            dlvVO.setDlvStat("0"+level);
            dlvVO.setGoodsNm(itemName);
            dlvVO.setDlvCompanyCode(dlvCompanyCode);
            dlvVO.setUsrId(userId);
            dlvMapper.insertDlvInfo(dlvVO);
    		// 연결 해제
    		br.close();
    		urlConn.disconnect();
        } else {
    		// 연결 해제
    		br.close();
    		urlConn.disconnect();
        }
   
		}
		
        return "dlvInfo"; // 배송 정보 저장 성공 시 success 페이지로 이동

    }
    
    
    @PostMapping("/Navertrack")
    public String NaverTrack(HttpSession session) throws IOException {
    	
		BufferedReader br = null;
		
		String apiUrl ="https://m.search.naver.com/p/csearch/ocontent/util/headerjson.naver?callapi=parceltracking";
		String apiKey ="REPLACE_WITH_NAVER_API_KEY";
		String userId = (String) session.getAttribute("userId");
		List<DlvVO> trckNumList = dlvService.getTrckNum(userId); // 택배사 정보 가져오기
		for (DlvVO dlvVO : trckNumList) {
		    String dlvCompanyCode = dlvVO.getDlvCompanyCode();
		    String trckNum = dlvVO.getTrckNum();
			String urlStr = apiUrl + "&t_code=" +dlvCompanyCode+ "&t_invoice="+trckNum +"&passportKey=" + apiKey;

	        System.out.println("urlStr : " + urlStr );
	        
			URL url=new URL(urlStr);
			
			HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();
			
			urlConn.setRequestMethod("GET"); // 대문자로
			
			urlConn.setRequestProperty("Content-type", "application/json");

			System.out.println("Response code: "+urlConn.getResponseCode()); //200
		}


		return "/main";
    	
    }
    
    @GetMapping("/trackList")
    public String trackList() throws IOException {
    	
    	trackDeliveryList(session);
        return "/main";
    }	
    
}
