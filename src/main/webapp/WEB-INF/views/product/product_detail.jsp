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
    <script src="https://cdn.ckeditor.com/ckeditor5/41.1.0/classic/ckeditor.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fslightbox/3.4.1/index.min.js"></script>
    <script type="text/javascript" src="/js/product_detail.js"></script>
    <title>상품 상세</title>

</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin: 1.5%; color: dimgray">${categoryPath} => ${product.name}</h2>
        </header>
        <section class="main-content-section" style="height: fit-content">
            <section class="product-info-section" style="display: flex; justify-content: center;">
                <section class="product-detail-image-section" style="width: 50%; height: 32vw">
                    <a id="main-image-link" href="${product.imageList[0].imageAddress}">
                        <img id="main-image" src="${product.imageList[0].imageAddress}" alt="Image"
                             style="width: 100%; height: 28vw">
                    </a>
                    <div class="rounded-bottom-2"
                         style="height: 15%; width: 100%; background-color: dimgray; opacity: 50%; display: flex; justify-content: start; align-items: center">
                        <c:forEach items="${product.imageList}" var="image">
                            <img class="rounded-2 product-card sub-image" src="${image.imageAddress}" alt="Image"
                                 style="height: 80%; margin: 1em">
                        </c:forEach>
                    </div>
                </section>
                <section class="product-info product-info-summary" style="width: 45%; margin-top: 1.5em; height: 32vw">
                    <div style="display: flex; justify-content: center; margin: 1em">
                        <span style="font-weight: bold; font-size: xx-large; color: black" id="product-name">${product.name}</span>
                    </div>
                    <hr color="black" size="5" width="105.5%">
                    <div style="font-size: large; margin-left: 2em">
                        <p><span style="font-size: x-large; font-weight: bold">가격 : </span><span
                                style="color: red" id="product-price">${product.price}</span></p>
                        <p><span style="font-size: x-large; font-weight: bold">재고 : </span><span
                                style="color: green">${product.stock}</span></p>
                        <p><span style="font-size: x-large; font-weight: bold">판매자 : </span><span
                                style="color: blue">${product.sellerName}</span></p>
                        <p><span style="font-size: x-large; font-weight: bold">요약 : </span>${product.description}</p>
                    </div>
                    <c:if test="${role == 1}">
                        <form class="product-order-section" id="product-order-form" method="post"
                              style="display: flex; justify-content: end;">
                            <input type="hidden" name="productId" id="product-id" value="${product.id}">
                            <div class="input-group input-group-lg me-2 mb-0" style="width: 50%; height: 75%">
                                <span class="input-group-text">수량</span>
                                <input type="text" class="form-control" aria-label="Sizing example input"
                                       aria-describedby="inputGroup-sizing-lg" name="count" id="count">
                            </div>
                            <button type="button" id="purchase-btn" class="btn btn-outline-dark m-2">바로 구매</button>
                            <button type="button" id="add-cart-btn" class="btn btn-outline-success m-2">장바구니 추가</button>
                        </form>
                    </c:if>
                </section>
            </section>
            <hr color="black" size="5" width="100%">
            <section class="product-detail-section">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <button class="nav-link active" id="product-detail-btn">상품 정보</button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link" id="product-review-btn">리뷰</button>
                    </li>
                </ul>
                <section style="display: flex; justify-content: center">
                    <section id="product-detail-section">
                        ${product.descriptionDetail}
                    </section>
                    <section id="product-review-section" style="display: none">
                        리뷰 섹션
                    </section>
                </section>
            </section>
        </section>
    </section>
</section>

