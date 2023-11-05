"use strict";

let jobTitleNameRequest = "";
let companyName = "";
let locationRequest = "";
let typeJob = [];
let application_way = [];
let status = [];
let minSalary = 1;
let maxSalary = 99999999;
let isManagerJob = true;
let token = "";
let page_size = 4;
let page = 1;
let sortField = "id";
let sortType = "DESC";
let job = [];
let jobManagement = [];

let baseUrljob = "http://localhost:8887/api/v1/job";
let baseUrljobApply = "http://localhost:8887/api/v1/job_management";
let baseViewList = "http://localhost:8887/api/v1/job/get-All";

$(function () {
  buildManager();
  // getListProduct();
  // getListJob();
  viewListJob();
});

function validate() {
  if (
    localStorage.getItem("id") === null ||
    localStorage.getItem("role") === null ||
    localStorage.getItem("fullName") === null ||
    localStorage.getItem("username") === null ||
    localStorage.getItem("token") === null
  ) {
    alert("Bạn cần phải đăng nhập trước khi sử dụng");
    window.location.href = "login.html";
  }
}

function buildManager() {
  if ("EMPLOYER" === localStorage.getItem("role")) {
    $(".header__cart")
      .empty()
      .append(
        `
      <div class="header__cart-wrap" style="cursor: pointer;" onclick="navToOrder()">
        <i class='fas fa-calendar-check' style='font-size:35px;color:white'></i>
          <span class="header__cart-notice">3</span>
        <!-- No cart : header__cart-list--no-cart -->
      </div>
      `
      );
    isManagerJob = false;
  } else if (localStorage.getItem("role") === null) {
    $("#button-add").empty();
  } else {
    isManagerJob = true;
    // $("#button-add").empty();
    $("#button-add").empty()
      .append(`<button type="button" class="btn base-font base-shoppe-bg" style=" width: 80%;" 
                              onclick="addProduct()">Post Job</button>`);
  }
}

function searchListJob(jobTitleNameRequest,status,minSalary,maxSalary,application_way,companyName,locationRequest,page,page_size,sortField,sortType
) {
  this.jobTitleNameRequest = jobTitleNameRequest;
  this.status = status;
  this.minSalary = minSalary;
  this.maxSalary = maxSalary;
  this.application_way = application_way;
  this.companyName = companyName;
  this.locationRequest = locationRequest;
  this.page = page;
  this.page_size = page_size;
  this.sortField = sortField;
  this.sortType = sortType;
}

function SearchProductRequest(
  jobTitleName,
  companyName,
  typeJob,
  application_way,
  status,
  minSalary,
  maxSalary,
  pageSize,
  pageNumber,
  sortField,
  sortType
) {
  this.jobTitleName = jobTitleName;
  this.companyName = companyName;
  this.typeJob = typeJob;
  this.application_way = application_way;
  this.status = status;
  this.minSalary = minSalary;
  this.maxSalary = maxSalary;
  this.page_size = pageSize;
  this.page = pageNumber;
  this.sortField = sortField;
  this.sortType = sortType;
}

function CreatProductRequest(jobTitleName,salary,image,status,jobDescription,companyName,location,jobRequirements,application_way,career
) {
  this.jobTitleName = jobTitleName;
  this.salary = salary;
  this.image = image;
  this.status = status;
  this.jobDescription = jobDescription;
  this.companyName = companyName;
  this.location = location;
  this.jobRequirements = jobRequirements;
  this.application_way = application_way;
  this.career = career;
}

function UpdateJob(
  id,
  jobTitleName,
  salary,
  image,
  status,
  jobDescription,
  companyName,
  location,
  jobRequirements,
  application_way,
  career
) {
  this.id = id;
  this.jobTitleName = jobTitleName;
  this.salary = salary;
  this.image = image;
  this.status = status;
  this.jobDescription = jobDescription;
  this.companyName = companyName;
  this.location = location;
  this.jobRequirements = jobRequirements;
  this.application_way = application_way;
  this.career = career;
}

function fillterApply() {
  console.log(123);
  getTypeProduct();
  getStyle();
  getStatusJob();
  
  // console.log(typeProduct);
  

}

