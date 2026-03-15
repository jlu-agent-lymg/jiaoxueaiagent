package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.entity.CourseLessonEntity;
import com.g01502.jiaoxuebackend.mapper.CourseLessonMapper;
import com.g01502.jiaoxuebackend.service.CourseLessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课节业务实现。
 */
@Slf4j
@Service
public class CourseLessonServiceImpl extends ServiceImpl<CourseLessonMapper, CourseLessonEntity> implements CourseLessonService {

    @Override
    public Long createLesson(CourseLessonEntity lesson) {
        lesson.setStatus(lesson.getStatus() == null ? "enabled" : lesson.getStatus());
        save(lesson);
        log.info("创建课节成功, lessonId={}, courseId={}", lesson.getId(), lesson.getCourseId());
        return lesson.getId();
    }

    @Override
    public void updateLesson(CourseLessonEntity lesson) {
        CourseLessonEntity dbLesson = getById(lesson.getId());
        if (dbLesson == null) {
            throw new BusinessException(404, "课节不存在");
        }
        updateById(lesson);
        log.info("更新课节成功, lessonId={}", lesson.getId());
    }

    @Override
    public List<CourseLessonEntity> listByCourseId(Long courseId) {
        return lambdaQuery()
                .eq(CourseLessonEntity::getCourseId, courseId)
                .orderByAsc(CourseLessonEntity::getSortNo)
                .list();
    }
}
