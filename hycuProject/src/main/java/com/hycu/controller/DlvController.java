package com.hycu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hycu.service.TrackingService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class DlvController {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private TrackingService trackingService;

    @PostMapping("/track")
    public String trackDelivery(@RequestParam("trckNum") String trckNum,
                                @RequestParam("dlvCompanyCode") String dlvCompanyCode,
                                @RequestParam("goodsNm") String goodsNm,
                                @RequestParam("shopCode") String shopCode) throws IOException {
        String userId = (String) session.getAttribute("userId");
        boolean saved = trackingService.register(trckNum, dlvCompanyCode, goodsNm, shopCode, userId);

        if (saved) {
            return "redirect:/dlvInfo";
        }
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("<script>alert('배송저장에 실패했습니다.');</script>");
        writer.flush();
        writer.close();
        return "dlvInfo";
    }

    @PostMapping("/trackList")
    public String trackDeliveryList(HttpSession session) {
        trackingService.refreshUserDeliveries((String) session.getAttribute("userId"));
        return "redirect:/dlvInfo";
    }

    @GetMapping("/trackList")
    public String trackList() {
        trackingService.refreshUserDeliveries((String) session.getAttribute("userId"));
        return "redirect:/main";
    }
}
