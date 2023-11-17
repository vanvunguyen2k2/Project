package com.example.project_job_seeking.Service;

import com.example.project_job_seeking.Config.Exception.AppException;
import com.example.project_job_seeking.Config.Exception.ErrorResponseEnum;
import com.example.project_job_seeking.Repository.UserRepository;
import com.example.project_job_seeking.Repository.specification.UserSpecification;
import com.example.project_job_seeking.modal.Entity.Role;
import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.SearchUserRequest;
import com.example.project_job_seeking.modal.dto.UserCreateRequestDto;
import com.example.project_job_seeking.modal.dto.UserDto;
import com.example.project_job_seeking.modal.dto.UserUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements IUserService , UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private IMailSenderService iMailSenderService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User create(UserCreateRequestDto dto) {
        Optional<User> optionalUser = userRepository.findUserByUsername(dto.getUsername());
        if (optionalUser.isPresent()) {
            throw  new AppException(ErrorResponseEnum.USER_NAME_EXISTSED);
        }
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);

        String passwordEncodeder = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(passwordEncodeder);
        entity.setRole(Role.CANDIDATE);
        entity.setWorking(true);

//        GỬI MAIL
        String text = "click de hoan thien tai khoan" + dto.getFull_name() + " : " + "<a href=\"http://localhost:8887/api/v1/user/get_by_id/6\">Click</a>\n";

        try {
            iMailSenderService.sendSimpleMessageWithHTML(
                    dto.getEmail(),
                    "Kich hoat tai khoan",
                    "click de hoan thien tai khoan" + "<a href=\"http://localhost:8887/api/v1/user/get_by_id/6\" style=\"font-size: large;\">Click</a>\n");
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return userRepository.save(entity);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void delete(int id) {
        Optional<User> checkRole = userRepository.findById(id);
        if (checkRole.isPresent()) {
            User user = checkRole.get();

            if (user.getRole() == Role.ADMIN){
                    throw new AppException(ErrorResponseEnum.FORBIDDEN);
            }else {
                user.setWorking(false);
                userRepository.save(user);
            }
        }
    }

    public Page<User> search(SearchUserRequest request){
        PageRequest pageRequest = null;
        if ("DESC".equals(request.getSortType())){
             pageRequest = PageRequest.of(request.getPage() - 1, request.getPage_size(), Sort.by(request.getSortField()).descending());
        }else {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getPage_size(), Sort.by(request.getSortField()).ascending());
        }

        Specification<User> condition = UserSpecification.buildCondition(request);
        return userRepository.findAll(condition, pageRequest);

    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User update(UserUpdateRequestDto dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        return userRepository.save(entity);

    }

    @Override
    public UserDto get_by_id(int id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()){
            UserDto  userDto = modelMapper.map(userOptional.get(), UserDto.class);
            return userDto;
        }else {
            return null;
        }
    }

    @Override
    public List<User> view() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    /**
     * DÙNG ĐỂ KIỂM TRA  (SECURITY)  USERNAME CÓ TỒN TẠI TRONG HỆ THỐNG HAY KHÔGNG
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    /**
     * LUỒNG CHẠY KHI CALL API - 1:     Authentication
     *                           2 :   Authority
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findUserByUsername(username);

//      NẾU KHÔNG CÓ DỮ LIỆU USERNAME TRONG USER THÌ BẮN RA LỖI
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("UserName không tồn tại");

//      NẾU CÓ DỮ LIỆU USERNAME TRẢ VỀ HỢP LỆ THÌ SẼ TẠO RA MỘT ĐỐI TƯỢNG USER DETAILS
        }else {

//          userdetails: là đối tượng mà spring security sử dụng để xác nhập chứ không phải là đối tượng user của minh trong entity
            User user = userOptional.get();

            /**
             * userdetails.getusername(): username
             * userdetails.getPassword(): mật khẩu đã đưuọc mã hóa
             * grantedAuthorityList: List quyền của user
             */

            List<GrantedAuthority> authoritiesList = new ArrayList<>();

//           Add thêm những permistion vào (chính là thằng Role() bên dưới);
            authoritiesList.add(user.getRole());

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authoritiesList);

        }
    }


}
