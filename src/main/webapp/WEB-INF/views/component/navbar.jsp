<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<nav class="bg-body-tertiary form-control" style="margin-top: 2%; display: flex; justify-content: center">
    <section style="display: flex; justify-content: center; width: 80%">
        <a href="/main" class="me-4" style="width: 20%"><img class="nav-bar-logo" src="/images/main/mainlabel.png"></a>
        <form class="search-form ms-3" role="search">
            <button class="btn btn-light me-2" type="button" data-bs-toggle="offcanvas"
                    data-bs-target="#offcanvasWithBothOptions" aria-controls="offcanvasWithBothOptions">
                <img class="nav-bar-search-icon" src="/images/icon/menu.png">
            </button>
            <input class="form-control me-2 input-search" type="search" placeholder="상품명" size="150"
                   aria-label="Search">
            <button class="btn btn-outline-success" type="submit"><img class="nav-bar-search-icon"
                                                                       src="/images/icon/search.png"></button>
        </form>
        <div class="nav-item-section ms-4">
            <a class="nav-item" href="#"><img class="nav-bar-icon" src="/images/icon/empty_cart.png"></a>
            <a class="nav-item" aria-current="page" href="#"><img class="nav-bar-icon"
                                                                  src="/images/icon/myPage.png"></a>
            <a class="nav-item" href="/logout"><img class="nav-bar-icon" src="/images/icon/logout.png"></a>
        </div>
    </section>
</nav>

<%--오프캔버스(카테고리 메뉴)--%>
<div class="offcanvas offcanvas-start" data-bs-scroll="true" tabindex="-1" id="offcanvasWithBothOptions"
     aria-labelledby="offcanvasWithBothOptionsLabel">
    <div class="offcanvas-header border-bottom">
        <h5 class="offcanvas-title" id="offcanvasWithBothOptionsLabel">메뉴</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body bg-white">
        <div class="menu_tree_management border-0">
            <ul class="tree" style="padding: 0px">
                <li>
                    <input type="checkbox" id="title">
                    <label for="title" class="title">카테고리</label>
                    <ul class="hide">
                        <c:forEach items="${largeCategories}" var="large">
                            <li>
                                <input type="checkbox" id="${large.code}">
                                <label for="${large.code}">${large.name}</label>
                                <ul class="hide">
                                    <c:forEach items="${large.categoryList}" var="medium">
                                        <li>
                                            <input type="checkbox" id="${medium.code}">
                                            <label for="${medium.code}">${medium.name}</label>
                                            <ul class="hide">
                                                <c:forEach items="${medium.categoryList}" var="small">
                                                    <li>
                                                        <input type="checkbox" id="${small.code}">
                                                        <label for="${small.code}"
                                                               class="lastTree">${small.name}</label>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<script>
    $().ready(() => {
        $(".tree label").click((event) => {
            let submenu = $(event.target).next("ul")
            if (submenu.is(":visible")) {
                submenu.slideUp()
            } else {
                submenu.slideDown()
            }
        })
    })
</script>