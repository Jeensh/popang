<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:choose>
    <c:when test="${depth == 2}">
        <h5 style="margin-top: 4%">중분류</h5>
    </c:when>
    <c:when test="${depth == 3}">
        <h5 style="margin-top: 4%">소분류</h5>
    </c:when>
</c:choose>
<c:forEach items="${Categories}" var="item">
    <button class="list-group-item list-group-item-action ${item.depth == 2 ? 'medium-category-item' : 'small-category-item'}">
        <span class="category-name">${item.name}</span><br>
        (<span class="category-code">${item.code}</span>)
    </button>
</c:forEach>

<script>
    $(".medium-category-item").click(function (a) {
        let item = $(a.target)
        if($(a.target).prop('tagName') == "SPAN")
            item = $(a.target).parent()

        let parentId = item.find(".category-code").text()
        let parentName = item.find(".category-name").text()

        $('input[name=mediumCode]').val(parentId)
        $('input[name=mediumName]').val(parentName)
        $('input[name=smallCode]').val("")
        $('input[name=smallName]').val("")


        $.ajax({
            url: '/admin/category/' + parentId,
            type: 'post',
            dataType: 'html',
            success: function (response) {
                $('#small-category').html(response);
                // $(".medium-category-item").removeAttr('disabled', false);
            },
            error: function (errors) {
                // $(a.target).removeAttr('disabled', false);
            }
        });
    })

    $(".small-category-item").click(function (a) {
        let parentId = $(a.target).find(".category-code").text()
        let parentName = $(a.target).find(".category-name").text()

        $('input[name=smallCode]').val(parentId)
        $('input[name=smallName]').val(parentName)
    })
</script>
