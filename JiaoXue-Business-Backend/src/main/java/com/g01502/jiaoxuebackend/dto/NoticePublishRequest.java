package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 公告发布请求。
 */
@Data
public class NoticePublishRequest {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotNull(message = "发布人不能为空")
    private Long publisherId;

    private String scopeType;
}
