<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script type="text/javascript" src="/js/admin/user_management.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>회원관리(관리자)</title>
</head>
<header>
    <jsp:include page="../../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">회원 관리</h2>
        </header>
        <section class="user-management-content-section">
            <table class="user-table table table-striped-columns">
                <tr>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>전화번호</th>
                    <th>역할</th>
                    <th>등급</th>
                    <th>가입일</th>
                    <th>저장</th>
                    <th>비밀번호</th>
                    <th>삭제</th>
                </tr>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td class="user-id">${user.id}</td>
                        <td class="user-name">${user.name}</td>
                        <td class="user-tel">${user.tel}</td>
                        <td class="user-role" style="color: midnightblue">
                            <c:choose>
                                <c:when test="${user.role == 1}">사용자</c:when>
                                <c:when test="${user.role == 2}">판매자</c:when>
                                <c:when test="${user.role == 3}">관리자</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <select class="user-grade form-select">
                                <option value="1" ${user.grade == 1 ? 'selected' : ''}>BRONZE</option>
                                <option value="2" ${user.grade == 2 ? 'selected' : ''}>SILVER</option>
                                <option value="3" ${user.grade == 3 ? 'selected' : ''}>GOLD</option>
                                <option value="4" ${user.grade == 4 ? 'selected' : ''}>VIP</option>
                            </select>
                        </td>
                        <td>${user.signupDate}</td>
                        <td>
                            <button class="btn btn-outline-dark save-btn">저장</button>
                        </td>
                        <td>
                            <button class="btn btn-outline-info reset-btn">초기화</button>
                        </td>
                        <td>
                            <button class="btn btn-outline-danger delete-btn">삭제</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="/admin/user/management?pageNumber=${startPage - 1}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="${startPage}" end="${endPage}" step="1" var="page">
                    <li class="page-item"><a class="page-link ${page == currentPage ? 'active' : ''}"
                                             href="/admin/user/management?pageNumber=${page}">${page}</a></li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href="/admin/user/management?pageNumber=${endPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </section>
</section>
</body>
</html>