package com.example.project_job_seeking.Config.Exception;

import com.example.project_job_seeking.modal.dto.LoginDto;
import com.example.project_job_seeking.utils.JWTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JWTokenUtils jwTokenUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        /*
               LẤY TOKEN TỪ API
        * */

        String token = request.getHeader(AUTHORIZATION);
        System.out.println(request.getHeader(AUTHORIZATION));
        System.out.println(token);
        String httprequest = request.getRequestURI();
        System.out.println(httprequest);

        if (StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/auth/login-jwt")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/job/search")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/job/delete")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/user/delete")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/user/get_by_id/")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/job/get-All")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/user/existsByUsername/")

                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/user/create")
            || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/user/search")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/job_management")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/api/v1/user/get-All")
                || StringUtils.containsAnyIgnoreCase(httprequest, "/swagger-ui/**")
                || StringUtils.containsAnyIgnoreCase(httprequest, "/swagger-ui/index.html")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/swagger-resources/**")
                || StringUtils.containsAnyIgnoreCase( httprequest, "/v3/api-docs/**"))
        {
            filterChain.doFilter(request, response);

        }else {
            if (jwTokenUtils.checkToken(token, response, request)) {

//                   GIẢI MÃ TOKEN --> LẤY THÔNG TIN USER --> AUTHEN
                LoginDto loginDto = jwTokenUtils.parseAccessToken(token);

//                LẤY DANH SÁCH QUYỀN CỦA USER
                List<GrantedAuthority> authorityList = new ArrayList<>();
                authorityList.add(loginDto.getRole());

//                TẠO MỘT ĐỐI TƯỢNG ĐỂ AUTHEN VÀO HỆ THỐNG
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            }
        }
    }
}
