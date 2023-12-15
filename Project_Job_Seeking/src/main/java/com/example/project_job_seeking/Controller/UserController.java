package com.example.project_job_seeking.Controller;

import com.example.project_job_seeking.Config.Exception.AppException;
import com.example.project_job_seeking.Config.Exception.ErrorResponseEnum;
import com.example.project_job_seeking.Config.annotation.UserIdExisit;
import com.example.project_job_seeking.Repository.UserRepository;
import com.example.project_job_seeking.Service.UserService;
import com.example.project_job_seeking.modal.Entity.Role;
import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@Validated
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public User create(@RequestBody @Valid UserCreateRequestDto dto){
        return userService.create(dto);
    }

    @GetMapping("/get-All")
    public Page<User> getAll(Pageable pageable){
        log.info("Message");
        log.warn("Warning");
        log.error("Error");
        return userService.getAll(pageable);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        userService.deleteAll();
        System.out.println("Xoa tat ca thanh cong");
    }

    @DeleteMapping("/delete/{id}")
//
    public void delete(@PathVariable(name = "id")  @Valid int id){
        Optional<User> checkRole = userRepository.findById(id);
        if (checkRole.isPresent()) {
            User user = checkRole.get();
            if (user.getRole() == Role.ADMIN) {
                throw new AppException(ErrorResponseEnum.FORBIDDEN);
            }else {
                userService.delete(id);
            }
        }
    }

    @PutMapping("/update")

    public User update(@RequestBody @Valid  UserUpdateRequestDto dto){
        return userService.update(dto);
    }

    @GetMapping("/view")
    public List<User> view (){
        return userService.view();
    }

    @GetMapping("/get_by_id/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
       public UserDto get_by_id(@UserIdExisit @PathVariable(name = "id")   int id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            return userService.get_by_id(id);
        }
        throw new AppException(ErrorResponseEnum.NOT_FOUND_USER);

    }


    @PostMapping("/search")
    public Page<User> search (@RequestBody SearchUserRequest request){
        return userService.search(request);
    }


    @GetMapping("/existsByUsername/{username}")
    public boolean existsByUsername(@PathVariable(name = "username") String username) {
        return userService.existsByUsername(username);

    }

    @GetMapping("/existsByEmail/{email}")
    public boolean existsByEmail(@PathVariable(name = "email") String email) {
        return userService.existsByEmail(email);

    }


    @GetMapping("/profileUser")
    public ResponseEntity<?> getUserProfile (Authentication authentication) {


//          LẤY RA THÔNG TIN USERNAME KHI ĐÃ ĐĂNG NHẬP THÀNH CÔNG
        String username = authentication.getName();

//        TIẾP ĐẾN LÀ LẤY THÔNG TIN CỦA USER ĐÓ THÔNG QUA USER NAME ĐÃ LẤY Ở TRÊN

        Optional<User> user = userRepository.findUserByUsername(username);
        System.out.println(user);

        ProfileDTO profileDTO = modelMapper.map(user.get(), ProfileDTO.class);

        System.out.println(profileDTO);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


//    public ResponseEntity<?> changeProfile (Authentication authentication, @RequestBody ChaneProfileDTO chaneProfileDTO) {
//
//        //          LẤY RA THÔNG TIN USERNAME KHI ĐÃ ĐĂNG NHẬP THÀNH CÔNG
//        String userName = authentication.getName();
//
//
//
//
//
//    }


}