async function viewListJob() {
  $.ajax({
    url: baseViewList,
    type: "GET",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    // data : JSON.stringify(request),
    error: function (err) {
      console.log(err.message);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      console.log(data.content);
      // console.log(data.number);
      // console.log(data.totalPages);
      // job.push(data.content);
      // console.log(job);

      fillProductsToTable(data.content);
      if (data) {
        buildPagination(data.number + 1, data.totalPages);
      } else {
        $("#content-product").append(`
          <div style="justify-content: center; align-items: center; font-size: 1.6rem;">Nothing to show</div>`);
      }
    },
  });
}

function searchInput() {
  let keyword = $(".header__search-input").val().toLowerCase();

  $.ajax({
    url: "http://localhost:8887/api/v1/job/view",
    type: "GET",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    // data : JSON.stringify(request),
    error: function (err) {
      console.log(err.message);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      console.log(data);

      let test = data.filter((value) => {
        return value.jobTitleName.toLowerCase().includes(keyword);
      });

      console.log(test);
      

      fillProductsToTable(test);
      clear();
    },
  });
}
function clear() {
  document.getElementById("inputSearch").value = "";
}

async function getListJob() {
  let request = new searchListJob(
    jobTitleNameRequest,
    status,
    minSalary,
    maxSalary,
    application_way,
    companyName,
    locationRequest,
    page,
    page_size,
    sortField,
    sortType
  );

  console.log(request);
  $.ajax({
    url: baseUrljob + "/search",
    type: "POST",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    data: JSON.stringify(request),
    error: function (err) {
      console.log(err.message);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      // console.log(data.totalPages);
      // console.log(data);
      // console.log(data.content);
      // job.push(data.content);
      fillProductsToTable(data.content);
      if (data) {
        buildPagination(data.number + 1, data.totalPages);
      } else {
        $("#content-product").append(`
          <div style="justify-content: center; align-items: center; font-size: 1.6rem;">Nothing to show</div>`);
      }
    },
  });
}

// async function getListProduct() {
//   let request = new SearchProductRequest(
//     jobTitleName,
//     companyName,
//     typeJob,
//     application_way,
//     status,
//     minSalary,
//     maxSalary,
//     pageSize,
//     pageNumber,
//     sortField,
//     sortType
//   );
//   //   ------------------------------------- API TÌM KIẾM PRODUCT -------------------------------------
//   $.ajax({
//     url: baseUrljob + "/search",
//     type: "POST",
//     beforeSend: function (xhr) {
//       xhr.setRequestHeader(
//         "Authorization",
//         "Bearer " + localStorage.getItem("token")
//       );
//     },
//     contentType: "application/json",
//     data: JSON.stringify(request),
//     error: function (err) {
//       console.log(err);
//       confirm(err.responseJSON.message);
//     },
//     success: function (data) {
//       fillProductsToTable(data.content);
//       console.log(data.content);
//       console.log(data.number);
//       buildPagination(data.number + 1, data.totalPages);
//     },
//   });

//   // ------Fake data bằng file json------
//     // fetch('/assets/data/product.json')
//     //   .then((response) => response.json())
//     //   .then((json) => {
//     //     console.log(json);
//     //     fillProductsToTable(json.content);
//     //     buildPagination(json.number + 1, json.totalPages);
//     //   });
// }

