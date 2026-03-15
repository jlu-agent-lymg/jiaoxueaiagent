-- 教学平台业务库测试数据脚本
-- 使用建议：
-- 1. 先执行 schema-full.sql，再执行本脚本。
-- 2. 本脚本使用固定测试 ID，便于前后端联调和接口回归验证。
-- 3. 删除语句只清理本脚本约定的测试数据，不会直接清空整张表。

USE jiaoxue_biz;

-- 按依赖关系逆序清理旧测试数据，确保脚本可重复执行。
DELETE FROM edu_order WHERE id IN (100001, 100002, 100003, 100004);
DELETE FROM edu_notice WHERE id IN (90001, 90002, 90003);
DELETE FROM edu_exam_record WHERE id IN (80001, 80002, 80003, 80004);
DELETE FROM edu_exam_paper WHERE id IN (70001, 70002, 70003);
DELETE FROM edu_learning_plan WHERE id IN (60001, 60002, 60003);
DELETE FROM edu_resource WHERE id IN (50001, 50002, 50003, 50004, 50005);
DELETE FROM edu_course_enrollment WHERE id IN (40001, 40002, 40003, 40004);
DELETE FROM edu_course_lesson WHERE id IN (30001, 30002, 30003, 30004, 30005, 30006);
DELETE FROM edu_course WHERE id IN (20001, 20002, 20003);
DELETE FROM sys_user WHERE id IN (10001, 10002, 10003, 10004, 10005, 10006);

-- 用户测试数据：覆盖管理员、教师、学生三类角色。
INSERT INTO sys_user (id, username, password, nickname, phone, email, role, status, create_time, update_time, deleted) VALUES
(10001, 'admin01', '123456', '平台管理员', '13800000001', 'admin01@example.com', 'admin', 1, '2026-03-15 09:00:00', '2026-03-15 09:00:00', 0),
(10002, 'teacher_zhang', '123456', '张老师', '13800000002', 'teacher.zhang@example.com', 'teacher', 1, '2026-03-15 09:05:00', '2026-03-15 09:05:00', 0),
(10003, 'teacher_li', '123456', '李老师', '13800000003', 'teacher.li@example.com', 'teacher', 1, '2026-03-15 09:10:00', '2026-03-15 09:10:00', 0),
(10004, 'student_wang', '123456', '王小明', '13800000004', 'student.wang@example.com', 'student', 1, '2026-03-15 09:15:00', '2026-03-15 09:15:00', 0),
(10005, 'student_chen', '123456', '陈小雨', '13800000005', 'student.chen@example.com', 'student', 1, '2026-03-15 09:20:00', '2026-03-15 09:20:00', 0),
(10006, 'student_liu', '123456', '刘晨', '13800000006', 'student.liu@example.com', 'student', 1, '2026-03-15 09:25:00', '2026-03-15 09:25:00', 0);

-- 课程测试数据：覆盖已发布、草稿、已下线三种状态。
INSERT INTO edu_course (id, title, summary, teacher_id, category, difficulty, price, status, create_time, update_time, deleted) VALUES
(20001, 'Java 后端开发入门', '面向零基础学员的 Java 后端开发课程，覆盖 Spring Boot、接口设计与数据库基础。', 10002, '编程开发', '初级', 199.00, 'published', '2026-03-15 10:00:00', '2026-03-15 10:00:00', 0),
(20002, '数据结构与算法实战', '通过刷题和案例拆解常见数据结构与算法题型。', 10003, '算法设计', '中级', 299.00, 'draft', '2026-03-15 10:10:00', '2026-03-15 10:10:00', 0),
(20003, '教学设计工作坊', '围绕课程设计、课堂组织与教学复盘展开的专题课程。', 10002, '教师成长', '中级', 99.00, 'offline', '2026-03-15 10:20:00', '2026-03-15 10:20:00', 0);

