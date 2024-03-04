// 초기화
$().ready(() => {
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
                let userItem = $(button.target).parent().parent()

                $.ajax({
                    url: '/rest/users/update',
                    type: 'post',
                    data: {
                        id: userItem.find(".user-id").text(),
                        grade: userItem.find(".user-grade").val()
                    },
                    dataType: 'json',
                    success: function (response) {
                        let title_data = '등급 변경이 완료되었습니다'
                        let message = response.message
                        let icon_data = 'success'
                        if (!response.success) {
                            title_data = '등급 변경에 실패하였습니다.'
                            message = response.message
                            icon_data = 'error'
                        }
                        Swal.fire({
                            title: title_data,
                            text: message,
                            icon: icon_data,
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
                let userItem = $(button.target).parent().parent()

                $.ajax({
                    url: '/rest/users/delete',
                    type: 'post',
                    data: {id: userItem.find(".user-id").text()},
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
                        }).then(() => {
                            location.reload()
                        })
                    }
                });
            }
        })
    })

    $(".reset-btn").click(function (button) {
        Swal.fire({
            title: '정말 비밀번호를 초기화 하시겠습니까?',
            text: "다시 되돌릴 수 없습니다. 신중하세요.",
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
                    url: '/rest/users/reset',
                    type: 'post',
                    data: {id: userItem.find(".user-id").text()},
                    dataType: 'json',
                    success: function (response) {
                        let title_data = '비밀번호 초기화가 완료되었습니다'
                        let message = response.message
                        let icon_data = 'success'
                        if (!response.success) {
                            title_data = '비밀번호 초기화에 실패하였습니다.'
                            message = response.message
                            icon_data = 'error'
                        }
                        Swal.fire({
                            title: title_data,
                            text: message,
                            icon: icon_data,
                        })
                    }
                });
            }
        })
    })
})