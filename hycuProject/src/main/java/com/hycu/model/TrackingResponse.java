package com.hycu.model;

/**
 * 스마트택배(SweetTracker) 조회 결과를 담는 도메인 DTO.
 * 외부 API의 JSON 구조를 컨트롤러/서비스에서 직접 다루지 않도록 캡슐화한다.
 */
public class TrackingResponse {

    private boolean ok;        // 외부 호출 + 파싱 성공 여부
    private String code;       // SweetTracker 응답 코드 (정상 응답엔 없음)
    private String itemName;   // 상품명
    private String level;      // 배송 단계 (1~6)

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /** code 104 = 등록되지 않은 운송장 번호 (조회 결과 없음). */
    public boolean isNotFound() {
        return "104".equals(code);
    }
}
