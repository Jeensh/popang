<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script type="text/javascript" src="/js/seller/product_edit.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>상품수정(판매자)</title>

</head>
<header>
    <jsp:include page="../../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">상품 수정</h2>
        </header>
        <div class="category-list-container">
            <div class="list-group category-list-wrapper" role="tablist">
                <h5 style="margin-top: 4%;">대분류</h5>
                <div class="category-list" id="large-category" style="max-height: 15em; overflow-y: auto;">
                    <c:forEach items="${largeCategories}" var="largeItem">
                        <button type="button"
                                class="large-category-item list-group-item list-group-item-action">
                            <span class="category-name">${largeItem.name}</span><br>
                            (<span class="category-code">${largeItem.code}</span>)
                        </button>
                    </c:forEach>
                </div>
            </div>
            <div class="list-group category-list-wrapper" role="tablist">
                <h5 style="margin-top: 4%">중분류</h5>
                <div class="category-list" id="medium-category" style="max-height: 15em; overflow-y: auto;">
                </div>
            </div>
            <div class="list-group category-list-wrapper" role="tablist">
                <h5 style="margin-top: 4%">소분류</h5>
                <div class="category-list" id="small-category" style="max-height: 15em; overflow-y: auto;">
                </div>
            </div>
        </div>
        <section class="main-content-section" style="display: flex; justify-content: center">
            <form action="/seller/product/add" id="product-add-form" method="post"
                  style="width: 80%; display: flex; flex-direction: column; align-items: center;">
                <input type="hidden" name="categoryCode" id="category-code">
                <div class="input-group input-group-lg mb-2">
                    <span class="input-group-text" id="name">이름</span>
                    <input type="text" class="form-control" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-lg" name="name">
                </div>
                <div class="input-group input-group-lg mb-2">
                    <span class="input-group-text" id="price">가격</span>
                    <input type="text" class="form-control" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-lg" name="price">
                    <span class="input-group-text" id="stock">수량</span>
                    <input type="text" class="form-control" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-lg" name="stock">
                </div>
                <div class="input-group input-group-lg mb-3">
                    <span class="input-group-text" id="description">요약</span>
                    <input type="text" class="form-control" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-lg" name="description">
                </div>
                <div class="input-image-section" style="width: 100%;">
                </div>
                <div style="display: flex; justify-content: center">
                    <button type="button" class="btn btn-outline-dark m-2" id="image-add-btn" onclick="addImageInput(this)">
                        이미지 추가
                    </button>
                    <button type="button" class="btn btn-outline-danger m-2" id="image-delete-btn"
                            onclick="deleteImageInput(this)">이미지 삭제
                    </button>
                </div>
                <div style="display: flex; flex-direction: column; align-items: start; width: 100%; height: 100%">
                    <h4>정보</h4>
                    <textarea style="width: 100%; height: 100%" name="descriptionDetail"></textarea>
                </div>
                <button type="button" class="save-btn btn btn-outline-info mt-3">상품 등록</button>
            </form>
        </section>
    </section>
</section>
</body>
</html>