<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <title>메인</title>

</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
    <section class="carousel-section" style="display: flex; justify-content: center">
        <div id="carouselExampleFade" class="carousel slide carousel-fade" style="width: 80%;" data-bs-ride="carousel">
            <div class="carousel-inner">
                <c:forEach items="${bannerList}" var="item">
                    <div class="carousel-item active">
                        <img src="${item.address}" class="d-block w-100" alt="banner">
                    </div>
                </c:forEach>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </section>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <section class="main-content-section">
        </section>
    </section>
</section>
</body>
</html>