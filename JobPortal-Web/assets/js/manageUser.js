let baseUrlUser = "http://localhost:8887/api/v1/user/";

let pageCurrent = 2;
let baseViewListUser = "http://localhost:8887/api/v1/user/view";

$(function () {
  getListUser();

});

function ManageUserFillToTable(userList) {
  // LÀM TRỐNG BẢNG DỮ LIỆU
  $(".userInfo").empty();

  // LẶP QUA TỪNG DATA TRẢ VỀ ĐỂ IN RA BẢNG
  for (var index = 0; index < userList.length; index++) {
    let valueUser = userList[index];
    // console.log(valueUser);

    // ĐẨY DỮ LIỆU VỪA LẶP ĐƯỢC VÀO BẢNG

    valueUser == 0
      ? $(".userInfo").append(`<h1>No data</h1`)
      : $(".userInfo").append(
        `
           
                <tr class = "user-padding">
                <td>` +
        valueUser.id +
        `    </td>
                <td><img src="` +
        valueUser.image +
        `" alt="" width="50" height="50"></td>
                <td>` +
        valueUser.username +
        `</td>
                <td>` +
        valueUser.password +
        `</td>  
                <td>` +
        valueUser.role +
        `</td>    
                <td>` +
        valueUser.working +
        `</td>
                <td>
                
                <div  style="display: flex; padding: 15px 5px; " >
                
                  <svg class = "more" style="padding-right: 10px;width="25" height="25"" onclick="deleteUser(${valueUser.id})" xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                    <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
                  </svg>
                 
                  <svg onclick="viewJobApllied(${valueUser.id})" class = "more"  xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-person-vcard" viewBox="0 0 16 16">
                    <path d="M5 8a2 2 0 1 0 0-4 2 2 0 0 0 0 4Zm4-2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5ZM9 8a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4A.5.5 0 0 1 9 8Zm1 2.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5Z"/>
                    <path d="M2 2a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H2ZM1 4a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H8.96c.026-.163.04-.33.04-.5C9 10.567 7.21 9 5 9c-2.086 0-3.8 1.398-3.984 3.181A1.006 1.006 0 0 1 1 12V4Z"/>
                    
                  </svg>
                </div>
                    
                </td>
                </tr>
            `
      );
  }
}
let isSortType = false; 
let sortField = "id";

function sortByfieldAndType(filed) {

  if(filed == sortField) {
    console.log(filed);
    // baseUrlGetlistUser = baseUrlGetlistUser.substr(0, 41);
    isSortType = !isSortType;
  }else {
    sortField = filed;
    // baseUrlGetlistUser = baseUrlGetlistUser.substr(0, 41);
    isSortType = true;
    console.log(filed);
  }
  getListUser();
  
}

function viewJobApllied(userId) {
  console.log(userId);
    $.ajax({
      url: baseUrlUser + "get_by_id/" + userId,
      type: 'GET',
      beforeSend: function (xhr) {
          xhr.setRequestHeader(
            "Authorization",
            "Bearer " + localStorage.getItem("token")
          );
        },
  contentType: "application/json",
  error: function (err) {
      console.log(err.message);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      console.log(data.appliedJob);

      fillJobToTable(data.appliedJob);

      $("#modalListJobApplied").modal("show");

    },
  })
}



function fillJobToTable(jobList) {
  $(".jobApplied").empty();

  for (var index = 0; index < jobList.length; index++) {
    let jobValue = jobList[index];
    console.log(jobValue);
  $(".jobApplied").append(

    `
        <tbody >
          <tr >
            <td>${jobValue.id}</td>
            <td><img style="width: 100px; height: 100px; object-fit: contain;"  src="${jobValue.image}" /></td>
            <td>${jobValue.companyName}</td>
            <td>${jobValue.jobTitleName}</td>

          </tr>
        </tbody>
    
    `
    
  );
  }
}

