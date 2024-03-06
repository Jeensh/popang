<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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
                                            <label for="${small.code}" class="lastTree">
                                                <a href="/products?pageNumber=1&categoryCode=${small.code}">
                                                        ${small.name}
                                                </a>
                                            </label>
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