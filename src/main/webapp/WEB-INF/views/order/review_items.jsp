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
    <title>리뷰 상품</title>

</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
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
            <h2 style="margin-top: 1%;">리뷰 상품 목록</h2>
        </header>
        <section class="main-content-section">
            <section class="product-list-section"
                     style="display: flex; flex-direction: column; align-items: center; width: 100%; margin-top:1.5em">
                <c:forEach items="${reviewItemList}" var="item">
                    <div class="card border-secondary mb-3" style="width: 60%">
                        <div class="card-header bg-transparent border-secondary"
                             style="font-size: x-large; color: #6f42c1">
                                ${item.product.name}
                        </div>
                        <section style="display: flex; justify-content: start">
                            <div class="card-body" style="display: flex; justify-content: start; width: 50%">
                                <div class="text-secondary" style="width: 50%">
                                    <h5 class="card-title">주문 내용</h5>
                                    <p class="card-text">
                                        <span style="font-size: large; color: orangered">주문 번호 : </span>${item.orderId}<br>
                                        <span style="font-size: large; color: #157347;">주문 수량 : </span>${item.count}<br>
                                        <span style="font-size: large; color: #b02a37">주문 가격 : </span>${item.totalPrice}<br>
                                    </p>
                                </div>
                            </div>
                            <div class="card-body" style="display: flex; justify-content: end; width: 50%">
                                <input type="hidden" name="order-item-id" value="${item.id}">
                                <input type="hidden" name="productId" value="${item.product.id}">
                                <c:if test="${item.review == 1}">
                                    <button class="review-btn btn btn-outline-success" style="max-height: 10em">리뷰 작성
                                    </button>
                                </c:if>
                                <a class="btn btn-outline-info ms-2" href="/products/${item.product.id}"
                                   style="max-height: 10em; text-align: center; display: flex; justify-content: center; align-items: center;">상품
                                    상세</a>
                            </div>
                        </section>
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
        // 리뷰 작성 버튼
        $(".review-btn").click((event) => {
            let productId = $(event.target).prev().val()
            let orderItemId = $(event.target).prev().prev().val()


            Swal.fire({
                title: '리뷰 작성',
                html: '<div style="display: flex; flex-direction: column; justify-content: center; align-items: center">' +
                    '<label for="score" class="form-label">평점</label>' +
                    '<input type="range" name="score" class="form-range" min="0" max="10" step="1" id="score" style="width: 80%">' +
                    '<div class="form-floating" style="width: 80%">' +
                    '<textarea class="form-control" placeholder="Leave a comment here" name="content" id="content"></textarea>' +
                    '<label for="content">코멘트</label>' +
                    '</div>' +
                    '</div>',
                showCancelButton: true,
                confirmButtonText: '저장',
                cancelButtonText: '취소',
                preConfirm: function () {
                    let score = $('input[name=score]').val()
                    let content = $('textarea[name=content]').val()
                    $.ajax({
                        url: '/order/review/write',
                        type: 'post',
                        data: {
                            score: score,
                            content: content,
                            productId: productId,
                            orderItemId: orderItemId
                        },
                        dataType: 'json',
                        success: function (response) {
                            Swal.fire({
                                    title: "리뷰 작성 완료!",
                                    text: response.message,
                                    icon: 'success'
                                }
                            ).then((result) => {
                                location.reload()
                            })
                        }
                    });
                }
            })
        });

        // 주문 목록 이동 버튼
        $("#orders-btn").click(() => {
            location.href = "/order/orders"
        })

        // 환불 목록 이동 버튼
        $("#refund-btn").click(() => {
            location.href = "/order/refunds"
        })

        // 리뷰 목록 이동 버튼
        $("#review-btn").click(() => {
            location.href = "/order/reviews"
        })
    })
</script>
</body>
</html>