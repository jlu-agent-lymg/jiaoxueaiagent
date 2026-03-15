# JiaoXue-Business-Backend

独立业务后端模块（与现有 AI Agent 分离），采用 Spring Boot + MyBatis-Plus 三层结构。

## 功能范围

- 用户与角色基础管理
- 课程管理（创建、编辑、发布）
- 课节管理
- 学生报名与学习进度
- 学习资源与审核
- 学习计划管理
- 试卷与考试记录
- 公告发布
- 订单与支付状态流转

## 运行说明

1. 创建数据库并执行建表脚本：`src/main/resources/sql/schema.sql`
2. 配置环境变量：
   - `BIZ_DB_URL`
   - `BIZ_DB_USERNAME`
   - `BIZ_DB_PASSWORD`
3. 启动命令：
   - `mvn spring-boot:run`

默认端口：`18081`
