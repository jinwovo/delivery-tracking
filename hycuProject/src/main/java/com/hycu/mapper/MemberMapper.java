package com.hycu.mapper;

import com.hycu.model.MemberVO;

public interface MemberMapper {
    MemberVO getMemberById(String userId);

	void getMemberById(MemberVO memberVo);

	void insertUsrBase(MemberVO memberVo);
}