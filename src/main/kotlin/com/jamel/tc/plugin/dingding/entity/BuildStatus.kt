package com.jamel.tc.plugin.dingding.entity

/**
 * 构建状态
 * @since 2021/07/24 22:16
 * @author gaojun
 **/
enum class BuildStatus(val status: String, val color: String) {
    TO_QUEUE("队列中", "#722ED1"),
    START("开始", "#2F54EB"),
    INTERRUPTED("中断", "#13C2C2"),
    FAIL("失败", "#F5222D"),
    SUCCESS("成功", "#52C41A")


}