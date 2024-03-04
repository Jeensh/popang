<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/pospang.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script type="text/javascript" src="/js/jquery-3.7.1.js"></script>
    <script type="text/javascript" src="/js/admin/category_management.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <title>카테고리관리(관리자)</title>

</head>
<header>
    <jsp:include page="../../component/navbar.jsp"></jsp:include>
</header>
<section class="main-body-section ">
    <section class="main-body bg-light-subtle">
        <header class="main-title-section bg-dark-subtle bg-gradient rounded-bottom-5">
            <h2 style="margin-top: 1%;">카테고리 관리</h2>
        </header>
        <section class="main-content-section">
            <div class="category-list-container">
                <div class="list-group category-list" id="large-category" role="tablist">
                    <h5 style="margin-top: 4%">대분류</h5>
                    <c:forEach items="${largeCategories}" var="largeItem">
                        <button class="large-category-item list-group-item list-group-item-action">
                            <span class="category-name">${largeItem.name}</span><br>
                            (<span class="category-code">${largeItem.code}</span>)
                        </button>
                    </c:forEach>
                </div>
                <div class="list-group category-list" id="medium-category" role="tablist">
                    <h5 style="margin-top: 4%">중분류</h5>
                </div>
                <div class="list-group category-list" id="small-category" role="tablist">
                    <h5 style="margin-top: 4%">소분류</h5>
                </div>
            </div>
        </section>
        <section class="add-category-section">
            <form class="add-category-form" id="add-category-form1">
                <div class="add-category-input input-group mb-2">
                    <span class="input-group-text">대</span>
                    <input type="text" class="form-control text-center" name="largeCode" placeholder="코드">
                    <input type="text" class="form-control text-center" name="largeName" placeholder="이름">
                    <span class="input-group-text">중</span>
                    <input type="text" class="form-control text-center" name="mediumCode" placeholder="코드">
                    <input type="text" class="form-control text-center" name="mediumName" placeholder="이름">
                    <span class="input-group-text">소</span>
                    <input type="text" class="form-control text-center" name="smallCode" placeholder="코드">
                    <input type="text" class="form-control text-center" name="smallName" placeholder="이름">
                    <button type="button" class="btn btn-outline-dark" id="set-add-button">등록</button>
                </div>
            </form>
        </section>
    </section>
</section>

<script>

</script>
</body>
</html>