function buildPagination(number, totalPages) {
  // kiểm tra nếu trang hiện tại là trang đầu -> disable đi
  if (number === 1) {
    $("#pagination").empty().append(`<li class="pagination-item">
                              <a class="pagination-item__link">
                                <i class="pagination-item__icon fas fa-angle-left"></i>
                              </a></li>`);
  } else {
    $("#pagination").empty().append(`<li class="pagination-item">
                              <a href="#" class="pagination-item__link " onclick="prePage()">
                                <i class="pagination-item__icon fas fa-angle-left"></i>
                              </a></li>`);
  }

  // Dùng hàm for để build ra số trang. Kiểm tra xem trang hiện tại là bao nhiêu thì background vàng
  for (let index = 1; index <= totalPages; index++) {
    if (number === index) {
      $("#pagination").append(
        `<li class="pagination-item pagination-item--active">
                                <a href="" class="pagination-item__link" onclick="chosePage(` +
          index +
          `)">
                                ` +
          index +
          `</a>
        </li>`
      );
    } else {
      $("#pagination").append(
        `<li class="pagination-item">
                                <a href="" class="pagination-item__link" onclick="chosePage(` +
          index +
          `)">
                                ` +
          index +
          `</a>
                              </li>`
      );
    }
  }

  // Kiểm tra nếu trang hiện tại là trang cuối -> disable đi
  if (number === totalPages) {
    $("#pagination").append(`<li class="pagination-item">
                              <a class="pagination-item__link "">
                                <i class="pagination-item__icon fas fa-angle-right"></i>
                              </a></li>`);
  } else {
    $("#pagination").append(`<li class="pagination-item">
                              <a href="#" class="pagination-item__link " onclick="nextPage()">
                                <i class="pagination-item__icon fas fa-angle-right"></i>
                              </a></li>`);
  }
}

function chosePage(pageNumber) {
  event.preventDefault();
  page = pageNumber;
  baseViewList = baseViewList.substr(0, 40);
  console.log(baseViewList);
  // baseViewList = baseUrljob.substring();
  console.log(baseViewList);
  baseViewList = baseViewList + "?page=" + pageNumber;

  // getListJob();
  viewListJob();
  // baseViewList = baseViewList + "";
  // console.log(baseViewList);
}
function prePage() {
  event.preventDefault();

  page--;
  // getListJob();
  viewListJob();
}

function nextPage() {
  event.preventDefault();
  page++;
  // getListJob();
  viewListJob();
}

function fillProductsToTable(jobList) {
  // LÀM TRỐNG DỮ LIỆU
  $("#content-product").empty();

  // console.log(jobList);
  // $("#content-product").append(`
  //         <h1 class="heading" >All jobs</h1>
  // `);

  for (var index = 0; index < jobList.length; index++) {
    let job = jobList[index];
    // console.log(job);
    // console.log(jobList[index]);
    // console.log(job.id);
    // let status = job.status === "NEW" ? "Mới" : "Đã sử dụng";
    // let status = job.status === "FULL_TIME" ? "FULL_TIME" : "PART_TIME";
    // let status =  job.status;
    // console.log(status);

    $("#content-product").append(
      `
    

    <section class="job-container">
    <div class="box-container">
        <div class="box">
            <div class="company">
                <img src="` +
        job.image +
        `" alt="">
                <div>
                    <h3>Lĩnh vực : ` +
        job.career +
        `</h3>
                    <h4>` +
        job.jobTitleName +
        `</h4><br></br>
                    <span>2 days ago</span>
                </div>
            </div>

            <div class = "info">
              <h3 class="job-title"><i class="fas fa-user-alt"></i><span> ` +
        job.companyName +
        ` </span></h3>
              <p class="location"><i class="fas fa-map-marker-alt"></i><span>` +
        job.location +
        `</span></p>
            </div>

            <div class="tags">
                <p><i class="fas fa-hand-holding-usd"></i><span>` +
        job.salary +
        ` </span></p>
                <p><i class="fas fa-briefcase"></i><span> ` +
        job.status +
        ` </span></p>
            </div>

            ${
              isManagerJob &&
              localStorage.getItem("role") !== null &&
              localStorage.getItem("id") !== null &&
              localStorage.getItem("username") !== null
                ? `<div class="flex-btn">
                  <a href="#" class="btn" onclick="viewDetail( ${job.id})"> View details </a>
                  <a href="#" class="btn" onclick = "editJob('${job.id}','${job.jobTitleName}' ,'${job.image}' , '${job.companyName}', '${job.salary}',
                  '${job.location}', '${job.status}', '${job.application_way}', '${job.jobDescription}', 
                  '${job.jobRequirements}', '${job.career}'  )"> Edit </a>
                  <a href="#" class="btn" onclick = "deleteJob(${job.id})"> Delete </a>                                          
              </div>
              </div>
                  </div>`
                : `<div class="flex-btn">
                    <a href="#" class="btn" onclick="viewDetail( '${job.id}','${job.jobTitleName}' ,'${job.image}' , '${job.companyName}', '${job.salary}',
                    '${job.location}', '${job.status}', '${job.application_way}', '${job.jobDescription}', 
                    '${job.jobRequirements}' )"> View details </a>


                    <a href="#" class="btn" onclick="addToBasket( ${job.id})"> Apply </a> 
                  

              </div>
              </div>
            </div>`
            }

            
</section>
         
    `
    );
  }
}
function applyJob(id) {
  console.log(id);
}

