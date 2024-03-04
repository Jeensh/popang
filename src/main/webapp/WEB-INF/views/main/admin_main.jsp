<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <title>메인(관리자)</title>

</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">관리자 애플리케이션</h2>
        </header>
        <section class="main-content-section">
            <section class="main-link-list row">
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="/admin/user/management">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/user_management.png">
                        </div>
                        <div class="link-item-text">
                            <h5>회원 관리</h5>
                        </div>
                    </a>
                </div>
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="/admin/category/management">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/category.png">
                        </div>
                        <div class="link-item-text">
                            <h5>카테고리 추가</h5>
                        </div>
                    </a>
                </div>
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="#">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/banner.png">
                        </div>
                        <div class="link-item-text">
                            <h5>배너 관리(미완)</h5>
                        </div>
                    </a>
                </div>
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="#">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/undefined.png">
                        </div>
                        <div class="link-item-text">
                            <h5>미정1</h5>
                        </div>
                    </a>
                </div>
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="#">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/undefined.png">
                        </div>
                        <div class="link-item-text">
                            <h5>미정2</h5>
                        </div>
                    </a>
                </div>
            </section>
        </section>
    </section>
</section>
</body>
</html>