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
    <title>배송 상세</title>

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
    <c:if test="${auth.role == 2}">
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
    </c:if>
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content" ;>
        <button class="link-item border-0 bg-white" id="refund-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/refund.png">
            </div>
            <div class="link-item-text">
                <h5>환불 목록</h5>
            </div>
        </button>
    </div>
</section>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">배송 상세</h2>
        </header>
        <section class="main-content-section" style="display: flex; justify-content: center">
            <div class="card mt-3 mb-3" style="width: 80%">
                <div class="card-header text-info" style="font-size: xx-large">
                    <input type="hidden" id="delivery-id" value="${refund.delivery.id}">
                    <input type="hidden" id="user-role" value="${auth.role}">
                    배송 번호 : ${refund.delivery.id}
                    (
                    <c:choose>
                        <c:when test="${refund.delivery.status == 1}">배송 전 <img class="status-img" src="/images/icon/packaging.png"></c:when>
                        <c:when test="${refund.delivery.status == 2}">배송 중 <img class="status-img" src="/images/icon/delivering.png"></c:when>
                        <c:when test="${refund.delivery.status == 3}">배송 완료 <img class="status-img" src="/images/icon/delivered.png"></c:when>
                    </c:choose>
                    )
                    <c:if test='${refund.delivery.sender.id == auth.id}'>
                        <span>
                        <c:choose>
                            <c:when test="${refund.delivery.status == 1}">
                                <button id="delivery-start-btn" class="btn btn-outline-info">배송 하기</button>
                            </c:when>
                            <c:when test="${refund.delivery.status == 2}">
                                <button id="deliver-finish-btn" class="btn btn-outline-info">배송완료 하기</button>
                            </c:when>
                        </c:choose>
                        </span>
                    </c:if>
                </div>
                <div class="card-body">
                    <h5 class="card-title" style="font-size: x-large">배송 정보</h5>
                    <p class="card-text">
                        <span style="font-size: large;">발송 : </span>${refund.delivery.sender.name}<br>
                        <span style="font-size: large;">수신 : </span>${refund.order.delivery.sender.name}<br>
                        <span style="color: orangered; font-size: large;">출발일 : </span>
                        <c:if test="${refund.delivery.departureDate == null}">
                            미정
                        </c:if>
                        <c:if test="${refund.delivery.departureDate != null}">
                            ${refund.delivery.departureDate}
                        </c:if>
                        <br>
                        <span style="color: cornflowerblue; font-size: large">도착일 : </span>
                        <c:if test="${refund.delivery.arrivalDate == null}">
                            미정
                        </c:if>
                        <c:if test="${refund.delivery.arrivalDate != null}">
                            ${refund.delivery.arrivalDate}
                        </c:if>
                    </p>
                    <h5 class="card-title" style="font-size: x-large">도착지</h5>
                    <p class="card-text">
                        <span style="color: darkslateblue; font-size: large">메인 주소 : </span>
                        <br>
                        <c:if test="${refund.delivery.address.mainAddress == null}">
                            <span style="margin-left: 1em">미정</span>
                        </c:if>
                        <c:if test="${refund.delivery.address.mainAddress != null}">
                            <span style="margin-left: 1em">${refund.delivery.address.mainAddress}</span>
                        </c:if>
                        <br>
                        <span style="color: darkslateblue; font-size: large">상세 주소 : </span>
                        <br>
                        <c:if test="${refund.delivery.address.detailAddress == null}">
                            <span style="margin-left: 1em">미정</span>
                        </c:if>
                        <c:if test="${refund.delivery.address.detailAddress != null}">
                            <span style="margin-left: 1em">${refund.delivery.address.detailAddress}</span>
                        </c:if>
                    </p>
                </div>
                <div class="card-footer text-body-secondary">
                    주문 날짜 : ${refund.order.orderDate}
                </div>
            </div>
        </section>
    </section>
</section>

<script>
    $().ready(() => {
        // 배송완료 버튼
        $("#deliver-finish-btn").click((event) => {
            let deliveryId = $("#delivery-id").val()

            Swal.fire({
                title: '배송을 완료하시겠습니까?',
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
                        url: '/order/refund/delivery/finish',
                        method: 'post',
                        type: 'json',
                        data: {deliveryId: deliveryId},
                        dataType: '',
                        success: function (response) {
                            Swal.fire({
                                    title: "배송 완료!",
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

        // 배송하기 버튼
        $("#delivery-start-btn").click((event) => {
            let deliveryId = $("#delivery-id").val()

            Swal.fire({
                title: '배송을 시작하시겠습니까?',
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
                        url: '/order/refund/delivery/start',
                        method: 'post',
                        type: 'json',
                        data: {deliveryId: deliveryId},
                        dataType: '',
                        success: function (response) {
                            Swal.fire({
                                    title: "배송 시작!",
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

        // 환불 목록 이동 버튼
        $("#refund-btn").click(() => {
            location.href = "/order/refunds"
        })
    })
</script>
</body>
</html>