function getTypeProduct() {
  let typeProduct = [];
  var checkedAllLocation = document.getElementById("AllLocation").checked;

  if (checkedAllLocation) {
    typeProduct.push("Tất cả địa điểm");
  }
  var checkedHaNoi = document.getElementById("HaNoi").checked;
  if (checkedHaNoi) {
    typeProduct.push("Hà Nội");
  }
  var checkedHCM = document.getElementById("HCM").checked;
  if (checkedHCM) {
    typeProduct.push("Hồ Chí Minh");
  }
  var checkedDN = document.getElementById("DN").checked;
  if (checkedDN) {
    typeProduct.push("Đà Nẵng");
  }

  
}

function getStyle() {
  let style = [];
  var checkedfulltime = document.getElementById("full-time").checked;
  if (checkedfulltime) {
    style.push("full-time");
  }
  var checkedparttime = document.getElementById("part-time").checked;
  if (checkedparttime) {
    style.push("part-time");
  }
  var checkedremote = document.getElementById("remote").checked;
  if (checkedremote) {
    style.push("remote");
  }
}

function getStatusJob() {
  let statusJob = [];
  var statusonline = document.getElementById("online").checked;
  if (statusonline) {
    statusJob.push("online");
  }
  var statusoffline = document.getElementById("offline").checked;
  if (statusoffline) {
    statusJob.push("offline");
  }
}

// function getFillterPrice() {
//   minPrice = document.getElementById("minPrice").value;
//   maxPrice = document.getElementById("maxPrice").value;
// }

// function clearFillter() {
//   document.getElementById("phone").checked = false;
//   document.getElementById("computer").checked = false;
//   document.getElementById("clothes").checked = false;
//   document.getElementById("footwear").checked = false;

//   document.getElementById("express").checked = false;
//   document.getElementById("fast").checked = false;
//   document.getElementById("save").checked = false;

//   document.getElementById("statusNew").checked = false;
//   document.getElementById("statusOld").checked = false;

//   document.getElementById("minPrice").value = "";
//   document.getElementById("maxPrice").value = "";
// }

function filterClear() {
  document.getElementById("AllLocation").checked = false;
  document.getElementById("HaNoi").checked = false;
  document.getElementById("HCM").checked = false;
  document.getElementById("DN").checked = false;
  document.getElementById("full-time").checked = false;
  document.getElementById("part-time").checked = false;
  document.getElementById("remote").checked = false;
  document.getElementById("online").checked = false;
  document.getElementById("offline").checked = false;
  document.getElementById("minPrice").value = "";
  document.getElementById("maxPrice").value = "";
}

function CreatOrderRequest(user_id, job_id) {
  this.user_id = user_id;
  this.job_id = job_id;
}

function addToBasket(job_id) {
  // console.log(input, document.getElementById(input).value);
  // let quantity = document.getElementById(input).value;
  // if (quantity > 0) {
  //   let text = "Bạn có chắc mua sản phẩm?.";
  //   if (confirm(text) == true) {
  //     console.log("ĐÃ thêm", quantity, productId);

  // Khởi tạo các gía trị cho request
  // let accountId = localStorage.getItem("id");

  //    CHECK XEM USER ĐÃ ĐĂNG NHẬP HAY CHƯA, NẾU CHƯA THÌ KHÔNG CHO SỬ DỤNG
  validate();

  let user_id = localStorage.getItem("id");
  let request = new CreatOrderRequest(user_id, job_id);
  console.log(request);

  //   ------------------------------------- API CHO SẢN PHẨM VÀO GIỎ -------------------------------------
  $.ajax({
    url: baseUrljobApply + "/create",
    type: "POST",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    data: JSON.stringify(request),
    error: function (err) {
      console.log(err);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      showAlrtSuccess();
      jobManagement.push(data);
      console.log(jobManagement);
      getListJob();
    },
  });
  // showAlrtSuccess();
}
// } else {
// alert("Số lượng sản phẩm phải lớn hơn 1");
// }
// }

