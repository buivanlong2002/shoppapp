package com.example.shopapp.controller;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.dtos.UserLoginDTO;
import com.example.shopapp.model.User;
import com.example.shopapp.response.ApiResponse;
import com.example.shopapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    private final MessageSource messageSource ;
    private final LocaleResolver localeResolver;

    @PostMapping("register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);

            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("passwords do not match");
            }
            ;
            userService.createUser(userDTO);

            return ResponseEntity.ok("Đăng ký thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO
            , HttpServletRequest request) {
           ApiResponse<String> apiResponse = new ApiResponse<>();
           String token = "";
        Locale locale = localeResolver.resolveLocale(request);
        try {
             token = userService.loginUser(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        } catch (Exception e) {
           e.printStackTrace();
        }
        if(token != null){
            apiResponse.setData(token);
            apiResponse.setCode("00");
            apiResponse.setMessage(messageSource.getMessage("user.login.login_successfully",null,locale));
        }else{
            apiResponse.setData(null);
            apiResponse.setCode("01");
            apiResponse.setMessage(" Đăng nhập thất bại");
        }
        return apiResponse;
    }


}
