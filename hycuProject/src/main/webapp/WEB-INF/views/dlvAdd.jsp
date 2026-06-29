<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>운송장 등록 · 배송조회</title>
  <link rel="stylesheet" href="resources/css/app.css">
</head>
<body>
  <div class="topbar">
    <div class="brand">
      <span class="mark"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 3h15v13H1z"/><path d="M16 8h4l3 3v5h-7V8z"/><circle cx="5.5" cy="18.5" r="2.5"/><circle cx="18.5" cy="18.5" r="2.5"/></svg></span>
      배송조회
    </div>
    <nav class="nav">
      <a href="/main">홈</a>
      <a href="/dlvAdd" class="active">운송장 등록</a>
      <a href="/dlvInfo">배송 조회</a>
      <a href="#">문의</a>
    </nav>
    <div class="user">
      <span class="uname">${sessionScope.userId}</span>
      <span class="avatar">${fn:toUpperCase(fn:substring(sessionScope.userId, 0, 1))}</span>
    </div>
  </div>

  <div class="wrap">
    <div class="page-head">
      <h1>운송장 등록</h1>
      <p>운송장 번호와 택배사를 입력하면 배송 상태를 자동으로 추적합니다.</p>
    </div>

    <div class="card card-pad form-card">
      <form action="/track" method="post">
        <div class="field">
          <label for="trckNum">운송장 번호 <span class="req">*</span></label>
          <input type="text" id="trckNum" name="trckNum" placeholder="숫자만 입력" required>
        </div>
        <div class="field">
          <label for="dlvCompanyCode">택배사 <span class="req">*</span></label>
          <select id="dlvCompanyCode" name="dlvCompanyCode" required>
            <option value="">선택해주세요</option>
            <c:forEach var="list" items="${dlvCode}">
              <option value="${list.dlvCompanyCode}">${list.dlvCompanyName}</option>
            </c:forEach>
          </select>
        </div>
        <div class="field">
          <label for="shopCode">쇼핑몰 <span class="req">*</span></label>
          <select id="shopCode" name="shopCode" required>
            <option value="">선택해주세요</option>
            <c:forEach var="list" items="${shopCode}">
              <option value="${list.shopCode}">${list.shopName}</option>
            </c:forEach>
          </select>
        </div>
        <div class="field">
          <label for="goodsNm">상품명</label>
          <input type="text" id="goodsNm" name="goodsNm" placeholder="예) 기계식 키보드">
        </div>
        <div class="btn-row">
          <button type="submit" class="btn btn-primary">운송장 등록</button>
          <button type="button" class="btn btn-ghost">엑셀 업로드 <span style="font-size:11px;opacity:.7">(예정)</span></button>
        </div>
      </form>
    </div>

    <div class="foot">© 2023 H201803006 권진우 · 졸업과제 · UI 고도화 2026</div>
  </div>
</body>
</html>
