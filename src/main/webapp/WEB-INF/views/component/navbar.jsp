<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<nav class="bg-body-tertiary form-control" style="margin-top: 2%; display: flex; justify-content: center">
    <section style="display: flex; justify-content: center; width: 80%">
        <a href="/main" class="me-4" style="width: 20%"><img class="nav-bar-logo" src="/images/main/mainlabel.png"></a>
        <form class="search-form ms-3" role="search" action="/products?pageNumber=1">
            <button class="btn btn-light me-2" id="category-menu-btn" type="button" data-bs-toggle="offcanvas"
                    data-bs-target="#offcanvasWithBothOptions" aria-controls="offcanvasWithBothOptions">
                <img class="nav-bar-search-icon" src="/images/icon/menu.png">
            </button>
            <input class="form-control me-2 input-search" type="search" placeholder="상품명" size="150"
                   aria-label="Search" name="keyword" value="${keyword}">
            <input type="hidden" name="categoryCode" value="${categoryCode}">
            <button class="btn btn-outline-success" type="submit">
                <img class="nav-bar-search-icon" src="/images/icon/search.png">
            </button>
        </form>
        <div class="nav-item-section ms-4">
            <a class="nav-item" href="/order/cart" id="cart" style="display: none"><img class="nav-bar-icon" src="/images/icon/empty_cart.png"></a>
            <a class="nav-item" aria-current="page" id="myPage" style="display: none" href="/order/orders"><img class="nav-bar-icon" src="/images/icon/myPage.png"></a>
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
        <div class="menu_tree_management border-0" id="category-menu">
        </div>
    </div>
</div>

<script>
    $.ajax({
        url: '/user/data',
        type: 'post',
        dataType: 'json',
        success: function(login){
            if(login.role == 1){
                $("#cart").css("display", "block")
                $("#myPage").css("display", "block")
            }
            else{
                $("#cart").remove()
                $("#myPage").remove()
            }
        },
        error: function (response){
            console.log(response)
        }
    });

    $().ready(() => {
        $("#category-menu-btn").one("click", () => {
            $.ajax({
                url: '/category/menu',
                type: 'post',
                dataType: 'html',
                success: function(response){
                    $("#category-menu").html(response)
                },
                error: function (response){
                    console.log(response)
                }
            });
        })
    })
</script>