-- 课节测试数据：覆盖视频、文档、直播、回放等内容类型。
INSERT INTO edu_course_lesson (id, course_id, title, content_type, content_url, duration_minutes, sort_no, status, create_time, update_time, deleted) VALUES
(30001, 20001, '认识 Spring Boot 工程结构', 'video', 'https://example.com/course/20001/lesson/1', 35, 1, 'enabled', '2026-03-15 10:30:00', '2026-03-15 10:30:00', 0),
(30002, 20001, 'RESTful 接口设计规范', 'doc', 'https://example.com/course/20001/lesson/2', 20, 2, 'enabled', '2026-03-15 10:35:00', '2026-03-15 10:35:00', 0),
(30003, 20001, 'MySQL 数据建模实践', 'live', 'https://example.com/course/20001/lesson/3', 60, 3, 'enabled', '2026-03-15 10:40:00', '2026-03-15 10:40:00', 0),
(30004, 20002, '数组与链表综合训练', 'video', 'https://example.com/course/20002/lesson/1', 45, 1, 'enabled', '2026-03-15 10:45:00', '2026-03-15 10:45:00', 0),
(30005, 20002, '递归与回溯思维导图', 'doc', 'https://example.com/course/20002/lesson/2', 25, 2, 'disabled', '2026-03-15 10:50:00', '2026-03-15 10:50:00', 0),
(30006, 20003, '教学反思案例复盘', 'replay', 'https://example.com/course/20003/lesson/1', 50, 1, 'enabled', '2026-03-15 10:55:00', '2026-03-15 10:55:00', 0);

-- 报名测试数据：覆盖学习中、已完成、已过期状态。
INSERT INTO edu_course_enrollment (id, course_id, student_id, progress, status, last_study_time, create_time, update_time, deleted) VALUES
(40001, 20001, 10004, 35, 'studying', '2026-03-15 11:00:00', '2026-03-15 11:00:00', '2026-03-15 11:00:00', 0),
(40002, 20001, 10005, 100, 'completed', '2026-03-14 20:30:00', '2026-03-10 09:00:00', '2026-03-14 20:30:00', 0),
(40003, 20002, 10004, 10, 'studying', '2026-03-15 11:05:00', '2026-03-15 11:05:00', '2026-03-15 11:05:00', 0),
(40004, 20003, 10006, 80, 'expired', '2026-02-20 18:00:00', '2026-02-01 08:30:00', '2026-02-20 18:00:00', 0);

-- 学习资源测试数据：覆盖待审核、审核通过、审核驳回三种状态。
INSERT INTO edu_resource (id, course_id, title, resource_type, url, uploader_id, audit_status, create_time, update_time, deleted) VALUES
(50001, 20001, 'Java 后端开发课程讲义', 'ppt', 'https://example.com/resource/50001', 10002, 'pass', '2026-03-15 11:10:00', '2026-03-15 11:10:00', 0),
(50002, 20001, '接口调试说明文档', 'doc', 'https://example.com/resource/50002', 10002, 'pending', '2026-03-15 11:12:00', '2026-03-15 11:12:00', 0),
(50003, 20002, '算法复杂度速查表', 'doc', 'https://example.com/resource/50003', 10003, 'pass', '2026-03-15 11:14:00', '2026-03-15 11:14:00', 0),
(50004, 20003, '课堂活动设计模板', 'question', 'https://example.com/resource/50004', 10002, 'reject', '2026-03-15 11:16:00', '2026-03-15 11:16:00', 0),
(50005, 20001, '数据库建模案例视频', 'video', 'https://example.com/resource/50005', 10003, 'pass', '2026-03-15 11:18:00', '2026-03-15 11:18:00', 0);

-- 学习计划测试数据：覆盖运行中、暂停、已完成状态。
INSERT INTO edu_learning_plan (id, student_id, course_id, daily_target_minutes, target_finish_date, status, remarks, create_time, update_time, deleted) VALUES
(60001, 10004, 20001, 45, '2026-04-15', 'running', '当前目标是先完成接口开发与数据库章节。', '2026-03-15 11:20:00', '2026-03-15 11:20:00', 0),
(60002, 10005, 20001, 30, '2026-03-30', 'finished', '已经按计划学完课程并完成结课测试。', '2026-03-01 09:30:00', '2026-03-14 20:40:00', 0),
(60003, 10006, 20003, 20, '2026-03-20', 'paused', '学员近期出差，计划临时暂停一周。', '2026-03-05 14:00:00', '2026-03-12 14:00:00', 0);

