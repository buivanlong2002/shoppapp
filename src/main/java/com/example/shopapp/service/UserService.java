package com.example.shopapp.service;

import com.example.shopapp.components.JwtTokenUtil;
import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.mapper.UserMapper;
import com.example.shopapp.model.Role;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
  private final AuthenticationManager authenticationManager;


    @Override
   /// đăng ký  người dùng mới
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        /// kiểm tra số điện thoiaj đã tồn tại hay chưa
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (role.getName().toUpperCase().equals(role.ADMIN)) {
            throw new DataIntegrityViolationException("bạn không thể tạo admin");
        }
        User newUser = userMapper.toUser(userDTO);
        newUser.setRole(role);
        // kiểm tra nếu có accountId , không yêu cầu password
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = newUser.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            newUser.setPassword(encodePassword);
        }

        return userRepository.save(newUser);
    }

    @Override
    public String loginUser(String phoneNumber, String password) throws Exception {
       Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
       if (optionalUser.isEmpty()) {
           throw new DataNotFoundException("Ivalid phoneNumber and passwword");
       }
        User existingUser = optionalUser.get();
//       check password
        if (existingUser.getFacebookAccountId()==0 && existingUser.getGoogleAccountId()==0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

        }
       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
               phoneNumber,password);
        existingUser.getAuthorities();
       // auth  with java Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }


}
