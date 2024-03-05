<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:forEach items="${Categories}" var="item">
    <button type="button" class="list-group-item list-group-item-action ${item.depth == 2 ? 'medium-category-item' : 'small-category-item'}">
        <span class="category-name">${item.name}</span><br>
        (<span class="category-code">${item.code}</span>)
    </button>
</c:forEach>

<script>
    $(".medium-category-item").click(function (a) {
        let item = $(a.target)
        if($(a.target).prop('tagName') == "SPAN")
            item = $(a.target).parent()

        $(".medium-category-item").removeClass("active")
        item.addClass("active")

        let parentId = item.find(".category-code").text()
        let parentName = item.find(".category-name").text()

        $('input[name=mediumCode]').val(parentId)
        $('input[name=mediumName]').val(parentName)
        $('input[name=smallCode]').val("")
        $('input[name=smallName]').val("")
        $('#category-code').val("")


        $.ajax({
            url: '/category/' + parentId,
            type: 'post',
            dataType: 'html',
            success: function (response) {
                $('#small-category').html(response);
            },
            error: function (errors) {
            }
        });
    })

    $(".small-category-item").click(function (a) {
        let item = $(a.target)
        if($(a.target).prop('tagName') == "SPAN")
            item = $(a.target).parent()

        let categoryId = item.find(".category-code").text()
        let categoryName = item.find(".category-name").text()

        $(".small-category-item").removeClass("active")
        item.addClass("active")

        $('input[name=smallCode]').val(categoryId)
        $('input[name=smallName]').val(categoryName)
        $('#category-code').val(categoryId)
    })
</script>
