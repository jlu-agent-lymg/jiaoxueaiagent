-- 教学平台业务库建表脚本
-- 说明：本脚本放在业务模块中，便于和代码版本保持一致。
USE jiaoxue_biz;
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    username VARCHAR(64) NOT NULL COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '密码(建议密文)',
    nickname VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    phone VARCHAR(32) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    role VARCHAR(32) NOT NULL DEFAULT 'student' COMMENT '角色:student/teacher/admin',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1启用 0禁用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    UNIQUE KEY uk_sys_user_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS edu_course (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    title VARCHAR(128) NOT NULL COMMENT '课程标题',
    summary VARCHAR(1000) DEFAULT NULL COMMENT '课程简介',
    teacher_id BIGINT NOT NULL COMMENT '讲师ID',
    category VARCHAR(64) DEFAULT NULL COMMENT '课程分类',
    difficulty VARCHAR(32) DEFAULT NULL COMMENT '难度等级',
    price DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '课程价格',
    status VARCHAR(32) NOT NULL DEFAULT 'draft' COMMENT '状态:draft/published/offline',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    KEY idx_edu_course_teacher_id (teacher_id),
    KEY idx_edu_course_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

CREATE TABLE IF NOT EXISTS edu_course_lesson (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    title VARCHAR(128) NOT NULL COMMENT '课节标题',
    content_type VARCHAR(32) NOT NULL COMMENT '内容类型:video/doc/live/replay',
    content_url VARCHAR(1000) DEFAULT NULL COMMENT '内容地址',
    duration_minutes INT DEFAULT 0 COMMENT '时长(分钟)',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    status VARCHAR(32) NOT NULL DEFAULT 'enabled' COMMENT '状态:enabled/disabled',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    KEY idx_edu_course_lesson_course_id (course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课节表';

CREATE TABLE IF NOT EXISTS edu_course_enrollment (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    progress INT NOT NULL DEFAULT 0 COMMENT '学习进度(0-100)',
    status VARCHAR(32) NOT NULL DEFAULT 'studying' COMMENT '状态:studying/completed/expired',
    last_study_time DATETIME DEFAULT NULL COMMENT '最后学习时间',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    UNIQUE KEY uk_edu_enrollment_course_student (course_id, student_id),
    KEY idx_edu_enrollment_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课报名表';

CREATE TABLE IF NOT EXISTS edu_resource (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    course_id BIGINT DEFAULT NULL COMMENT '课程ID',
    title VARCHAR(128) NOT NULL COMMENT '资源标题',
    resource_type VARCHAR(32) NOT NULL COMMENT '资源类型:doc/video/ppt/question',
    url VARCHAR(1000) NOT NULL COMMENT '资源地址',
    uploader_id BIGINT NOT NULL COMMENT '上传人ID',
    audit_status VARCHAR(32) NOT NULL DEFAULT 'pending' COMMENT '审核状态:pending/pass/reject',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    KEY idx_edu_resource_course_id (course_id),
    KEY idx_edu_resource_audit_status (audit_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习资源表';

CREATE TABLE IF NOT EXISTS edu_learning_plan (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    daily_target_minutes INT NOT NULL COMMENT '每日目标时长(分钟)',
    target_finish_date DATE NOT NULL COMMENT '目标完成日期',
    status VARCHAR(32) NOT NULL DEFAULT 'running' COMMENT '状态:running/paused/finished',
    remarks VARCHAR(500) DEFAULT NULL COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    KEY idx_edu_learning_plan_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习计划表';

CREATE TABLE IF NOT EXISTS edu_exam_paper (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    title VARCHAR(128) NOT NULL COMMENT '试卷标题',
    total_score INT NOT NULL DEFAULT 100 COMMENT '总分',
    duration_minutes INT NOT NULL DEFAULT 60 COMMENT '考试时长(分钟)',
    passing_score INT NOT NULL DEFAULT 60 COMMENT '及格分',
    question_json MEDIUMTEXT DEFAULT NULL COMMENT '题目JSON',
    status VARCHAR(32) NOT NULL DEFAULT 'draft' COMMENT '状态:draft/published/archived',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    KEY idx_edu_exam_paper_course_id (course_id),
    KEY idx_edu_exam_paper_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷表';

CREATE TABLE IF NOT EXISTS edu_exam_record (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    exam_paper_id BIGINT NOT NULL COMMENT '试卷ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    score INT NOT NULL COMMENT '得分',
    submit_time DATETIME NOT NULL COMMENT '提交时间',
    answer_json MEDIUMTEXT DEFAULT NULL COMMENT '答案JSON',
    result VARCHAR(32) NOT NULL COMMENT '结果:pass/fail',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    KEY idx_edu_exam_record_exam_paper_id (exam_paper_id),
    KEY idx_edu_exam_record_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试记录表';

CREATE TABLE IF NOT EXISTS edu_notice (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    title VARCHAR(128) NOT NULL COMMENT '公告标题',
    content TEXT NOT NULL COMMENT '公告内容',
    publisher_id BIGINT NOT NULL COMMENT '发布人ID',
    scope_type VARCHAR(32) NOT NULL DEFAULT 'all' COMMENT '范围:all/student/teacher',
    status VARCHAR(32) NOT NULL DEFAULT 'published' COMMENT '状态:draft/published/offline',
    publish_time DATETIME DEFAULT NULL COMMENT '发布时间',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    KEY idx_edu_notice_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

CREATE TABLE IF NOT EXISTS edu_order (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    order_no VARCHAR(64) NOT NULL COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    biz_type VARCHAR(32) NOT NULL DEFAULT 'course' COMMENT '业务类型:course/member/service',
    biz_id BIGINT NOT NULL COMMENT '业务ID',
    amount DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    pay_status VARCHAR(32) NOT NULL DEFAULT 'unpaid' COMMENT '支付状态:unpaid/paid/refunded/closed',
    pay_channel VARCHAR(32) DEFAULT NULL COMMENT '支付渠道',
    paid_time DATETIME DEFAULT NULL COMMENT '支付时间',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删 1已删',
    UNIQUE KEY uk_edu_order_order_no (order_no),
    KEY idx_edu_order_user_id (user_id),
    KEY idx_edu_order_pay_status (pay_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
