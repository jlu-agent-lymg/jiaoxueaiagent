package com.g01502.jiaoxuebackend.common.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 通用分页响应结构。
 */
@Data
public class PageResponse<T> {

    private Long total;
    private Long current;
    private Long size;
    private List<T> records;

    public static <T> PageResponse<T> empty(Long current, Long size) {
        PageResponse<T> response = new PageResponse<>();
        response.setCurrent(current);
        response.setSize(size);
        response.setTotal(0L);
        response.setRecords(Collections.emptyList());
        return response;
    }
}
