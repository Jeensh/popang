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
    <title>환불 목록</title>

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
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content">
        <button class="link-item border-0 bg-white" id="refund-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/refund.png">
            </div>
            <div class="link-item-text">
                <h5>환불 목록</h5>
            </div>
        </button>
    </div>
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content" ;>
        <button class="link-item border-0 bg-white" id="review-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/review.png">
            </div>
            <div class="link-item-text">
                <h5>리뷰 작성</h5>
            </div>
        </button>
    </div>
</section>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">환불 관리</h2>
        </header>
        <section class="main-content-section">
            <section class="product-list-section"
                     style="display: flex; flex-direction: column; align-items: center; width: 100%; margin-top:1.5em">
                <c:forEach items="${refundList}" var="refund">
                    <div class="card border-secondary mb-3" style="width: 60%">
                        <div class="card-header bg-transparent border-secondary text-info" style="font-size: x-large">
                            주문번호 : ${refund.order.id} -
                            <c:choose>
                                <c:when test="${refund.status == 1}">환불 중</c:when>
                                <c:when test="${refund.status == 2}">환불 완료</c:when>
                            </c:choose>
                            <span style="color: darkslateblue">
                                (
                                <c:choose>
                                    <c:when test="${refund.delivery.status == 1}">배송 전 <img class="status-img" src="/images/icon/packaging.png"></c:when>
                                    <c:when test="${refund.delivery.status == 2}">배송 중 <img class="status-img" src="/images/icon/delivering.png"></c:when>
                                    <c:when test="${refund.delivery.status == 3}">배송 완료 <img class="status-img" src="/images/icon/delivered.png"></c:when>
                                </c:choose>
                                )
                            </span>
                        </div>
                        <div class="card-body" style="display: flex; justify-content: start">
                            <div class="text-secondary" style="width: 50%">
                                <h5 class="card-title">환불 내용</h5>
                                <p class="card-text">
                                    <span style="color: orangered; font-size: large;">환불 신청일 : </span>
                                    <c:if test="${refund.enrollDate == null}">
                                    미정
                                    </c:if>
                                    <c:if test="${refund.enrollDate != null}">
                                        ${refund.enrollDate}
                                    </c:if>
                                    <br>
                                    <span style="color: cornflowerblue; font-size: large">환불 완료일 : </span>
                                    <c:if test="${refund.refundDate == null}">
                                    미정
                                    </c:if>
                                    <c:if test="${refund.refundDate != null}">
                                        ${refund.refundDate}
                                    </c:if>
                                    <br>
                                    <span style="color: red; font-weight: bold">가격 : </span>
                                    <span style="color: orangered;">${refund.order.orderPrice}</span><br>
                                </p>
                            </div>
                            <div class="text-secondary" style="width: 50%; display: flex; justify-content: end">
                                <input type="hidden" class="refund-id" value="${refund.id}">
                                <button class="delivery-btn btn btn-outline-secondary" style="max-height: 3em">배송 확인</button>
                            </div>
                        </div>
                        <div class="card-footer bg-transparent border-success">주문 날짜 : ${refund.order.orderDate}</div>
                    </div>
                </c:forEach>
            </section>
        </section>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="/order/refunds?pageNumber=${startPage - 1}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="${startPage}" end="${endPage}" step="1" var="page">
                    <li class="page-item"><a class="page-link ${page == currentPage ? 'active' : ''}"
                                             href="/order/refunds?pageNumber=${page}">${page}</a></li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href="/order/refunds?pageNumber=${endPage + 1}" aria-label="Next">
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
            let refundId = $(event.target).parent().find(".refund-id").val()
            location.href = "/order/refund/" + refundId + "/delivery"
        })

        // 주문 목록 이동 버튼
        $("#orders-btn").click(() => {
            location.href = "/order/orders"
        })

        // 리뷰 목록 이동 버튼
        $("#review-btn").click(() => {
            location.href = "/order/reviews"
        })
    })
</script>
</body>
</html>