function deleteUser(userId) {

  let result = confirm("Are you sure to delete");
  if (result ) {
    $.ajax({
      url: baseUrlUser + "delete/" + userId,
      type: "DELETE",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(
          "Authorization",
          "Bearer " + localStorage.getItem("token")
        );
      },
      contentType: "application/json",
      error: function (err) {
        console.log(err.message);
        confirm(err.responseJSON.message);
      },
      success: function (data) {
        alert("Delete Success");
        getListUser();
        ManageUserFillToTable(data);
      },
    });
  }else {
    return false;
  }

  
}

function buildPagingUser(currentPage, totalPage) {
  if (currentPage === 1 && totalPage === 1) {
    $(".user-pagination").empty().append(`
      <button>Next</button>
    `);
  } else {
    $(".user-pagination").empty().append(`
      
      <span class = "indexPage"></span>
      <button onclick = "nextPaging()">Next</button>
    `);
  }

  for (var index = 1; index <= totalPage; index++) {
    if (index === currentPage) {
      $(".indexPage").append(
        `
            <button style="background-color: violet; cursor: no-drop;">${index}</button>
          `
      );
    } else {
      $(".indexPage").append(
        `
            <button onclick="changePage(${index})">${index}</button>
          `
      );
    }
  }

  if (currentPage === totalPage) {
    $(".user-pagination").empty().append(`
      <button onclick = "prevPaging()">Pre</button>
    `);
  }
}

function changePage(page) {
  if (page == currentPage) {
    return;

  }
  currentPage = page;
  getListUser();
}

function prevPaging() {
  changePage(currentPage -1);
  
}

function nextPaging() {
  changePage (currentPage + 1);

  
}

function clearInput() {
  document.getElementById("search").value = "";
}

function searchUser() {
  let key = $("#search").val().toLowerCase().trim();
  // console.log(key);

  $.ajax({
    url: baseViewListUser,
    type: "GET",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    error: function (err) {
      console.log(err.message);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      let value = data.filter((val) => {
        return (
          val.full_name.toLowerCase().includes(key) ||
          val.email.toLowerCase().includes(key) ||
          val.information.toLowerCase().includes(key) ||
          val.username.toLowerCase().includes(key) ||
          val.role.toLowerCase().includes(key)
        );
      });

      console.log(value.length);
      clearInput();
      ManageUserFillToTable(value);
      buildPagingUser(data.number + 1, data.totalPages);
    },
  });
}

let currentPage = 1;
let size = 4;

function getListUser() {

  let baseUrlGetlistUser = "http://localhost:8887/api/v1/user/get-All";
  
  // baseUrlGetlistUser = baseUrlGetlistUser.substr(0,41);

  baseUrlGetlistUser += "?page=" + currentPage  + "&size=" + size;
  
  baseUrlGetlistUser += "&sort=" + sortField + "," + (isSortType ? "asc" : "desc");
  
  $.ajax({
    url: baseUrlGetlistUser,
    type: "GET",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    contentType: "application/json",
    error: function (err) {
      console.log(err.message);
      confirm(err.responseJSON.message);
    },
    success: function (data) {
      ManageUserFillToTable(data.content);

      buildPagingUser(data.number + 1, data.totalPages);
      PageSizeUser();
    },
  });
}

function PageSizeUser() {
  // $(".filterEntries").empty();

  $(".filterEntries")
    .empty()
    .append(
      `
      <div class = "entries" >
        Show  <select onchange="changPageSize()" name = "" id = "table_size">
                <option value = "2">5</option>
                <option value = "10">10</option>
                <option value = "20">20</option>
                <option value = "50">50</option>
              </select> user
      </div>
  `
    );
}

function changPageSize() {
  let key = document.getElementById("table_size").value;
  console.log(key);

  baseUrlGetlistUser = baseUrlGetlistUser.substr(0, 41);
  baseUrlGetlistUser = baseUrlGetlistUser + "?size=" + key;
  console.log(baseUrlGetlistUser);
  getListUser();
}
