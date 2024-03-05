<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <title>메인(판매자)</title>

</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">판매자 애플리케이션</h2>
        </header>
        <section class="main-content-section">
            <section class="main-link-list row">
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a class="link-item" href="/seller/product/management">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/product.png">
                        </div>
                        <div class="link-item-text">
                            <h5>상품 관리(미완)</h5>
                        </div>
                    </a>
                </div>
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="#">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/order.png">
                        </div>
                        <div class="link-item-text">
                            <h5>주문 관리(미완)</h5>
                        </div>
                    </a>
                </div>
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="#">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/revenue.png">
                        </div>
                        <div class="link-item-text">
                            <h5>매출 관리(미완)</h5>
                        </div>
                    </a>
                </div>
                <div class="link-item-wrapper bg-body-secondary col-lg-4 col-md-4 col-sm-6 col-12 border">
                    <a href="#">
                        <div class="link-item-icon-section">
                            <img class="link-item-icon" src="/images/icon/coupon.png">
                        </div>
                        <div class="link-item-text">
                            <h5>쿠폰 관리(미완)</h5>
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
            </section>
        </section>
    </section>
</section>
</body>
</html>