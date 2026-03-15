package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 选课请求。
 */
@Data
public class EnrollmentCreateRequest {

    @NotNull(message = "课程 ID 不能为空")
    private Long courseId;

    @NotNull(message = "学生 ID 不能为空")
    private Long studentId;
}
