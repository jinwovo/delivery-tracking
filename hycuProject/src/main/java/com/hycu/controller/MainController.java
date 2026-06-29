package com.hycu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hycu.model.DlvVO;
import com.hycu.service.DlvService;

@Controller
public class MainController {
	
	@Autowired
	private HttpSession session;
	
    @Autowired
    private DlvService dlvService;
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	//메인 페이지 이동
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainPageGET(Model model) {
		
		String userId = (String) session.getAttribute("userId");
		DlvVO dlvCount = dlvService.getDlvMainCount(userId); // 메인화면 배송건수 표시

		model.addAttribute("dlvCount", dlvCount);
		model.addAttribute("recentList", dlvService.getDlvInfo(1, 5, userId)); // 대시보드 최근 배송 5건

		return "main";
		
	}
	
	@RequestMapping(value = "/dlvAdd", method = RequestMethod.GET)
	public String dlvAdd(Model model) {
		List<DlvVO> dlvCorp = dlvService.getDlvCorp(); // 택배사 정보 가져오기
		
		List<DlvVO> mallCorp = dlvService.getMallCorp(); // 택배사 정보 가져오기

		model.addAttribute("dlvCode", dlvCorp);
		model.addAttribute("shopCode", mallCorp);
        return "dlvAdd";
	}
	
    @RequestMapping(value = "/dlvInfo", method = RequestMethod.GET)
    public String getDlvInfo(@RequestParam(name = "page", defaultValue = "1") int currentPage, Model model) {
		String usrId = (String) session.getAttribute("userId");

        int recordsPerPage = 10;
        int totalRecords = dlvService.getTotalRecords(usrId); // 로그인 사용자 기준 전체 건수 (이전엔 전체 테이블을 세어 페이지 수가 틀렸음)
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        List<DlvVO> dlvList = dlvService.getDlvInfo(currentPage, recordsPerPage, usrId); // 페이지별 목록

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("dlvList", dlvList);

        return "dlvInfo";
    }
}
