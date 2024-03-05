// 초기화
$().ready(() => {
    $(".large-category-item").click(function (a) {
        let item = $(a.target)
        if($(a.target).prop('tagName') == "SPAN")
            item = $(a.target).parent()

        let parentId = item.find(".category-code").text()
        let parentName = item.find(".category-name").text()

        $(".large-category-item").removeClass("active")
        item.addClass("active")

        $('input[name=largeCode]').val(parentId)
        $('input[name=largeName]').val(parentName)
        $('input[name=mediumCode]').val("")
        $('input[name=mediumName]').val("")
        $('input[name=smallCode]').val("")
        $('input[name=smallName]').val("")

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

    $("#set-add-button").click(function (button) {
        Swal.fire({
            title: '등록 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '승인',
            cancelButtonText: '취소',
        }).then((result) => {
            if (result.isConfirmed) {
                let userItem = $(button.target).parent().parent()

                $.ajax({
                    url: '/rest/categories/add',
                    type: 'post',
                    data: $("#add-category-form1").serialize(),
                    dataType: 'json',
                    success: function (response) {
                        let title_data = '카테고리 등록이 완료되었습니다'
                        let message = response.message
                        let icon_data = 'success'
                        if (!response.success) {
                            title_data = '카테고리 등록에 실패하였습니다.'
                            message = response.message
                            icon_data = 'error'
                        }
                        Swal.fire({
                            title: title_data,
                            text: message,
                            icon: icon_data,
                        }).then(() => {
                            location.reload()
                        })
                    }
                });
            }
        })
    })
})