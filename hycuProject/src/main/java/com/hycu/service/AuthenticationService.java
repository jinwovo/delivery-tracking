package com.hycu.service;

import org.springframework.stereotype.Service;

import com.hycu.model.MemberVO;

@Service
public class AuthenticationService {

    private final MemberService memberService;

    public AuthenticationService(MemberService memberService) {
        this.memberService = memberService;
    }

    public boolean authenticate(String userId, String password) {
        MemberVO member = memberService.getMemberById(userId);

        if (member != null && member.getPassWd().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}