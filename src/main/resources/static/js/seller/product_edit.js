// 함수
let imageInputCounter = 0;
let ckeditor
function addImageInput(button) {
    let imageInput = "<div class=\"input-image input-group input-group-lg mb-3\">\n" +
        "                    <span class=\"input-group-text\" id=\"images\">이미지 주소</span>\n" +
        "                    <input type=\"text\" class=\"form-control\" aria-label=\"Sizing example input\"\n" +
        "                           aria-describedby=\"inputGroup-sizing-lg\" name=\"images\"></div>\n"
    let div = $(button).parent().prev()

    if (imageInputCounter < 4) {
        div.prepend(imageInput)
        imageInputCounter++
    }
}

function deleteImageInput(button) {
    if(imageInputCounter > 0){
        let input = $(button).parent().prev().find("div").eq(0);
        input.remove()
        imageInputCounter--;
    }
}

// 초기화
$().ready(() => {
    imageInputCounter = $(".input-image").length

    ClassicEditor
        .create(document.querySelector('#editor'))
        .then((res) => {
            let editor = $("#editor").next()
            editor.css("width", "100%")
            ckeditor = res
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
                // 폼 데이터를 직렬화하여 JavaScript 객체로 변환
                let formData = $("#product-add-form").serializeArray();

                // 변환된 객체를 수정
                for (var i = 0; i < formData.length; i++) {
                    if (formData[i].name === "descriptionDetail") { // 필드 이름을 확인하여 수정할 필드 선택
                        formData[i].value = ckeditor.getData(); // 필드 값을 변경
                    }
                }

                // 수정된 JavaScript 객체를 다시 직렬화하여 사용할 수 있음
                let serializedData = $.param(formData);

                $.ajax({
                    url: '/seller/product/edit',
                    type: 'post',
                    data: serializedData,
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
                        }).then((result) => {
                            if(response.success){
                                location.href = "/seller/product/" + $("#product-id").val()
                            }
                        })
                    }
                });
            }
        })
    })

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

                $.ajax({
                    url: '/seller/product/delete',
                    type: 'post',
                    data: {id: $("#product-id").val()},
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
                        }).then((result) => {
                            if(response.success){
                                location.href = "/seller/product/management?pageNumber=1"
                            }
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
            error: function (errors){
                $('#medium-category').html("");
                $('#small-category').html("");
            }
        });
    })
})