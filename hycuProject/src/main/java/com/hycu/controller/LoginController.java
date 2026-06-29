package com.hycu.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hycu.mapper.MemberMapper;
import com.hycu.model.DlvVO;
import com.hycu.model.MemberVO;
import com.hycu.service.AuthenticationService;
import com.hycu.service.DlvService;
import com.hycu.service.MemberService;

@Controller
public class LoginController {
    @Autowired
    private HttpSession session;
    @Autowired
    private DlvService dlvService;
    @Autowired
    private DlvController dlvController; // DlvController 주입
    @Autowired
    private MemberMapper membermapper;

    private final MemberService memberService;
    private AuthenticationService authenticationService;

    @Autowired
    public LoginController(MemberService memberService, AuthenticationService authenticationService) {
        this.memberService = memberService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "loginForm";
    }	
    
    @GetMapping("/loginSign")
    public String showLoginSignForm() {
        return "loginSign";
    }	
    
    @PostMapping("/sign")
    public String processSign(@RequestParam("usrId") String usrId,
                               @RequestParam("passWd") String passWd,
                               @RequestParam("usrEmail") String usrEmail,
                               @RequestParam("usrPhone") String usrPhone,
                               @RequestParam("usrBirth") String usrBirth,
                               Model model ,HttpServletResponse response) throws IOException {
		
    	
    	
    	MemberVO memberVo = new MemberVO();
    	
        MemberVO member = memberService.getMemberById(usrId);

        if (member != null) {

    		response.setContentType("text/html; charset=utf-8");
    		PrintWriter w = response.getWriter();
    		w.write("<script>alert('"+"이미존재하는 id 입니다."+"');</script>");
    		w.flush();
        	return "loginSign";

        } else {
        }    	
		memberVo.setUsrBirth(usrBirth);
		memberVo.setUsrId(usrId);
		memberVo.setPassWd(passWd);
		memberVo.setUsrEmail(usrEmail);
		memberVo.setUsrPhone(usrPhone);
		membermapper.insertUsrBase(memberVo);
    	
    	return "/loginForm";
    	
    }	

    @PostMapping("/login")
    public String processLogin(@RequestParam("usrId") String userId,
                               @RequestParam("passWd") String password,
                               Model model) throws IOException {
        boolean isValidUser = authenticationService.authenticate(userId, password);

        if (isValidUser) {
            session.setAttribute("userId", userId); // 세션에 아이디 저장
            model.addAttribute("userId", userId);
            DlvVO dlvCount = dlvService.getDlvMainCount(userId); // 메인화면 배송건수 표시
            model.addAttribute("dlvCount", dlvCount);

            dlvController.trackDeliveryList(session);

            return "/main";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "/loginForm";
        }
    }
}
