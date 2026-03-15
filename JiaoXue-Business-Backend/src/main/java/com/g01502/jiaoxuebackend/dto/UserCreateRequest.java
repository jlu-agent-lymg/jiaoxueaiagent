package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户创建请求。
 */
@Data
public class UserCreateRequest {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String nickname;
    private String phone;
    private String email;
    private String role;
}
