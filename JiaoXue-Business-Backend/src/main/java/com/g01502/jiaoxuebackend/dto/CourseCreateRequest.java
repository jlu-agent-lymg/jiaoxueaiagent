package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 课程创建请求。
 */
@Data
public class CourseCreateRequest {

    @NotBlank(message = "课程标题不能为空")
    private String title;

    private String summary;

    @NotNull(message = "讲师 ID 不能为空")
    private Long teacherId;

    private String category;
    private String difficulty;
    private BigDecimal price;
}
