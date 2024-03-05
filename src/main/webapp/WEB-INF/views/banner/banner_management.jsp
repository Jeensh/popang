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
    <title>배너관리(관리자)</title>
</head>
<header>
    <jsp:include page="../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">배너 관리</h2>
        </header>
        <section class="user-management-content-section">
            <table class="user-table table table-striped-columns">
                <tr>
                    <th>아이디</th>
                    <th>주소</th>
                    <th>삭제</th>
                </tr>
                <c:forEach items="${bannerList}" var="banner">
                    <tr>
                        <td class="banner-id">${banner.id}</td>
                        <td class="banner-address" style="max-width: 200px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;">
                           ${banner.address}
                        </td>
                        <td>
                            <button class="btn btn-outline-danger delete-btn">삭제</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
        <form action="/admin/banner/add" method="post" style="display: flex; justify-content: center">
            <div class="add-category-input input-group mb-2" style="width: 50%">
                <span class="input-group-text">주소</span>
                <input type="text" class="form-control text-center" name="address">
                <button type="submit" class="btn btn-outline-dark" id="set-add-button">등록</button>
            </div>
        </form>
    </section>
</section>

<script>
    $().ready(() => {
        $(".delete-btn").click(function (button) {
            Swal.fire({
                title: '정말 삭제 하시겠습니까?',
                text: "다시 되돌릴 수 없습니다. 신중하세요.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '승인',
                cancelButtonText: '취소',
            }).then((result) => {
                if (result.isConfirmed) {
                    let bannerItem = $(button.target).parent().parent()

                    $.ajax({
                        url: '/admin/banner/delete',
                        type: 'post',
                        data: {id: bannerItem.find(".banner-id").text()},
                        dataType: 'json',
                        success: function (response) {
                            let title_data = '삭제가 완료되었습니다'
                            let message = response.message
                            let icon_data = 'success'
                            if (!response.success) {
                                title_data = '삭제에 실패하였습니다.'
                                message = response.message
                                icon_data = 'error'
                            }
                            Swal.fire({
                                title: title_data,
                                text: message,
                                icon: icon_data,
                            }).then(() => {
                                location.reload()
                            })
                        },
                    });
                }
            })
        })
    })
</script>
</body>
</html>