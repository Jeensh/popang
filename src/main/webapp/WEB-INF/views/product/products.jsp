<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/products.js"></script>
    <meta charset="UTF-8">
    <title>상품 목록</title>
</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">상품 목록</h2>
        </header>
        <section class="main-content-section">
            <div class="row row-cols-1 row-cols-md-3 mt-1 g-4">
                <c:forEach items="${productList}" var="product">
                    <div class="col">
                        <div class="product-card card h-100"
                             style="max-height: 450px; display: flex; justify-content: center">
                            <div class="border-bottom"
                                 style="display: flex; justify-content: center; align-items: center; width: 100%; height: 50%; border-width: thin; border-color: lightgray">
                                <a href="/products/${product.id}">
                                    <img src="${product.imageList[0].imageAddress}" class="card-img-top"
                                         alt="prouct-image"
                                         style="width: 100%; height: 100%">
                                </a>
                            </div>
                            <div class="product-card-body card-body" style="width: 90%; height: 40%">
                                <h5 class="card-title">${product.name}</h5>
                                <p class="product-card-body" style="max-height: 3em">${product.description}</p>
                                <p class="product-card-body" style="max-height: 1.5em"><span style="color: red; font-weight: bold">평점 : </span><span style="color: gold">${product.score}</span></p>
                                <p class="product-card-body" style="max-height: 1.5em"><span style="color: orangered; font-weight: bold">가격 : </span><span style="color: #dc3545">${product.price}</span></p>
                            </div>
                            <div class="card-footer" style="height: 10%">
                                <small class="text-body-secondary">판매자 : ${product.sellerName}</small>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
        <nav aria-label="Page navigation example" style="margin-top: 1em;">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link"
                       href='/products?pageNumber=${startPage - 1}&categoryCode=${categoryCode}&keyword=${keyword}'
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="${startPage}" end="${endPage}" step="1" var="page">
                    <li class="page-item"><a class="page-link ${page == currentPage ? 'active' : ''}"
                                             href='/products?pageNumber=${page}&categoryCode=${categoryCode}&keyword=${keyword}'>${page}</a>
                    </li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link"
                       href='/products?pageNumber=${endPage + 1}&categoryCode=${categoryCode}&keyword=${keyword}'
                       aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </section>
</section>

</body>
</html>