<script>
    let productId
    let productName
    let price

    $().ready(() => {
        productId = $('#product-id').val()
        productName = $('#product-name').text()
        price = $('#product-price').text()

        // 장바구니 버튼
        $('#add-cart-btn').click(() => {
            let count = $('#count').val()

            Swal.fire({
                title: '상품을 장바구니에 추가하시겠습니까?',
                html:
                    '<table style="display: flex; justify-content: center; font-weight: bold">' +
                    '<tr>' +
                    '<td>상품명 : ' + productName + '<br></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>개수 : ' + count + '<br></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>가격 : ' + price * count + '<br></td>' +
                    '</tr>' +
                    '</table>',
                showCancelButton: true,
                confirmButtonText: '추가하기',
                cancelButtonText: '취소',
                showLoaderOnConfirm: true,
                preConfirm: (isConfirmed) => {
                    let param = {
                        count: count,
                        productId: productId
                    }

                    $.ajax({
                        url: '/order/cart/add',
                        type: 'post',
                        data: param,
                        dataType: 'json',
                        success: function(response){
                            let title_data = '장바구니에 상품이 추가되었습니다.'
                            let message = response.message
                            let icon_data = 'success'
                            if (!response.success) {
                                title_data = '상품 추가에 실패했습니다.'
                                message = response.message
                                icon_data = 'error'
                            }
                            Swal.fire({
                                title: title_data,
                                html: "<span style=\"color: #dc3545\">" + message + "</span>",
                                icon: icon_data,
                            }).then((result) => {
                                if(response.success){
                                    // location.href = "/seller/product/management?pageNumber=1"
                                }
                            })

                        },
                        error: function(response){
                            console.log(response)
                        }
                    });
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        })

        // 즉시 구매 버튼
        $("#purchase-btn").click(() => {
            let count = $('#count').val()

            Swal.fire({
                title: '배송 주소를 입력해주세요',
                html:
                    '<table style="display: flex; justify-content: center;">' +
                    '<tr>' +
                    '<td>상품명 : ' + productName + '<br></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>구매 개수 : ' + count + '<br></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>구매 가격 : ' + price * count + '<br></td>' +
                    '</tr>' +
                    '</table>' +
                    '<input id="address" class="swal2-input" name="mainAddress" placeholder="주소">' +
                    '<input id="detail-address" class="swal2-input" name="detailAddress" placeholder="상세 주소">',
                showCancelButton: true,
                confirmButtonText: '결제하기',
                cancelButtonText: '취소',
                showLoaderOnConfirm: true,
                preConfirm: (isPayed) => {
                    let mainAddress = $('input[name=mainAddress]').val()
                    let detailAddress = $('input[name=detailAddress]').val()
                    let totalPrice = price * count

                    let param = {
                        mainAddress: mainAddress,
                        detailAddress: detailAddress,
                        totalPrice: totalPrice,
                        count: count,
                        productId: productId
                    }

                    $.ajax({
                        url: '/order/purchase',
                        type: 'post',
                        data: param,
                        dataType: 'json',
                        success: function(response){
                            let title_data = '구매가 완료되었습니다.'
                            let message = response.message
                            let icon_data = 'success'
                            if (!response.success) {
                                title_data = '구매에 실패하였습니다.'
                                message = response.message
                                icon_data = 'error'
                            }
                            Swal.fire({
                                title: title_data,
                                html: "<span style=\"color: #dc3545\">" + message + "</span>",
                                icon: icon_data,
                            }).then((result) => {
                                if(response.success){
                                    location.reload();
                                }
                            })

                        },
                        error: function(response){
                            console.log(response)
                        }
                    });
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        });

        // 서브 이미지
        $('.sub-image').click((event) => {
            let subimg = $(event.target)
            let mainimg = $('#main-image')

            mainimg.parent().attr("href", subimg.attr('src'))
            mainimg.attr("src", subimg.attr("src"))
        })

        // 상품 정보 탭
        $('#product-detail-btn').click((event) => {
            let detailBtn = $(event.target)
            let reivewBtn = $('#product-review-btn')
            detailBtn.addClass("active")
            reivewBtn.removeClass("active")

            let detailSection = $('#product-detail-section')
            let reviewSection = $('#product-review-section')

            reviewSection.css("display", "none")
            detailSection.css("display", "block")

        })

        // 상품 리뷰 탭
        $('#product-review-btn').click((event) => {
            let reviewBtn = $(event.target)
            let detailBtn = $('#product-detail-btn')
            reviewBtn.addClass("active")
            detailBtn.removeClass("active")

            let detailSection = $('#product-detail-section')
            let reviewSection = $('#product-review-section')

            detailSection.css("display", "none")
            reviewSection.css("display", "block")
        })
    })
</script>
</body>
</html>