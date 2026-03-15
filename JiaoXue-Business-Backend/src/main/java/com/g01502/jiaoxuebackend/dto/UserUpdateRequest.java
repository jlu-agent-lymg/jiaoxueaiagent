package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户更新请求。
 */
@Data
public class UserUpdateRequest {

    @NotNull(message = "用户 ID 不能为空")
    private Long id;

    private String nickname;
    private String phone;
    private String email;
    private String role;
    private Integer status;
}
