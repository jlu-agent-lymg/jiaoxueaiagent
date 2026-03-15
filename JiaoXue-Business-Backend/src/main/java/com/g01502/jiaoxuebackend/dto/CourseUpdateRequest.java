package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 课程更新请求。
 */
@Data
public class CourseUpdateRequest {

    @NotNull(message = "课程 ID 不能为空")
    private Long id;

    private String title;
    private String summary;
    private String category;
    private String difficulty;
    private BigDecimal price;
    private String status;
}
