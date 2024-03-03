<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<nav class="bg-body-tertiary form-control" style="margin-top: 2%; display: flex; justify-content: center">
    <section style="display: flex; justify-content: center; width: 70%">
        <a href="/main" class="me-4" style="width: 20%"><img class="nav-bar-logo" src="/images/main/mainlabel.png"></a>
        <form class="search-form ms-3" role="search">
            <button class="btn btn-light me-2" type="button"><img class="nav-bar-search-icon"
                                                                       src="/images/icon/menu.png"></button>
            <input class="form-control me-2 input-search" type="search" placeholder="상품명" size="150"
                   aria-label="Search">
            <button class="btn btn-outline-success" type="submit"><img class="nav-bar-search-icon"
                                                                       src="/images/icon/search.png"></button>
        </form>
        <div class="nav-item-section ms-4">
            <a class="nav-item" href="/logout"><img class="nav-bar-icon" src="/images/icon/empty_cart.png"></a>
            <a class="nav-item" aria-current="page" href="#"><img class="nav-bar-icon" src="/images/icon/myPage.png"></a>
            <a class="nav-item" href="/logout"><img class="nav-bar-icon" src="/images/icon/logout.png"></a>
        </div>
    </section>
</nav>