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
    <title>주문 목록(관리자)</title>

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
</section>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">주문 관리</h2>
        </header>
        <section class="main-content-section">
            <section class="product-list-section"
                     style="display: flex; flex-direction: column; align-items: center; width: 100%; margin-top:1.5em">
                <c:forEach items="${orderList}" var="order">
                    <div class="card border-secondary mb-3" style="width: 60%">
                        <div class="card-header bg-transparent border-secondary text-info" style="font-size: x-large">
                            주문번호 : ${order.id} -
                            <c:choose>
                                <c:when test="${order.status == 1}">결제 전</c:when>
                                <c:when test="${order.status == 2}">결제 완료</c:when>
                                <c:when test="${order.status == 3}">구매 확정</c:when>
                                <c:when test="${order.status == 4}">환불</c:when>
                            </c:choose>
                            <span style="color: darkslateblue">
                                (
                                <c:choose>
                                    <c:when test="${order.delivery.status == 1}">배송 전 <img class="status-img" src="/images/icon/packaging.png"></c:when>
                                    <c:when test="${order.delivery.status == 2}">배송 중 <img class="status-img" src="/images/icon/delivering.png"></c:when>
                                    <c:when test="${order.delivery.status == 3}">배송 완료 <img class="status-img" src="/images/icon/delivered.png"></c:when>
                                </c:choose>
                                )
                            </span>
                        </div>
                        <div class="card-body" style="display: flex; justify-content: start">
                            <div class="text-secondary" style="width: 50%">
                                <h5 class="card-title">주문 내용</h5>
                                <p class="card-text">
                                    <span style="color: red; font-weight: bold">가격 : </span>
                                    <span style="color: orangered;">${order.orderPrice}</span><br>
                                    <c:forEach items="${order.orderItemList}" var="item">
                                        <div class="mb-2">
                                        ${item.product.name}<br>
                                    <span style="color: red">수량 : </span> ${item.count}
                                    <span style="color: darkslateblue">결제 금액 : </span> <span
                                            style="color: orange">${item.totalPrice}</span>
                            </div>
                                    </c:forEach>
                                </p>
                            </div>
                            <div class="text-secondary" style="width: 50%; display: flex; justify-content: end">
                                <input type="hidden" class="order-id" value="${order.id}">
                                <button class="delivery-btn btn btn-outline-secondary" style="max-height: 3em">배송 확인</button>
                            </div>
                        </div>
                        <div class="card-footer bg-transparent border-success">주문 날짜 : ${order.orderDate}</div>
                    </div>
                </c:forEach>
            </section>
        </section>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="/seller/order/orders?pageNumber=${startPage - 1}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="${startPage}" end="${endPage}" step="1" var="page">
                    <li class="page-item"><a class="page-link ${page == currentPage ? 'active' : ''}"
                                             href="/seller/order/orders?pageNumber=${page}">${page}</a></li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href="/seller/order/orders?pageNumber=${endPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </section>
</section>

<script>
    $().ready(() => {
        // 배송 확인 버튼
        $(".delivery-btn").click((event) => {
            let orderId = $(event.target).parent().find(".order-id").val()
            location.href = "/order/" + orderId + "/delivery"
        })

        // 구매 확정 버튼
        $(".confirm-btn").click((event) => {
            let orderId = $(event.target).parent().find(".order-id").val()

            Swal.fire({
                title: '구매를 확정하시겠습니까?',
                imageWidth: 400,
                imageHeight: 200,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '예',
                cancelButtonText: '아니오',
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        url: '/order/confirm',
                        method: 'post',
                        type: 'json',
                        data: {orderId: orderId},
                        dataType: '',
                        success: function (response) {
                            Swal.fire({
                                    title: "구매확정 완료!",
                                    icon: 'success'
                                }
                            ).then((result) => {
                                location.reload()
                            })
                        }
                    });
                }
            })
        })

        // 주문 목록 버튼
        $("#orders-btn").click(() => {
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