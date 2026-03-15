package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.dto.UserCreateRequest;
import com.g01502.jiaoxuebackend.dto.UserUpdateRequest;
import com.g01502.jiaoxuebackend.entity.UserEntity;

/**
 * 用户业务接口。
 */
public interface UserService extends IService<UserEntity> {

    Long createUser(UserCreateRequest request);

    void updateUser(UserUpdateRequest request);

    PageResponse<UserEntity> pageUsers(PageRequest request);
}
