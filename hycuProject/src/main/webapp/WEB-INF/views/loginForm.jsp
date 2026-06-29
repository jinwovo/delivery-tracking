<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>로그인 · 배송조회</title>
  <link rel="stylesheet" href="resources/css/app.css">
</head>
<body>
  <div class="auth-bg">
    <div class="auth-card">
      <div class="auth-logo">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 3h15v13H1z"/><path d="M16 8h4l3 3v5h-7V8z"/><circle cx="5.5" cy="18.5" r="2.5"/><circle cx="18.5" cy="18.5" r="2.5"/></svg>
      </div>
      <h1>배송조회</h1>
      <p class="tag">흩어진 택배를 한 곳에서 추적하세요</p>
      <c:if test="${not empty error}">
        <div style="background:var(--bad-bg);color:var(--bad);padding:11px 14px;border-radius:10px;font-size:13.5px;font-weight:600;margin-bottom:16px;text-align:center;">${error}</div>
      </c:if>
      <form action="/login" method="post">
        <div class="field">
          <label for="usrId">아이디</label>
          <input type="text" id="usrId" name="usrId" placeholder="아이디를 입력하세요" autofocus>
        </div>
        <div class="field">
          <label for="passWd">비밀번호</label>
          <input type="password" id="passWd" name="passWd" placeholder="비밀번호를 입력하세요">
        </div>
        <button type="submit" class="btn btn-primary btn-block" style="margin-top:8px;">로그인</button>
      </form>
      <p class="auth-foot">계정이 없으신가요? <a href="/loginSign">회원가입</a></p>
    </div>
  </div>
</body>
</html>
