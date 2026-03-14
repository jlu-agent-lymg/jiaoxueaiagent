package com.g01502.jiaoxueaiagent.constant;

/**
 * 文件系统相关常量定义接口。
 *
 * <p>功能说明：统一声明工具读写与下载的基础目录。
 * <p>流程说明：各工具类拼接子目录后执行文件读写或资源下载，避免路径分散定义。
 */
public interface FileConstant {

    /**
     * 文件保存目录
     */
    String FILE_SAVE_DIR = System.getProperty("user.dir") + "/tmp";
}
