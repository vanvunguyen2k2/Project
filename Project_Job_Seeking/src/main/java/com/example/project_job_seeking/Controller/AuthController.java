package com.example.project_job_seeking.Controller;


import com.example.project_job_seeking.Config.Exception.AppException;
import com.example.project_job_seeking.Config.Exception.ErrorResponseEnum;
import com.example.project_job_seeking.Repository.UserRepository;
import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.LoginDto;
import com.example.project_job_seeking.modal.dto.loginRequest;
import com.example.project_job_seeking.utils.JWTokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private JWTokenUtils jwTokenUtils;


    /**
     *
     * @param principal : ĐỐI TƯỢNG NÀY ĐƯỢC SINH RA KHI ĐÃ XÁC THỰC ĐƯỢC NGƯỜI DÙNG
     * @return
     */

    @GetMapping("/login-basic-v1")
    public LoginDto loginDto1 (Principal principal) {

        String username = principal.getName();
        System.out.println(username);

//        TÌM RA ĐỐI TƯỢNG ACCOUNT TỪ USERNAME

        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_USER);
        }

        User user = userOptional.get();
        System.out.println(user);
        LoginDto loginDto = new LoginDto();
        System.out.println(loginDto);
        BeanUtils.copyProperties(user, loginDto);
        return loginDto;


    }



    @GetMapping("/login-basic-v2")
    public LoginDto loginDto2 (@RequestParam String username, @RequestParam String password) {

//        TÌM RA ĐỐI TƯỢNG ACCOUNT TỪ USERNAME

        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()){
            throw new AppException(ErrorResponseEnum.USERNAME_NOT_EXIST);
        }

        User user = userOptional.get();
//        SO SÁNH PASSWORD
        boolean checkPassword = passwordEncoder.matches(password, user.getPassword());
        if (!checkPassword) {
            throw new AppException(ErrorResponseEnum.PASWORD_WRONG);
        }

        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(user, loginDto);
        return loginDto;


    }


    @PostMapping("/login-jwt")
    public LoginDto loginJWT (@RequestBody loginRequest loginRequest) {

//        TÌM RA ĐỐI TƯỢNG ACCOUNT TỪ USERNAME

        Optional<User> userOptional = userRepository.findUserByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()){
            throw new AppException(ErrorResponseEnum.USERNAME_NOT_EXIST);
        }

        User user = userOptional.get();
//        SO SÁNH PASSWORD
        boolean checkPassword = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!checkPassword) {
            throw new AppException(ErrorResponseEnum.PASWORD_WRONG);
        }

//        QUA BƯỚC BÊN TRÊN THÌ ĐÃ LOGIN THÀNH CÔNG

//        SAU ĐÓ TẠO RA MÃ TOKEN ĐỂ GÁN VÀO ĐỐI TƯƠNG LOGINDTO


        LoginDto loginDto = new LoginDto();
        System.out.println(loginDto);
        BeanUtils.copyProperties(user, loginDto);
        loginDto.setUserAgent(httpServletRequest.getHeader("User-Agent"));


        String token = jwTokenUtils.createAccessToken(loginDto);
        System.out.println(token);

        loginDto.setToken(token);
        return loginDto;


    }

}
