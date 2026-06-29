<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>홈 · 배송조회</title>
  <link rel="stylesheet" href="resources/css/app.css">
</head>
<body>
  <div class="topbar">
    <div class="brand">
      <span class="mark"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 3h15v13H1z"/><path d="M16 8h4l3 3v5h-7V8z"/><circle cx="5.5" cy="18.5" r="2.5"/><circle cx="18.5" cy="18.5" r="2.5"/></svg></span>
      배송조회
    </div>
    <nav class="nav">
      <a href="/main" class="active">홈</a>
      <a href="/dlvAdd">운송장 등록</a>
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
      <h1>안녕하세요, ${sessionScope.userId}님 👋</h1>
      <p>현재 배송 현황을 한눈에 확인하세요.</p>
    </div>

    <div class="stats">
      <div class="stat wait">
        <div class="ic"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg></div>
        <div class="num">${empty dlvCount.dlvNotFin ? 0 : dlvCount.dlvNotFin}</div><div class="lbl">배송대기중</div>
      </div>
      <div class="stat info">
        <div class="ic"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 3h15v13H1z"/><path d="M16 8h4l3 3v5h-7V8z"/><circle cx="5.5" cy="18.5" r="2.5"/><circle cx="18.5" cy="18.5" r="2.5"/></svg></div>
        <div class="num">${empty dlvCount.dlvNow ? 0 : dlvCount.dlvNow}</div><div class="lbl">배송중</div>
      </div>
      <div class="stat ok">
        <div class="ic"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg></div>
        <div class="num">${empty dlvCount.dlvFin ? 0 : dlvCount.dlvFin}</div><div class="lbl">배송완료</div>
      </div>
      <div class="stat bad">
        <div class="ic"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/><line x1="12" y1="9" x2="12" y2="13"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg></div>
        <div class="num">${empty dlvCount.dlvStrange ? 0 : dlvCount.dlvStrange}</div><div class="lbl">이상 배송</div>
      </div>
    </div>

    <div class="card">
      <div class="table-head">
        <h2>최근 배송</h2>
        <a class="sub" href="/dlvInfo">전체 보기 →</a>
      </div>
      <table>
        <thead><tr><th>운송장 번호</th><th>택배사</th><th>상품명</th><th>상태</th></tr></thead>
        <tbody>
          <c:forEach items="${recentList}" var="dlv">
            <tr>
              <td class="mono">${dlv.trckNum}</td>
              <td><div class="courier"><span class="dot"></span>
                <c:choose>
                  <c:when test="${dlv.dlvCompanyCode == '04'}">CJ대한통운</c:when>
                  <c:when test="${dlv.dlvCompanyCode == '05'}">한진택배</c:when>
                  <c:when test="${dlv.dlvCompanyCode == '06'}">로젠택배</c:when>
                  <c:when test="${dlv.dlvCompanyCode == '08'}">롯데택배</c:when>
                  <c:when test="${dlv.dlvCompanyCode == '01'}">우체국택배</c:when>
                  <c:otherwise>${dlv.dlvCompanyCode}</c:otherwise>
                </c:choose>
              </div></td>
              <td class="goods">${dlv.goodsNm}</td>
              <td>
                <c:choose>
                  <c:when test="${dlv.dlvStat == '06'}"><span class="badge ok">배송완료</span></c:when>
                  <c:when test="${dlv.dlvStat == '05'}"><span class="badge info">배송중</span></c:when>
                  <c:otherwise><span class="badge wait">배송준비</span></c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty recentList}">
            <tr><td colspan="4" style="text-align:center;color:var(--faint);padding:36px;">등록된 배송이 없습니다. <a href="/dlvAdd" style="color:var(--primary-d);font-weight:700;">운송장 등록하기 →</a></td></tr>
          </c:if>
        </tbody>
      </table>
    </div>

    <div class="foot">© 2023 H201803006 권진우 · 졸업과제 · UI 고도화 2026</div>
  </div>
</body>
</html>
