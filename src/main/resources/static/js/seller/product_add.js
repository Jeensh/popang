// 초기화
$().ready(() => {
    ClassicEditor
        .create(document.querySelector('#editor'))
        .then(() => {
            let editor = $("#editor").next()
            editor.css("width", "100%")
        })
        .catch(error => {
            console.error(error);
        });

    $(".save-btn").click(function (button) {
        Swal.fire({
            title: '저장 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '승인',
            cancelButtonText: '취소',
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: '/seller/product/add',
                    type: 'post',
                    data: $("#product-add-form").serialize(),
                    dataType: 'json',
                    success: function (response) {
                        console.log(response)
                        let title_data = '상품이 등록되었습니다.'
                        let message = response.message
                        let icon_data = 'success'
                        if (!response.success) {
                            title_data = '상품 등록에 실패하였습니다.'
                            message = response.message
                            icon_data = 'error'
                        }
                        Swal.fire({
                            title: title_data,
                            html: "<span style=\"color: #dc3545\">" + message + "</span>",
                            icon: icon_data,
                        })
                    }
                });
            }
        })
    })

    $(".large-category-item").click(function (a) {
        let item = $(a.target)
        if ($(a.target).prop('tagName') == "SPAN")
            item = $(a.target).parent()

        $(".large-category-item").removeClass("active")
        item.addClass("active")

        let parentId = item.find(".category-code").text()
        let parentName = item.find(".category-name").text()
        $('#category-code').val("")

        $.ajax({
            url: '/category/' + parentId,
            type: 'post',
            dataType: 'html',
            success: function (response) {
                $('#medium-category').html(response);
                $('#small-category').html("");
            },
            error: function (errors) {
                $('#medium-category').html("");
                $('#small-category').html("");
            }
        });
    })
})

// 함수
let imageInputCounter = 0;

function addImageInput(button) {
    let imageInput = "<div class=\"input-group input-group-lg mb-3\">\n" +
        "                    <span class=\"input-group-text\" id=\"images\">이미지 주소</span>\n" +
        "                    <input type=\"text\" class=\"form-control\" aria-label=\"Sizing example input\"\n" +
        "                           aria-describedby=\"inputGroup-sizing-lg\" name=\"images\">\n"
    let div = $(button).parent().prev()

    if (imageInputCounter < 4) {
        div.prepend(imageInput)
        imageInputCounter++
    }
}

function deleteImageInput(button) {
    if (imageInputCounter > 0) {
        let input = $(button).parent().prev().find("div").eq(imageInputCounter - 1);
        input.remove()
        imageInputCounter--;
    }
}