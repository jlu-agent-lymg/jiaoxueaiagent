package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.entity.CourseLessonEntity;

import java.util.List;

/**
 * 课节业务接口。
 */
public interface CourseLessonService extends IService<CourseLessonEntity> {

    Long createLesson(CourseLessonEntity lesson);

    void updateLesson(CourseLessonEntity lesson);

    List<CourseLessonEntity> listByCourseId(Long courseId);
}
