package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.dto.UserCreateRequest;
import com.g01502.jiaoxuebackend.dto.UserUpdateRequest;
import com.g01502.jiaoxuebackend.entity.UserEntity;
import com.g01502.jiaoxuebackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理接口。
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<Long> createUser(@Valid @RequestBody UserCreateRequest request) {
        return ApiResponse.success("创建用户成功", userService.createUser(request));
    }

    @PutMapping
    public ApiResponse<Void> updateUser(@Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
        return ApiResponse.success("更新用户成功", null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<UserEntity>> pageUsers(@Valid PageRequest request) {
        return ApiResponse.success(userService.pageUsers(request));
    }
}
