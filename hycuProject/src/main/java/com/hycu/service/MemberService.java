package com.hycu.service;

import com.hycu.model.MemberVO;

public interface MemberService {
    MemberVO getMemberById(String userId);

    /** 회원 가입. 아이디가 이미 있으면 false. */
    boolean register(MemberVO member);

}