function navManagerProduct() {
  isManagerJob = true;
  buildProduct();
}

function editJob(
  jobId,
  jobTitleName,
  image,
  companyName,
  salary,
  location,
  status,
  application_way,
  jobDescription,
  jobRequirements,
  career
) {
  console.log(
    jobId,
    jobTitleName,
    image,
    companyName,
    salary,
    location,
    status,
    application_way,
    jobDescription,
    jobRequirements
  );

  document.getElementById("jobId").value = jobId;
  document.getElementById("jobName").value = jobTitleName;
  document.getElementById("jobImage").value = image;
  document.getElementById("jobCompanyName").value = companyName;
  document.getElementById("jobSalary").value = salary;
  document.getElementById("jobLocation").value = location;
  document.getElementById("jobStatus").value = status;
  document.getElementById("jobApply").value = application_way;
  document.getElementById("jobDescription").value = jobDescription;
  document.getElementById("jobRequirements").value = jobRequirements;
  document.getElementById("jobCareer").value = career;

  // document.getElementById("jobId").value = jobId;
  // jobName = document.getElementById("jobName").value;
  // jobImage = document.getElementById("jobImage").value;
  // jobCompanyName = document.getElementById("jobCompanyName").value;
  // jobSalary = document.getElementById("jobSalary").value;
  // jobLocation = document.getElementById("jobLocation").value;
  // jobStatus = document.getElementById("jobStatus").value;
  // jobApply = document.getElementById("jobApply").value;
  // jobGender = document.getElementById("jobGender").value;
  // jobDescription = document.getElementById("jobDescription").value;
  // jobRequirements = document.getElementById("jobRequirements").value;
  // console.log(jobId);

  $("#modalProduct").modal("show");
}

function viewDetail(
  jobid,
  jobTitleName,
  jobimage,
  jobcompanyName,
  jobsalary,
  joblocation,
  jobstatus,
  jobapplication_way,
  jobDescription,
  jobRequirements
) {
  window.location.href = "viewDetail.html";

  // let jobname = jobTitleName;
  // let image = jobimage;
  // let companyName = jobcompanyName;
  // let salary = jobsalary;
  // let location = joblocation;
  // let status = jobstatus;
  // let application_way = jobapplication_way;
  // let description = jobDescription;
  // let requirements = jobRequirements;
  // document.getElementById("aaaa").innerHTML = jobid;
  // console.log(document.querySelector("#aaaa"));
  // console.log(document.querySelector(".ViewDetails-jobTitle"));

  // console.log(12);
  // console.log(document.getElementsByClassName('ViewDetails-image')) ;
  // console.log(document.getElementById("header_v2"));

  // document.getElementsByClassName('ViewDetails-jobTitle').innerHTML = jobname;
  // document.getElementsByClassName('ViewDetails-jobCompanyName').innerHTML = companyName;
  // document.getElementsByClassName('ViewDetails-Location').innerHTML = location;
  // document.getElementsByClassName('ViewDetails-salary').innerHTML = salary;
  // document.getElementsByClassName('ViewDetails-status').innerHTML = status;
  // document.getElementsByClassName('ViewDetails-Requirements').innerHTML = requirements;
  // document.getElementsByClassName('ViewDetails-description').innerHTML = description;
  // document.getElementsByClassName('ViewDetails-applyway').innerHTML = application_way;
}

// function editProduct(productId,imageProduct,productName,price,shippingUnit,status,type) {
//   console.log(productId,imageProduct,productName,price,shippingUnit,status,type);
//   document.getElementById("productId").value = productId;
//   document.getElementById("productName").value = productName;
//   document.getElementById("imageProduct").value = imageProduct;
//   document.getElementById("priceProduct").value = price;
//   document.getElementById("statusProduct").value = status;
//   document.getElementById("shippingUnitProduct").value = shippingUnit;
//   document.getElementById("typeProduct").value = type;
//   $("#modalProduct").modal("show");
// }

