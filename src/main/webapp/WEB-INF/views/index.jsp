<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <title>로그인</title>
</head>
<body>
<header>
</header>
<section class="login-section">
    <form method="post" action="/user/auth">
        <div class="login-banner">
            <img src="/images/main/mainlabel.png">
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" style="width: 5.5em">아이디</span>
            <input type="text" class="form-control" name="id" aria-label="Sizing example input"
                   aria-describedby="inputGroup-sizing-default">
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" style="width: 5.5em">비밀번호</span>
            <input type="password" class="form-control" name="password" aria-label="Recipient's username"
                   aria-describedby="button-addon2">
            <button class="btn btn-outline-secondary" type="submit" id="button-addon2">로그인</button>
        </div>
        <div class="login-section-footer">
            <a href="/user/register">회원가입</a>
        </div>
    </form>
</section>
</body>
</html>