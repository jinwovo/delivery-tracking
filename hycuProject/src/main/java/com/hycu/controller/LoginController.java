package com.hycu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hycu.model.MemberVO;
import com.hycu.service.AuthenticationService;
import com.hycu.service.MemberService;
import com.hycu.service.TrackingService;

@Controller
public class LoginController {

    @Autowired
    private HttpSession session;

    private final MemberService memberService;
    private final AuthenticationService authenticationService;
    private final TrackingService trackingService;

    @Autowired
    public LoginController(MemberService memberService,
                          AuthenticationService authenticationService,
                          TrackingService trackingService) {
        this.memberService = memberService;
        this.authenticationService = authenticationService;
        this.trackingService = trackingService;
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
                              Model model) {
        MemberVO member = new MemberVO();
        member.setUsrId(usrId);
        member.setPassWd(passWd);
        member.setUsrEmail(usrEmail);
        member.setUsrPhone(usrPhone);
        member.setUsrBirth(usrBirth);

        if (!memberService.register(member)) {
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
            return "loginSign";
        }
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("usrId") String userId,
                               @RequestParam("passWd") String password,
                               Model model) {
        if (authenticationService.authenticate(userId, password)) {
            session.setAttribute("userId", userId);
            trackingService.refreshUserDeliveries(userId); // 로그인 시 최신 상태 갱신
            return "redirect:/main";
        }
        model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "loginForm";
    }
}
