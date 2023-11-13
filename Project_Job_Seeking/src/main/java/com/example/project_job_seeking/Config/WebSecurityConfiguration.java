package com.example.project_job_seeking.Config;

import com.example.project_job_seeking.Config.Exception.JWTRequestFilter;
import com.example.project_job_seeking.Service.UserService;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // kết hợp với @Bean để tạo thành 1 bean trong spring IOC
@EnableWebSecurity   // khai báo security
@EnableGlobalMethodSecurity(prePostEnabled = true) // Để có thể phân quyền tại controller
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;


    /*
        Khởi tạo BEAN (THEO SOLID)
    * */

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTRequestFilter jwtRequestFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).// Cấu hình UserDetailsService để khi xác thực người dùng sẽ gọi tới hàm loadUserByUsername()
                passwordEncoder(passwordEncoder);// Cấu hình phương thức để mã hoá mật khẩu
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // config những API ko cần xác thực
                .antMatchers("api/not-authenticated", "/swagger-ui/**", "/swagger-ui/index.html",
                        "/api/v1/auth/login-jwt" ,"/api/v1/user/create", "/api/v1/auth/login-basic-v2",
                        "/api/v1/user/get-All", "/api/v1/job/get-All" ,"/api/v1/job/search", "/api/v1/job_management/**",
                        "/api/v1/job/**", "/api/v1/user/**", "/api/v1/user/get_by_id/**" ).permitAll()

                // Config những API phải có Authority là ADMIN thì mới được truy cập
//                .antMatchers(String.valueOf(HttpMethod.GET),"/api/v1/user/**").hasAuthority("ADMIN")

                // Config những API phải có Authority là ADMIN hoặc User thì mới được truy cập
                .antMatchers("api/admin-or-user").hasAnyAuthority("ADMIN", "User")

                .anyRequest().authenticated()// Những đường dẫn còn lại cần được xác thực

                .and().httpBasic()// Kích hoạt cấu hình http basic trong Spring Security

                // tắt tính năng Cross-Site Request Forgery (CSRF) trong Spring Security.
                .and().cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//          KHAI BÁO LỚP FILTER SẼ ĐƯỢC THỰC HIỆN KHI AUTHEN VÀ AUTHOR
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override // Config cho đường dẫn (swagger) ko bị chặn bởi security
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui/index.html#")
                .antMatchers("/v3/api-docs/**");
    }
}
