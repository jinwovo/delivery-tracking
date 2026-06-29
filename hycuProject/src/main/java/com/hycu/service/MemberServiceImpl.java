package com.hycu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hycu.mapper.MemberMapper;
import com.hycu.model.MemberVO;


@Service
public class MemberServiceImpl implements MemberService {
	
    @Autowired
    private MemberMapper memberMapper;
    

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberVO getMemberById(String userId) {
        return memberMapper.getMemberById(userId);
    }

    @Override
    public boolean register(MemberVO member) {
        if (memberMapper.getMemberById(member.getUsrId()) != null) {
            return false;
        }
        memberMapper.insertUsrBase(member);
        return true;
    }

}