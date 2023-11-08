"use strict";

let isAdmin = false;
let isEMPLOYER = false;
let isCandidate = false;
function checkAdmin() {
    
    if (localStorage.getItem("role") === "ADMIN") {
        isAdmin = true;
    }else {
        isAdmin = false;
    }
    console.log(isAdmin);
}
function checkUser() {
    
    if (localStorage.getItem("role") === "EMPLOYER") {
        isEMPLOYER = true;
    }else {
        isEMPLOYER = false;
    }
    console.log(isEMPLOYER);
}

function checkCandidate() {
    if (localStorage.getItem("role") === "CANDIDATE") {
        isCandidate = true;
    }else {
        isCandidate = false;
    }
    console.log(isCandidate);
}
$(function () {

    checkAdmin();
    checkUser();
    checkLogin();
});

function checkLogin() {
    let username = localStorage.getItem("username");
    // let image = localStorage

    let textNoLogin =
        '<li class="header__navbar-item header__navbar-item--strong header__navbar-item--separate">' +
        '<a href="/newSignup.html" style="text-decoration: none;" class="text-white">Đăng ký</a>  </li>' +
        '<li class="header__navbar-item header__navbar-item--strong">' +
        '<a href="/login.html" style="text-decoration: none;" class="text-white">Đăng Nhập</a></li>';

    let textUserLoginRoleCandidate =
        '<li class="header__navbar-item header__navbar-user">' +
        '<img src="./assets/img/user_avatar.png" alt="" class="header__navbar-user-img" />' +
        '<span class="header__navbar-user-name">' +
        username +
        "</span>" +
        '<ul class="header__navbar-user-menu"><li class="header__navbar-user-item">' +
        '<a href="">Tài khoản của tôi</a></li><li class="header__navbar-user-item">' +
        '<a href="">Địa chỉ của tôi</a></li><li class="header__navbar-user-item">' +
        '<a href="">Đơn mua</a> </li><li class="header__navbar-user-item header__navbar-user-item--separate">' +
        '<a href="" onclick="logout()">Đăng xuất</a></li> </ul></li>'
        
    let textUserLoginRoleAdmin =
        '<li class="header__navbar-item header__navbar-user">' +
        '<img src="./assets/img/user_avatar.png" alt="" class="header__navbar-user-img" />' +
        '<span class="header__navbar-user-name">' +
        username +
        "</span>" +
        '<ul class="header__navbar-user-menu"><li class="header__navbar-user-item">' +
        '<a href="">Tài khoản của tôi</a></li><li class="header__navbar-user-item">' +
        '<a href="./manageUSer.html">Quản lý người dùng</a></li><li class="header__navbar-user-item">' +
        '<a href="">Địa chỉ của tôi</a></li><li class="header__navbar-user-item">' +
        '<a href="">Đơn mua</a> </li><li class="header__navbar-user-item header__navbar-user-item--separate">' +
        '<a href="" onclick="logout()">Đăng xuất</a></li> </ul></li>'

    let textUserLoginRoleEmployee = 
        '<li class="header__navbar-item header__navbar-user">' +
        '<img src="./assets/img/user_avatar.png" alt="" class="header__navbar-user-img" />' +
        '<span class="header__navbar-user-name">' +
        username +
        "</span>" +
        '<ul class="header__navbar-user-menu"><li class="header__navbar-user-item">' +
        '<a href="">Tài khoản của tôi</a></li><li class="header__navbar-user-item">' +
        '<a href="">Địa chỉ của tôi</a></li><li class="header__navbar-user-item">' +
        '<a href="">Quản lý công việc</a> </li><li class="header__navbar-user-item header__navbar-user-item--separate">' +
        '<a href="" onclick="logout()">Đăng xuất</a></li> </ul></li>'

    if (localStorage.getItem("token") === null) {
        console.log("chưa đăng nhập");
        document.getElementById("user-login").innerHTML = textNoLogin;
    } else if (isAdmin) {
        console.log("Đã đăng nhập với role là admin");
        document.getElementById("user-login").innerHTML = textUserLoginRoleAdmin;
    } else if (isCandidate) {
        console.log("Đã đăng nhập với role là Candidate");
        document.getElementById("user-login").innerHTML = textUserLoginRoleCandidate;
    } else if (isEMPLOYER) {
        console.log("Đã đăng nhập với role là EMPLOYER");
        document.getElementById("user-login").innerHTML = textUserLoginRoleEmployee;
        
    }
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("fullName");
    localStorage.removeItem("id");
    localStorage.removeItem("role");
    window.location.href = "/";
}
