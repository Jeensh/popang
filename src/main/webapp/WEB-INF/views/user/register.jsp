<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <title>회원가입</title>
</head>
<body>
<header>
</header>
<section class="register-section">
    <form method="post" class="register-form" action="/user/register">
        <div class="register-banner">
            <img src="/images/main/mainlabel.png" class="register-banner-image">
        </div>
        <section class="register-form-content">
            <h6>회원정보를 입력하세요.</h6>
            <div class="input-group mb-3">
                <span class="input-group-text"><img class="register-icon" src="/images/icon/id.png"></span>
                <input type="text" class="form-control" name="id" aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-default" placeholder="아이디">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text"><img class="register-icon" src="/images/icon/pw.png"></span>
                <input type="password" class="form-control" name="password" aria-label="Recipient's username"
                       aria-describedby="button-addon2" placeholder="비밀번호">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text"><img class="register-icon" src="/images/icon/pw.png"></span>
                <input type="password" class="form-control" name="passwordCheck" aria-label="Recipient's username"
                       aria-describedby="button-addon2" placeholder="비밀번호 확인">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text"><img class="register-icon" src="/images/icon/tel.png"></span>
                <input type="text" class="form-control" name="tel" aria-label="Recipient's username"
                       aria-describedby="button-addon2" placeholder="전화번호">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text"><img class="register-icon" src="/images/icon/name.png"></span>
                <input type="text" class="form-control" name="name" aria-label="Recipient's username"
                       aria-describedby="button-addon2" placeholder="이름">
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="role" value="2" id="role-checkbox">
                <label class="form-check-label" for="role-checkbox" style="font-weight: bold">
                    판매자
                </label>
            </div>
            <span style="color: red; font-weight: bold">
                <c:if test="${messages != null}">
                    <c:forEach items="${messages}" var="message">
                        <p>${message}</p>
                    </c:forEach>
                </c:if>
            </span>
            <div class="register-section-footer">
                <a href="/" style="margin-right: auto; margin-top: 1em">뒤로가기</a>
                <button type="submit" class="btn btn-outline-info" style="margin-left: auto">회원가입</button>
            </div>
        </section>
    </form>
</section>
</body>
</html>