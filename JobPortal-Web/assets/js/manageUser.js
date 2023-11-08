let baseUrlUser = "http://localhost:8887/api/v1/user/"; 
let baseUrlGetlistUser = "http://localhost:8887/api/v1/user/get-All";
let pageCurrent = 2;
let baseViewListUser = "http://localhost:8887/api/v1/user/view";


$(function () {
  getListUser();
  // buildManager();
  // // buildAdmin();
  // // getListProduct();
  // // getListJob();
  // viewListJob();
  // searchUser();

  
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
                <td>` +valueUser.id +`    </td>
                <td><img src="` +valueUser.image +`" alt="" width="50" height="50"></td>
                <td>` +valueUser.full_name +`</td>
                <td>` +valueUser.username +`</td>
                <td>` +valueUser.password +`</td>    
                <td>` +valueUser.email +`</td>     
                <td>` +valueUser.information.toUpperCase() +`</td>  
                <td>` +valueUser.role +`</td>    
                <td>` +valueUser.phone_number +`</td>
                <td>
                <svg onclick="deleteUser(${valueUser.id})" xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
              </svg>
                    
                </td>
                </tr>
            `
        );
  }
}


function deleteUser(userId) {
    $.ajax({
        url: baseUrlUser + "delete/" + userId,
        type: 'DELETE',
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

        // console.log(data);
        alert("Delete Success")
        ManageUserFillToTable(data);
      },
    })
    
}

function buildPagingUser (currentPage, totalPage) {
  console.log(currentPage, totalPage);
  if (currentPage === 1 && totalPage === 1) {
    $(".user-pagination").empty().append(`
      <button>Next</button>
    `) 
  }else {
    $(".user-pagination").empty().append(`
      <button onclick = "pre()">Prev</button>
      <span class = "indexPage"></span>
      <button onclick = "next()">Next</button>
    `) 
  }

    for(var index = 1; index <= totalPage; index++){

      if (index === currentPage) {
        
        $(".indexPage").append(
          `
            <button style="background-color: violet; cursor: no-drop;">${index}</button>
          `
          )
      }else {
        $(".indexPage").append(
          `
            <button onclick="chosePageIndex(${index})">${index}</button>
          `
          )
      }
    }
    
    if (currentPage === totalPage) {
      $(".user-pagination").empty().append(`
      <button onclick = "pre()">Pre</button>
    `) 
    }
}

function chosePageIndex(indexpage) {

    baseUrlGetlistUser = baseUrlGetlistUser.substr(0,41)

    baseUrlGetlistUser = baseUrlGetlistUser + "?page=" + indexpage;
    getListUser();
  
}

function next () {
  baseUrlGetlistUser = baseUrlGetlistUser.substr(0,41)
  baseUrlGetlistUser = baseUrlGetlistUser + "?page=" + pageCurrent++;
  getListUser();

}


function pre() {

  
  baseUrlGetlistUser = baseUrlGetlistUser.substr(0,41)
  // console.log(baseUrlGetlistUser);
  baseUrlGetlistUser = baseUrlGetlistUser + "?page=" + (--pageCurrent-1);
  // console.log(baseUrlGetlistUser);
  getListUser();
}


function clearInput() {
  document.getElementById("search").value = '';
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
            let value = data.filter(val => {
              return val.full_name.toLowerCase().includes(key) || 
              val.email.toLowerCase().includes(key) || 
              val.information.toLowerCase().includes(key) ||
              val.role.toLowerCase().includes(key)
            })
          
            console.log(value.length);
            clearInput();
            ManageUserFillToTable(value);
            buildPagingUser(data.number + 1, data.totalPages)
            
          },
        });
      }
  
  




function getListUser() {
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
      // console.log(data.size);
      // console.log(data.number);
      // console.log(data.content);
      // console.log(data.totalPages);
      ManageUserFillToTable(data.content);
      buildPagingUser(data.number + 1, data.totalPages)
      PageSizeUser();
      
    },
  });
}

// function changSize() {
//   // document.getElementById("table_size").value = 0;
//   let val = document.getElementById("table_size").value;
//   console.log(val);
  
// }


function PageSizeUser() {
  // $(".filterEntries").empty();

  $(".filterEntries").empty().append(
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
