<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>주문 상품 목록(판매자)</title>

</head>
<header>
    <jsp:include page="../../component/navbar.jsp"></jsp:include>
</header>
<section id="sideMenu"
         style="display: flex;  flex-direction: column; align-items: center; position: fixed; margin-top: 1em; margin-left: 1em">
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content">
        <button class="link-item border-0 bg-white" id="orders-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/order.png">
            </div>
            <div class="link-item-text">
                <h5>주문 목록</h5>
            </div>
        </button>
    </div>
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content" ;>
        <button class="link-item border-0 bg-white" id="order-item-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/product.png">
            </div>
            <div class="link-item-text">
                <h5>주문 상품</h5>
            </div>
        </button>
    </div>
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content" ;>
        <button class="link-item border-0 bg-white" id="refund-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/refund.png">
            </div>
            <div class="link-item-text">
                <h5>환불 상품</h5>
            </div>
        </button>
    </div>
</section>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">주문 상품 목록</h2>
        </header>
        <section class="main-content-section">
            <section class="product-list-section"
                     style="display: flex; flex-direction: column; align-items: center; width: 100%; margin-top:1.5em">
                <c:forEach items="${orderItemList}" var="item">
                    <div class="card border-secondary mb-3" style="width: 60%">
                        <div class="card-header bg-transparent border-secondary" style="font-size: x-large; color: #6f42c1">
                            ${item.product.name}
                        </div>
                        <div class="card-body" style="display: flex; justify-content: start">
                            <div class="text-secondary" style="width: 50%">
                                <h5 class="card-title">주문 내용</h5>
                                <p class="card-text">
                                    <span style="font-size: large; color: orangered">주문 번호 : </span>${item.orderId}<br>
                                    <span style="font-size: large; color: #157347;">주문 수량 : </span>${item.count}<br>
                                    <span style="font-size: large; color: #b02a37">주문 가격 : </span>${item.totalPrice}<br>
                                </p>
                            </div>
                        </div>
                        <div class="card-footer bg-transparent border-success">주문 날짜 : ${item.orderDate}</div>
                    </div>
                </c:forEach>
            </section>
        </section>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="/seller/order/items?pageNumber=${startPage - 1}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="${startPage}" end="${endPage}" step="1" var="page">
                    <li class="page-item"><a class="page-link ${page == currentPage ? 'active' : ''}"
                                             href="/seller/order/items?pageNumber=${page}">${page}</a></li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href="/seller/order/items?pageNumber=${endPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </section>
</section>

<script>
    $().ready(() => {
        // 주문 목록 버튼
        $("#orders-btn").click(() => {
            let role = $("#user-role").val()
            console.log(role)
            if(role == 1)
                location.href = "/order/orders"
            else
                location.href = "/seller/order/orders"
        })

        // 주문 상품 버튼
        $("#order-item-btn").click(() => {
            location.href = "/seller/order/items"
        })

        // 환불 상품 버튼
        $("#refund-btn").click(() => {
            location.href = "/seller/order/refunds"
        })
    })
</script>
</body>
</html>