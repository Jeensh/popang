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
    <title>장바구니</title>

</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
</header>
<section id="sideMenu"
         style="display: flex;  flex-direction: column; align-items: center; position: fixed; margin-top: 1em; margin-left: 1em">
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content">
        <button class="link-item border-0 bg-white" id="buy-all-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/pay.png">
            </div>
            <div class="link-item-text">
                <h5>일괄 주문</h5>
            </div>
        </button>
    </div>
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content" ;>
        <button class="link-item border-0 bg-white" id="buy-select-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/pick.png">
            </div>
            <div class="link-item-text">
                <h5>선택 주문</h5>
            </div>
        </button>
    </div>
    <div class="link-item-wrapper" style="width: fit-content; height: fit-content" ;>
        <button class="link-item border-0 bg-white" id="clear-btn">
            <div class="link-item-icon-section">
                <img class="link-item-icon" src="/images/icon/trashcan.png">
            </div>
            <div class="link-item-text">
                <h5>비우기</h5>
            </div>
        </button>
    </div>
</section>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">장바구니</h2>
            <input type="hidden" id="cart-id" value="${cart.id}">
        </header>
        <section class="main-content-section">
            <div class="row row-cols-1 row-cols-md-3 g-4 p-3">
                <c:forEach items="${cart.cartItemList}" var="item">
                    <div class="col">
                        <div class="cart-item card" style="height: 30vw;">
                            <input type="checkbox" id="checkbox${item.id}" name="cartItem-id" style="display: none"
                                   value="${item.id}">
                            <label for="checkbox${item.id}" style="width: 100%; height: 60%">
                                <img src="${item.product.imageList[0].imageAddress}" class="card-img-top"
                                     alt="product-image" style="width: 100%; height: 100%">
                            </label>
                            <div class="card-body" style="height: 27%; overflow: hidden">
                                <h5 class="card-title">${item.product.name}</h5>
                                <p>
                                    <span class="fw-bold text-danger">총 가격 : </span>
                                    <span id="total-price-${item.id}">${item.product.price * item.count}</span><br>
                                    <span class="fw-bold ">구매 수량 : </span>${item.count}
                                </p>
                            </div>
                            <div class="card-footer"
                                 style="display: flex; justify-content: end; height: 13%; overflow: hidden">
                                    <%--                                <button class="update-btn btn btn-outline-secondary me-2" style="overflow: hidden">개수 조정</button>--%>
                                <button class="delete-btn btn btn-danger" style="overflow: hidden">삭제</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </section>
</section>

<script>
    function buySelected() {
        // 체크된 항목들의 총 가격을 저장할 변수
        let totalPrice = 0;
        let cartItemIdList = [];

        // 각 체크된 항목을 순회하면서 총 가격 계산
        $('input[name="cartItem-id"]:checked').each(function () {
            // 체크된 항목의 총 가격을 계산하여 누적
            let itemId = $(this).val();
            cartItemIdList.push(itemId);
            totalPrice += parseInt($("#total-price-" + itemId).text());
        });

        Swal.fire({
            title: '배송 주소를 입력해주세요',
            html:
                '<table style="display: flex; justify-content: center;">' +
                '<tr>' +
                '<td>총 가격 : ' + totalPrice + '<br></td>' +
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

                let param = {
                    mainAddress: mainAddress,
                    detailAddress: detailAddress,
                    idList: cartItemIdList
                }

                console.log(param)

                $.ajax({
                    url: '/order/cart/purchase',
                    type: 'post',
                    data: param,
                    dataType: 'json',
                    success: function (response) {
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
                            if (response.success) {
                                location.reload()
                            }
                        })

                    },
                    error: function (response) {
                        console.log(response)
                    }
                });
            },
            allowOutsideClick: () => !Swal.isLoading()
        })
    }

    $().ready(() => {
        // 일괄 주문
        $('#buy-all-btn').click((event) => {
            $('input[name="cartItem-id"]').each(function () {
                $(this).attr("checked", 'true')
            });

            buySelected()
        })

        // 선택 주문
        $('#buy-select-btn').click((event) => {
            buySelected()
        })

        // 비우기
        $('#clear-btn').click((event) => {
            let cartId = $("#cart-id").val()

            Swal.fire({
                title: '장바구니를 비우시겠습니까?',
                imageWidth: 400,
                imageHeight: 200,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '네',
                cancelButtonText: '아니오',
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        url: '/order/cart/clear',
                        method: 'post',
                        type: 'json',
                        data: {cartId: cartId},
                        dataType: '',
                        success: function (response) {
                            Swal.fire({
                                    title: "장바구니 비우기 완료!",
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

        // 삭제 버튼
        $('.delete-btn').click((event) => {
            let cartItemId = $(event.target).parent().prev().prev().prev().val()
            let card = $(event.target).parent().parent()

            Swal.fire({
                title: '나를 버리지 말아다오...',
                imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT43M6jgd1dm9HCEVmekeNRw8odkNqJhozliA&usqp=CAU',
                imageWidth: 400,
                imageHeight: 200,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '삭제',
                cancelButtonText: '취소',
                // reverseButtons: true, // 버튼 순서 거꾸로
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        url: '/order/cart/delete',
                        method: 'post',
                        type: 'json',
                        data: {carItemId: cartItemId},
                        dataType: '',
                        success: function (response) {
                            let title = "나는 니가 밉다.."
                            let icon = 'success'
                            Swal.fire({
                                    title: title,
                                    icon: icon
                                }
                            )
                            card.remove()
                        }
                    });
                }
            })
        });
    })
</script>
</body>
</html>