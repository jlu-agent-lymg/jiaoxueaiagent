package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.dto.UserCreateRequest;
import com.g01502.jiaoxuebackend.dto.UserUpdateRequest;
import com.g01502.jiaoxuebackend.entity.UserEntity;
import com.g01502.jiaoxuebackend.mapper.UserMapper;
import com.g01502.jiaoxuebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现。
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public Long createUser(UserCreateRequest request) {
        long exists = lambdaQuery().eq(UserEntity::getUsername, request.getUsername()).count();
        if (exists > 0) {
            throw new BusinessException(409, "用户名已存在");
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(request, entity);
        if (entity.getRole() == null || entity.getRole().isBlank()) {
            entity.setRole("student");
        }
        entity.setStatus(1);
        save(entity);
        log.info("创建用户成功, userId={}, username={}", entity.getId(), entity.getUsername());
        return entity.getId();
    }

    @Override
    public void updateUser(UserUpdateRequest request) {
        UserEntity entity = getById(request.getId());
        if (entity == null) {
            throw new BusinessException(404, "用户不存在");
        }
        BeanUtils.copyProperties(request, entity);
        updateById(entity);
        log.info("更新用户成功, userId={}", entity.getId());
    }

    @Override
    public PageResponse<UserEntity> pageUsers(PageRequest request) {
        Page<UserEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<UserEntity> result = lambdaQuery()
                .like(request.getKeyword() != null && !request.getKeyword().isBlank(), UserEntity::getUsername, request.getKeyword())
                .or(request.getKeyword() != null && !request.getKeyword().isBlank())
                .like(request.getKeyword() != null && !request.getKeyword().isBlank(), UserEntity::getNickname, request.getKeyword())
                .page(page);

        PageResponse<UserEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
