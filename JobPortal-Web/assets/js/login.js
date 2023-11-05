
let baseUrlAuth = "http://localhost:8887/api/v1";

function Account(username, password) {
  this.username = username;
  this.password = password;
}

function AccountSignUp(username,phone_number, password, image,information,full_name, email ,date_of_birth , gender,role) {
  this.username = username;
  this.phone_number = phone_number;
  this.password = password;
  this.image = image
  this.information = information;
  this.full_name = full_name;
  this.email = email;
  this.date_of_birth  = date_of_birth;
  this.gender = gender;
  this.role = role;

}

function signUp() {
  validateFomrSignup();
  event.preventDefault();
  let username = document.getElementById("inputUsername").value;
  let password = document.getElementById("password").value;
  let full_name = document.getElementById("fullname").value;
  let email = document.getElementById("email").value;
  let phone_number = document.getElementById("numberphone").value;
  let information = document.getElementById("information").value;
  let date_of_birth = document.getElementById("date_of_birth").value;
  let gender = document.getElementById("gender").value;
  let role = document.getElementById("role").value;
  let image = document.getElementById("image").value;

  let account = new AccountSignUp(username, phone_number, password, image,information, full_name, email, date_of_birth, gender, role);
  console.log(account);

  //   ------------------------------------- CALL API ĐĂNG KÝ -------------------------------------
    $.ajax({
      url: baseUrlAuth + "/user/create",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(account),
      error: function (err) {
          console.log(err)
          confirm(err.responseJSON.message)
      },
      success: function (data) {
          console.log(data)
          // window.location.href = "./index.html"
          window.location.href = "./login.html"
      }
  });
  // window.location.href = "./login.html"
}

function listenInput () {
  if(document.getElementById("inputUsername").value) {
    
    document.getElementById("errUsername").innerHTML = '';
  }

  if(document.getElementById("password").value) {
    
    document.getElementById("errPass").innerHTML = '';
  }

  if(document.getElementById("fullname").value) {
    
    document.getElementById("errFullname").innerHTML = '';
  }
  if(document.getElementById("email").value) {
    
    document.getElementById("errEmail").innerHTML = '';
  }

  if(document.getElementById("numberphone").value) {
    
    document.getElementById("errNumber").innerHTML = '';
  }

  if(document.getElementById("information").value) {
    
    document.getElementById("errInfo").innerHTML = '';
  }

}

function validateFomrSignup () {

  let regexUsername = /[a-zA-Z0-9]/; 
  if (!regexUsername.test(document.getElementById("inputUsername").value)) {
      let error = "Username không hợp lệ";
      document.getElementById("errUsername").innerHTML = error;
  }

  let regexPassword = /((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W]).{6,20})/;
  if (!regexPassword.test(document.getElementById("password").value)) {
    let error = "Password không hợp lệ";
    document.getElementById("errPass").innerHTML = error;
  }

  let regexFullname = /^([a-zA-Z0-9]+|[a-zA-Z0-9]+\s{1}[a-zA-Z0-9]{1,}|[a-zA-Z0-9]+\s{1}[a-zA-Z0-9]{3,}\s{1}[a-zA-Z0-9]{1,})$/
  if (!regexFullname.test(document.getElementById("fullname").value)) {
    let error = "Fullname không hợp lệ";
    document.getElementById("errFullname").innerHTML = error;
  }

  let regexEmail = /^[a-z0-9](\.?[a-z0-9]){5,}@g(oogle)?mail\.com$/;
  if (!regexEmail.test(document.getElementById("email").value)) {
    let error = "Email không hợp lệ";
    document.getElementById("errEmail").innerHTML = error;
  }

  let regexPhone = /^(?:(?:\+|00)33[\s.-]{0,3}(?:\(0\)[\s.-]{0,3})?|0)[1-9](?:(?:[\s.-]?\d{2}){4}|\d{2}(?:[\s.-]?\d{3}){2})$/
  if (!regexPhone.test(document.getElementById("numberphone").value)) {
    let error = "Phone không hợp lệ";
    document.getElementById("errNumber").innerHTML = error;
  }

  if (!document.getElementById("information").value) {
    console.log(document.getElementById("information").value);
    document.getElementById("errInfo").innerHTML = "Vui lòng điền thông tin";
  }

}

function validateFomrLogin () {
  if (!document.getElementById("inputUsername").value) {
    let error = "Vui lòng điền username của bạn";
    document.getElementById("errUsername").innerHTML = error;
  }


  if (!document.getElementById("password").value) {
    let error = "Vui lòng điền password của bạn";
    document.getElementById("errPass").innerHTML = error;
  }
}
function Checkvalue () {
  if(document.getElementById("inputUsername").value) {
    document.getElementById("errUsername").innerHTML ="";
  }

  if(document.getElementById("password").value) {
    document.getElementById("errPass").innerHTML ="";
  }
}

function Showpassword () {
    if (document.getElementById("password").type === "password") {
      document.getElementById("password").type = "text";
    }else {
      document.getElementById("password").type = "password";
    }

}




function login() {
  validateFomrLogin();
  event.preventDefault();
  let username = document.getElementById("inputUsername").value;
  let password = document.getElementById("password").value;
  let account = new Account(username, password);
  console.log(account);
  //   ------------------------------------- API ĐĂNG NHẬP -------------------------------------
  $.ajax({
    url: baseUrlAuth + "/auth/login-jwt",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(account),
    error: function (err) {
      console.log(err)
      confirm(err.responseJSON.message)
    },
    success: function (data) {
      console.log(data)
      localStorage.setItem("fullName", data.full_name);
      localStorage.setItem("id", data.id);
      localStorage.setItem("role", data.role);
      localStorage.setItem("token", data.token);
      localStorage.setItem("username", data.username);
      localStorage.setItem("userAgent", data.userAgent);
      window.location.href = "./index.html"
    }
  });
  
  // Fake data bằng file json
  // let checkAccount;
  // fetch('/assets/data/account.json')
  //   .then((response) => response.json())
  //   .then((json) =>
  //     checkLogin(json)
  //   );
  // function checkLogin(json) {
  //   checkAccount = json.find(element => (element.username === username && element.password === password))
  //   console.log(checkAccount)
  //   if (checkAccount) {
  //     localStorage.setItem("fullName", checkAccount.fullName);
  //     localStorage.setItem("id", checkAccount.id);
  //     localStorage.setItem("role", checkAccount.role);
  //     localStorage.setItem("token", checkAccount.token);
  //     localStorage.setItem("username", checkAccount.username);
  //     window.location.href = "./index.html"
  //   } else {
  //     alert("User hoặc mật khẩu ko đúng")
  //   }
  // }
}


