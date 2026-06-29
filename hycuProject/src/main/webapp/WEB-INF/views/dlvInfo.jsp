<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>배송 조회 · 배송조회</title>
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
      <a href="/dlvAdd">운송장 등록</a>
      <a href="/dlvInfo" class="active">배송 조회</a>
      <a href="#">문의</a>
    </nav>
    <div class="user">
      <span class="uname">${sessionScope.userId}</span>
      <span class="avatar">${fn:toUpperCase(fn:substring(sessionScope.userId, 0, 1))}</span>
    </div>
  </div>

  <div class="wrap">
    <div class="page-head">
      <h1>배송 조회</h1>
      <p>등록한 운송장의 최신 배송 상태입니다.</p>
    </div>

    <div class="card">
      <div class="table-head">
        <h2>전체 배송</h2>
        <span class="sub">최근 등록순</span>
      </div>
      <table>
        <thead>
          <tr><th>운송장 번호</th><th>택배사</th><th>상품명</th><th>배송 상태</th><th>등록일시</th></tr>
        </thead>
        <tbody>
          <c:forEach items="${dlvList}" var="dlv">
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
              <td class="goods">${dlv.sysRegDtime}</td>
            </tr>
          </c:forEach>
          <c:if test="${empty dlvList}">
            <tr><td colspan="5" style="text-align:center;color:var(--faint);padding:40px;">등록된 배송이 없습니다. <a href="/dlvAdd" style="color:var(--primary-d);font-weight:700;">운송장 등록하기 →</a></td></tr>
          </c:if>
        </tbody>
      </table>

      <c:if test="${totalPages > 1}">
        <div class="pager">
          <c:if test="${currentPage > 1}"><a href="?page=${currentPage - 1}">‹</a></c:if>
          <c:forEach begin="1" end="${totalPages}" var="page">
            <c:choose>
              <c:when test="${page == currentPage}"><span class="active">${page}</span></c:when>
              <c:otherwise><a href="?page=${page}">${page}</a></c:otherwise>
            </c:choose>
          </c:forEach>
          <c:if test="${currentPage < totalPages}"><a href="?page=${currentPage + 1}">›</a></c:if>
        </div>
      </c:if>
    </div>

    <div class="foot">© 2023 H201803006 권진우 · 졸업과제 · UI 고도화 2026</div>
  </div>
</body>
</html>
