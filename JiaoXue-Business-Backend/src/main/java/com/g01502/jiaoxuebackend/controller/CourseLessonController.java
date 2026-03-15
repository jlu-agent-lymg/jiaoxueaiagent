package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.entity.CourseLessonEntity;
import com.g01502.jiaoxuebackend.service.CourseLessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课节管理接口。
 */
@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class CourseLessonController {

    private final CourseLessonService lessonService;

    @PostMapping
    public ApiResponse<Long> createLesson(@Valid @RequestBody CourseLessonEntity lesson) {
        return ApiResponse.success("创建课节成功", lessonService.createLesson(lesson));
    }

    @PutMapping
    public ApiResponse<Void> updateLesson(@Valid @RequestBody CourseLessonEntity lesson) {
        lessonService.updateLesson(lesson);
        return ApiResponse.success("更新课节成功", null);
    }

    @GetMapping("/by-course/{courseId}")
    public ApiResponse<List<CourseLessonEntity>> listByCourseId(@PathVariable("courseId") Long courseId) {
        return ApiResponse.success(lessonService.listByCourseId(courseId));
    }
}
