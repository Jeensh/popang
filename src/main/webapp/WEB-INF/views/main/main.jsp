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
    <section class="main-body bg-light-subtle" style="display: flex; flex-direction: column; align-items: center">
        <section class="main-content-section rounded-3 border-2" style="background-color: #EDEEF0; width: 95%; margin-top: 1em; padding: 5px">
            <div style="display: flex; justify-content: center; margin-top:1em">
                <h1 style="font-weight: bold">< 판매량 <span style="color: orangered">TOP 5</span> ></h1>
            </div>
            <div class="row row-cols-1 row-cols-md-3 g-4" style="display: flex; justify-content: center">
                <c:forEach items="${topSales}" var="item">
                    <div class="col" style="width: 30%; height: 50%">
                        <div class="product-card card" style="width: 100%; height: 400px">
                            <a href="/products/${item.id}" style="height: 65%"><img src="${item.imageList[0].imageAddress}" class="card-img-top" alt="..." style="height: 100%"></a>
                            <div class="card-body" style="height: 35%">
                                <h5 class="card-title">${item.name}</h5>
                                <p class="product-card-body" style="height: 80%">
                                    <span style="color: red; font-weight: bold">평점 : </span><span style="color: gold">${item.score}</span><br>
                                    <span style="color: orangered; font-weight: bold">가격 : </span><span style="color: #dc3545">${item.price}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
        <section class="main-content-section rounded-3 border-2" style="background-color: #EDE1E3; width: 95%; margin-top: 1em; padding: 5px">
            <div style="display: flex; justify-content: center; margin-top:1em">
                <h1 style="font-weight: bold">< 평점 <span style="color: orangered">TOP 5</span> ></h1>
            </div>
            <div class="row row-cols-1 row-cols-md-3 g-4" style="display: flex; justify-content: center">
                <c:forEach items="${topScores}" var="item">
                    <div class="col" style="width: 30%; height: 50%">
                        <div class="product-card card" style="width: 100%; height: 400px">
                            <a href="/products/${item.id}" style="height: 65%"><img src="${item.imageList[0].imageAddress}" class="card-img-top" alt="..." style="height: 100%"></a>
                            <div class="card-body" style="height: 35%">
                                <h5 class="card-title">${item.name}</h5>
                                <p class="product-card-body" style="height: 80%">
                                    <span style="color: red; font-weight: bold">평점 : </span><span style="color: gold">${item.score}</span><br>
                                    <span style="color: orangered; font-weight: bold">가격 : </span><span style="color: #dc3545">${item.price}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
        <section class="main-content-section rounded-3 border-2" style="background-color: #D1DFE8; width: 95%; margin-top: 1em; padding: 5px">
            <div style="display: flex; justify-content: center; margin-top:1em">
                <h1 style="font-weight: bold">< 조회수 <span style="color: orangered">TOP 5</span> ></h1>
            </div>
            <div class="row row-cols-1 row-cols-md-3 g-4" style="display: flex; justify-content: center">
                <c:forEach items="${topViews}" var="item">
                    <div class="col" style="width: 30%; height: 50%">
                        <div class="product-card card" style="width: 100%; height: 400px">
                            <a href="/products/${item.id}" style="height: 65%"><img src="${item.imageList[0].imageAddress}" class="card-img-top" alt="..." style="height: 100%"></a>
                            <div class="card-body" style="height: 35%">
                                <h5 class="card-title">${item.name}</h5>
                                <p class="product-card-body" style="height: 80%">
                                    <span style="color: red; font-weight: bold">평점 : </span><span style="color: gold">${item.score}</span><br>
                                    <span style="color: orangered; font-weight: bold">가격 : </span><span style="color: #dc3545">${item.price}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </section>
</section>
</body>
</html>