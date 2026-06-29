package com.hycu.service;

/**
 * 배송 추적 오케스트레이션 — 외부 API 조회와 영속을 조율한다.
 * (기존엔 이 로직이 전부 DlvController 안에 있었음)
 */
public interface TrackingService {

    /** 운송장을 조회해 등록한다. 저장에 성공하면 true. */
    boolean register(String trckNum, String companyCode, String goodsNm, String shopCode, String userId);

    /** 사용자의 모든 운송장 상태를 외부 API로 다시 조회해 최신화한다. */
    void refreshUserDeliveries(String userId);
}