function addProduct() {
  resetFormProduct();
  $("#modalProduct").modal("show");
}

function confirmDeleteProduct(productId) {
  $("#modalConfirmDelete").modal("show");
  document.getElementById("productIdDelete").value = productId;
}

function deleteProduct() {
  let productId = document.getElementById("productIdDelete").value;
  //   ------------------------------------- API XOÁ SẢN PHẨM -------------------------------------
  $.ajax({
    url: baseUrlProduct + "/delete/" + productId,
    type: "POST",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    data: JSON.stringify(request),
    error: function (err) {
      console.log(err);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      showAlrtSuccess();
      getListProduct();
      $("#modalConfirmDelete").modal("hide");
    },
  });

  showAlrtSuccess();
  $("#modalConfirmDelete").modal("hide");
}

function deleteJob(jobId) {
  console.log(jobId);

  $.ajax({
    url: baseUrljob + "/delete/" + jobId,
    type: "DELETE",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",

    error: function (err) {
      console.log(err);
      confirm(err.responseJSON.message);
    },
    success: function () {
      showAlrtSuccess();
      console.log("thanh cong");
      getListJob();
    },
  });
}

function saveProduct() {
  // Lấy các thông tin cần thiết trên form
  const jobId = document.getElementById("jobId").value;
  const jobTitleName = document.getElementById("jobName").value;
  const jobImage = document.getElementById("jobImage").value;
  const jobCompanyName = document.getElementById("jobCompanyName").value;
  const jobSalary = document.getElementById("jobSalary").value;
  const jobLocation = document.getElementById("jobLocation").value;
  const jobStatus = document.getElementById("jobStatus").value;
  const jobApply = document.getElementById("jobApply").value;
  // const jobGender = document.getElementById("jobGender").value;
  const jobDescription = document.getElementById("jobDescription").value;
  const jobRequirements = document.getElementById("jobRequirements").value;
  const jobCareer = document.getElementById("jobCareer").value;

  // Tạo 1 request cho Thêm mới Job
  let request = new CreatProductRequest(
    jobTitleName,
    jobSalary,
    jobImage,
    jobStatus,
    jobDescription,
    jobCompanyName,
    jobLocation,
    jobRequirements,
    jobApply,
    jobCareer
  );
  console.log(request);

  let updateRequest = new UpdateJob(
    jobId,
    jobTitleName,
    jobSalary,
    jobImage,
    jobStatus,
    jobDescription,
    jobCompanyName,
    jobLocation,
    jobRequirements,
    jobApply,
    jobCareer
  );

  console.log(updateRequest);
  // NẾU JOB ID MÀ RỖNG THÌ SẼ LÀ THÊM MỚI CÒN KHÔNG THÌ SẼ LÀ UPDATE
  let url = jobId === "" ? baseUrljob + "/create" : baseUrljob + "/update";

  let type = jobId === "" ? "POST" : "PUT";

  //   ------------------------------------- API UPDATE, THÊM MỚI SẢN PHẨM -------------------------------------
  $.ajax({
    url: url,
    type: type,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    data: JSON.stringify(jobId === "" ? request : updateRequest),
    error: function (err) {
      console.log(err);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      console.log(data);
      $("#modalProduct").modal("hide");
      showAlrtSuccess();
      getListJob();
    },
  });
}

function resetFormProduct() {
  document.getElementById("jobId").value = "";
  document.getElementById("jobName").value = "";
  document.getElementById("jobImage").value = "";
  document.getElementById("jobCompanyName").value = "";
  document.getElementById("jobSalary").value = "";
  document.getElementById("jobLocation").value = "";
  document.getElementById("jobStatus").value = "FULL_TIME";
  document.getElementById("jobApply").value = "ONLINE";
  // document.getElementById("jobGender").value = "MALE";
  document.getElementById("jobDescription").value = "";
  document.getElementById("jobRequirements").value = "";
  document.getElementById("jobCareer").value = "";
}

function sortCreateDate(type) {
  sortField = "create_date";
  sortType = type;
  getListJob();
  console.log(type);
}

function sortPrice(type) {
  sortField = "salary";
  sortType = type;
  getListJob();
  console.log(type);
}
