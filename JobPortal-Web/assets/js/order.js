"use strict";
var baseUrlJobManagement = "http://localhost:8887/api/v1/job_management";

function SearchOrderRequest(orderBy, statusOrder, pageSize, pageNumber, sortBy, sortType) {
    this.orderBy = orderBy;
    this.statusOrder = statusOrder;
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.sortBy = sortBy;
    this.sortType = sortType;
}

$(function () {
    // $("#pagination").load("/assets/html/pagination.html");
    // console.log(123);
    orderPageAll();
});

function orderPageAll() {
    changActivePage('order-all')
    $(".status-order").empty()
    $(".status-order").append("<div>Trạng thái: Tất cả</div>")
    getListOrder(null)
}

function orderPagePending() {
    changActivePage('order-pending');
    getListOrder('PENDING');
    $(".status-order").load("/assets/html/button-order.html");
}

function orderPageDone() {
    changActivePage('order-done')
    getListOrder('APPROVED');
}

function orderPageCancel() {
    changActivePage('order-cancel')
    getListOrder('CANCEL');
}

function changActivePage(pageActive) {
    const navLinks = Array.from(document.getElementsByClassName("nav-link"));
    navLinks.forEach(element => element.classList.remove('active', 'base-shoppe-bg'))

    var navActive = document.getElementById(pageActive);
    navActive.classList.add('active', 'base-shoppe-bg');
    navActive.classList.remove('text-dark')
}

function getListOrder(jobStatus) {
    let userId = localStorage.getItem("id");
    let request = new SearchOrderRequest(userId, status, 100, 1, "orderDate", "DESC")
    let conditionStatus = jobStatus ? ("&jobStatus=" + jobStatus) : "";
    //   ------------------------------------- API DANH SÁCH ORDER -------------------------------------
    $.ajax({
        url: baseUrlJobManagement + "/get-by-jobstatus?userID=" + userId + conditionStatus,
        type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        error: function (err) {
            console.log(err)
            confirm(err.responseJSON.message)
        },
        success: function (data) {
            console.log(data);
            fillOrderToTable(data, jobStatus);
        }
    });

    // ------Fake data bằng file json------
    // fetch('/assets/data/order.json')
    //     .then((response) => response.json())
    //     .then((json) => {
    //         if (status) {
    //             let data = json.content.filter(function (element) {
    //                 return element.status === status;
    //             });
    //             fillOrderToTable(data, status);
    //         } else {
    //             fillOrderToTable(json.content, status);
    //         }
    //     });
}

function fillOrderToTable(orderList, status) {
    $("#order-item").empty()
    console.log(orderList)
    for (var index = 0; index < orderList.length; index++) {
        let order = orderList[index];
        let statusJob;
        // if (order.orderStatus === 'PENDING') {
        //     statusOrder = "Chờ thanh toán";
        // } else if (order.orderStatus === 'DONE') {
        //     statusOrder = "Đã thanh toán";
        // } else {
        //     statusOrder = "Đã huỷ";
        // }

        if (order.jobStatus === 'PENDING') {
            statusJob = 'Chờ xác nhận';
        }else if (order.jobStatus === 'APPROVED') {
            statusJob = "Đã xác nhận";
        }else if(order.jobStatus === 'CANCEL'){
            statusJob = 'Đã hủy';
        }

        let textStatus = (order.jobStatus === 'PENDING' && status === 'PENDING') ?
            `<div class="row">
                        <div class="col-6">
                            <button type="button" class="btn base-font base-shoppe-bg" style="width: 80%;"
                             onclick="approvedJob(` + order.id + `)">Xác nhận</button>
                        </div>

                        <div class="col-6">
                            <button type="button" class="btn base-font text-white bg-secondary " style=" width: 80%;" 
                            onclick="deleteJob(`+ order.id + `)">Huỷ </button>
                        </div>
                    </div>` : ('Trạng thái: ' + statusJob);

        $("#order-item").append(
            `<div class="row border-bottom mb-3 bg-white">
            <div class="col-3 center-block text-center">
                <img src="`+ order.job.image + `" 
                class="img-fluid img-thumbnail img-order " alt="Sheep">
            </div>
            <div class="col-5 d-flex justify-conten-start align-items-center">
                <div class="">
                    <h3 class="p-2">Công ty: `+ order.job.companyName.toUpperCase() + `</h3>

                    <h4 class="p-2">Vị trí: `+ order.job.jobTitleName + `</h4>
                    <div class="p-2">Địa chỉ: `+ order.job.location + `</div>
                    <div class="p-2">Mức lương: `+ order.job.salary + ` đ</div>
                </div>
            </div>
            <div class="col-4 d-flex justify-conten-start align-items-center">
                <div class="">
                    <div class="p-2">Ngày apply: `+ order.job.createDate + `</div>

                    <div class="p-2 status-order" style="color: red;" >` + textStatus + `</div>
                </div>
            </div>
        </div>`
        )
    }
}



function approvedJob(jobManaId) {
    console.log(jobManaId);
    //   ------------------------------------- API THANH TOÁN ORDER -------------------------------------
    $.ajax({
        url: baseUrlJobManagement + "/approvedJob/" + jobManaId,
        type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        error: function (err) {
            console.log(err)
            confirm(err.responseJSON.message)
        },
        success: function (data) {
            orderPagePending();
            // alert("Đã thanh toán thành công!" + orderId);
        }
    });
    // orderPagePending();
    // alert("Đã thanh toán thành công!" + orderId);

}

function deleteJob(jobManaId) {
    //   ------------------------------------- API HUỶ ĐƠN HÀNG -------------------------------------
    $.ajax({
        url: baseUrlJobManagement + "/delete/" + jobManaId,
        type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        contentType: "application/json",
        error: function (err) {
            console.log(err)
            confirm(err.responseJSON.message)
        },
        success: function (data) {
            console.log(data);
            orderPagePending();
            // alert("Đã huỷ đơn hàng thành công!" + orderId)
        }
    });
    // orderPagePending();

}