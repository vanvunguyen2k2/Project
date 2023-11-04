package com.example.project_job_seeking.utils;


import antlr.Token;
import com.alibaba.fastjson.JSON;
import com.example.project_job_seeking.Config.Exception.AppExceptionDto;
import com.example.project_job_seeking.modal.Entity.Role;
import com.example.project_job_seeking.modal.dto.LoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class JWTokenUtils {
    private static final long EXPIRATION_TIME = 864000000; // 10 days, thời hạn của token
    private static final String SECRET = "123456"; // Chữ ký bí mật
    private static final String PREFIX_TOKEN = "Bearer"; // Ký tự đầu của token
    private static final String AUTHORIZATION = "Authorization"; // Key của token trên header



    // HÀM NÀY DÙNG ĐỂ TẠO RA TOKEN
    public String createAccessToken(LoginDto loginDto) {
        // TẠO GIÁ TRỊ THỜI HẠN TOKEN ( BẰNG THỜI GIAN HIỆN TẠI + 10 NGÀY HOẶC TUỲ THEO )
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = Jwts.builder()
                .setId(String.valueOf(loginDto.getId())) //set giá trị Id
                .setSubject(loginDto.getUsername()) // set giá trị subject
                .setIssuedAt(new Date())
                .setIssuer("VTI")
                .setExpiration(expirationDate) // set thời hạn của token
                .signWith(SignatureAlgorithm.HS512, SECRET) // KHAI BÁO PHƯƠNG THỨC MÃ HÓA TOKEN VÀ CHỮ KÝ BÍ MẬT
                .claim("authorities", loginDto.getRole().name()) // THÊM TRƯỜNG AUTHORITIES ĐỂ LƯU GIÁ TRỊ PHÂN QUYỀN
                .claim("user-Agent", loginDto.getUserAgent()).compact();// THÊM TRƯỜNG USER-AGENT ĐỂ LƯU THÔNG TIN TRÌNH DUYỆT ĐANG DÙNG


        return token;
    }

    // HÀM NÀY DÙNG ĐỂ GIẢI MÃ HÓA TOKEN
    public LoginDto parseAccessToken(String token) {
        LoginDto loginDto = new LoginDto();
        if (!token.isEmpty()) {
            try {
                token = token.replace(PREFIX_TOKEN, "").trim();
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();
                System.out.println(claims);
                // LẤY RA CÁC THÔNG TIN
                String user = claims.getSubject();
                System.out.println(user);
                Role role = Role.valueOf(claims.get("authorities").toString());
                System.out.println(role);
                String userAgent = claims.get("user-Agent").toString();

                // GÁN CÁC THÔNG TIN VÀO ĐỐI TƯỢNG LOGINDTO, CÓ THỂ SỬ DỤNG CONSTRUCTOR
                loginDto.setUsername(user);
                loginDto.setRole(role);
                loginDto.setUserAgent(userAgent);
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }
        return loginDto;
    }

    public boolean checkToken(String token, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        try {
            if (StringUtils.isBlank(token) || !token.startsWith(PREFIX_TOKEN)) { // token bị trống -> lỗi
                responseJson(response, new AppExceptionDto(401,"Token ko hợp lệ",  httpServletRequest.getRequestURI()));
                return false;
            }
//             BỎ TỪ KHÓA BEARER VÀ KHOẢNG TRẮNG Ở 2 ĐẦU
                    token = token.replace(PREFIX_TOKEN, "").trim();

            LoginDto loginDto = parseAccessToken(token);
            if (loginDto == null) { // Ko có token trên hệ thống
                responseJson(response, new AppExceptionDto(401,"Token ko tồn tại hoặc hết hạn", httpServletRequest.getRequestURI()));
                return false;
            }
        } catch (Exception e) {
            responseJson(response, new AppExceptionDto(401,e.getMessage(), httpServletRequest.getRequestURI()));
            return false;
        }
        return true;
    }

    // Hàm này dùng để response dữ liệu khi gặp lỗi
    private void responseJson(HttpServletResponse response, AppExceptionDto appException){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(401);
        try {
            response.getWriter().print(JSON.toJSONString(appException));
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
