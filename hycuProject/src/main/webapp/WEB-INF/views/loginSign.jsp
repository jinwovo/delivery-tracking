<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>회원가입 · 배송조회</title>
  <link rel="stylesheet" href="resources/css/app.css">
</head>
<body>
  <div class="auth-bg">
    <div class="auth-card">
      <div class="auth-logo">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 3h15v13H1z"/><path d="M16 8h4l3 3v5h-7V8z"/><circle cx="5.5" cy="18.5" r="2.5"/><circle cx="18.5" cy="18.5" r="2.5"/></svg>
      </div>
      <h1>회원가입</h1>
      <p class="tag">30초면 시작할 수 있어요</p>
      <form action="/sign" method="post">
        <div class="field">
          <label for="usrId">아이디</label>
          <input type="text" id="usrId" name="usrId" placeholder="사용할 아이디">
        </div>
        <div class="field">
          <label for="passWd">비밀번호</label>
          <input type="password" id="passWd" name="passWd" placeholder="비밀번호">
        </div>
        <div class="field">
          <label for="usrEmail">이메일</label>
          <input type="text" id="usrEmail" name="usrEmail" placeholder="you@example.com">
        </div>
        <div class="field">
          <label for="usrPhone">휴대폰</label>
          <input type="text" id="usrPhone" name="usrPhone" placeholder="010-0000-0000">
        </div>
        <div class="field">
          <label for="usrBirth">생년월일</label>
          <input type="text" id="usrBirth" name="usrBirth" placeholder="1998-03-06">
        </div>
        <button type="submit" class="btn btn-primary btn-block" style="margin-top:8px;">가입하기</button>
      </form>
      <p class="auth-foot">이미 계정이 있으신가요? <a href="/login">로그인</a></p>
    </div>
  </div>
</body>
</html>
