<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <title>상품관리(판매자)</title>

</head>
<header>
    <jsp:include page="../../component/navbar.jsp"></jsp:include>
</header>
<div class="link-item-wrapper" style="width: fit-content; height: fit-content; position: fixed">
    <a class="link-item" href="/seller/product/add">
        <div class="link-item-icon-section">
            <img class="link-item-icon" src="/images/icon/add_product.png">
        </div>
        <div class="link-item-text">
            <h5>상품 추가</h5>
        </div>
    </a>
</div>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">상품 관리</h2>
        </header>
        <section class="main-content-section">
            <section class="product-list-section"
                     style="display: flex; flex-direction: column; align-items: center; width: 100%">
                <c:forEach items="${productList}" var="item">
                    <div class="product-card card m-3" style="width: 70%; height: 10%">
                        <div class="row g-0">
                            <a href="/seller/product/${item.id}" class="col-md-4">
                                <img src="${item.imageList[0].imageAddress}" class="img-fluid rounded-start"
                                     alt="item-image">
                            </a>
                            <div class="col-md-8">
                                <div class="card-body">
                                    <h3 class="card-title">${item.name}</h3>
                                    <p>
                                        <span class="fw-bold text-danger">가격 : </span>${item.price}<br>
                                        <span class="fw-bold ">수량 : </span>${item.stock}
                                    </p>
                                    <p class="product-card-description">${item.description}</p>
                                    <p class="card-text">
                                        <small class="text-body-secondary">
                                            등록일 : ${item.uploadDate}
                                        </small>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </section>
        </section>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="/seller/product/management?pageNumber=${startPage - 1}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="${startPage}" end="${endPage}" step="1" var="page">
                    <li class="page-item"><a class="page-link ${page == currentPage ? 'active' : ''}"
                                             href="/seller/product/management?pageNumber=${page}">${page}</a></li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href="/seller/product/management?pageNumber=${endPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </section>
</section>
</body>
</html>