-- 试卷测试数据：覆盖已发布、草稿、已归档状态。
INSERT INTO edu_exam_paper (id, course_id, title, total_score, duration_minutes, passing_score, question_json, status, create_time, update_time, deleted) VALUES
(70001, 20001, 'Java 后端开发阶段测验', 100, 60, 60, '[{"id":"Q1","type":"single","score":20,"title":"Java 中用于定义类的关键字是什么？","options":["class","struct","object","define"],"answer":"class"},{"id":"Q2","type":"single","score":20,"title":"Spring Boot 默认内嵌的 Web 容器通常是哪个？","options":["Tomcat","Jetty","Undertow","Nginx"],"answer":"Tomcat"},{"id":"Q3","type":"short","score":60,"title":"请简述 RESTful 接口设计中的资源命名原则。","answer":"资源名采用名词复数形式，结合 HTTP 方法表达操作语义。"}]', 'published', '2026-03-15 11:30:00', '2026-03-15 11:30:00', 0),
(70002, 20002, '算法基础随堂测验', 100, 45, 60, '[{"id":"Q1","type":"single","score":50,"title":"下面哪种数据结构适合实现队列？","options":["数组","链表","栈","哈希表"],"answer":"链表"},{"id":"Q2","type":"short","score":50,"title":"请说明时间复杂度 O(n log n) 的常见算法。","answer":"如归并排序、堆排序等。"}]', 'draft', '2026-03-15 11:35:00', '2026-03-15 11:35:00', 0),
(70003, 20003, '教学设计复盘试卷', 100, 40, 60, '[{"id":"Q1","type":"short","score":100,"title":"结合一次真实课堂，描述你的教学反思流程。","answer":"从教学目标、课堂互动、学生反馈与改进计划四个维度展开。"}]', 'archived', '2026-03-15 11:40:00', '2026-03-15 11:40:00', 0);

-- 考试记录测试数据：覆盖及格与不及格两种结果。
INSERT INTO edu_exam_record (id, exam_paper_id, student_id, score, submit_time, answer_json, result, create_time, update_time, deleted) VALUES
(80001, 70001, 10004, 78, '2026-03-15 12:00:00', '{"Q1":"class","Q2":"Tomcat","Q3":"资源名建议使用名词，并通过 HTTP 方法表达新增、查询、更新和删除。"}', 'pass', '2026-03-15 12:00:00', '2026-03-15 12:00:00', 0),
(80002, 70001, 10005, 92, '2026-03-14 20:20:00', '{"Q1":"class","Q2":"Tomcat","Q3":"接口路径应聚焦资源，不要把动作写进 URL。"}', 'pass', '2026-03-14 20:20:00', '2026-03-14 20:20:00', 0),
(80003, 70002, 10004, 55, '2026-03-15 12:10:00', '{"Q1":"数组","Q2":"快速排序"}', 'fail', '2026-03-15 12:10:00', '2026-03-15 12:10:00', 0),
(80004, 70003, 10006, 68, '2026-02-22 18:30:00', '{"Q1":"先回看教学目标，再分析学生互动情况，最后形成改进清单。"}', 'pass', '2026-02-22 18:30:00', '2026-02-22 18:30:00', 0);

-- 公告测试数据：覆盖全员公告、学生公告、教师公告，以及已发布/草稿状态。
INSERT INTO edu_notice (id, title, content, publisher_id, scope_type, status, publish_time, create_time, update_time, deleted) VALUES
(90001, '平台联调环境开放通知', '教学平台测试环境已开放，请相关同学使用测试账号进行课程、报名、考试与订单流程联调。', 10001, 'all', 'published', '2026-03-15 12:20:00', '2026-03-15 12:20:00', '2026-03-15 12:20:00', 0),
(90002, '学生端学习打卡提醒', '请已报名学员在本周内完成课程第一章学习，并同步更新学习计划。', 10002, 'student', 'published', '2026-03-15 12:25:00', '2026-03-15 12:25:00', '2026-03-15 12:25:00', 0),
(90003, '教师工作坊讲义征集', '本公告作为草稿保留，用于后续发布教师工作坊资料提交通知。', 10001, 'teacher', 'draft', NULL, '2026-03-15 12:30:00', '2026-03-15 12:30:00', 0);

-- 订单测试数据：覆盖未支付、已支付、已退款、已关闭状态。
INSERT INTO edu_order (id, order_no, user_id, biz_type, biz_id, amount, pay_status, pay_channel, paid_time, create_time, update_time, deleted) VALUES
(100001, 'ORD202603150001', 10004, 'course', 20001, 199.00, 'unpaid', NULL, NULL, '2026-03-15 12:40:00', '2026-03-15 12:40:00', 0),
(100002, 'ORD202603150002', 10005, 'course', 20001, 199.00, 'paid', 'alipay', '2026-03-14 19:58:00', '2026-03-14 19:50:00', '2026-03-14 19:58:00', 0),
(100003, 'ORD202603150003', 10006, 'course', 20003, 99.00, 'refunded', 'wechat', '2026-02-01 09:10:00', '2026-02-01 09:00:00', '2026-02-05 16:00:00', 0),
(100004, 'ORD202603150004', 10004, 'course', 20002, 299.00, 'closed', NULL, NULL, '2026-03-15 12:45:00', '2026-03-15 12:50:00', 0);
