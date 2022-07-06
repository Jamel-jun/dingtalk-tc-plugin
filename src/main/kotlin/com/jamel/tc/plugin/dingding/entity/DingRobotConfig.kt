package com.jamel.tc.plugin.dingding.entity

data class DingRobotConfig(
    val dingNoticeEnabled: Boolean = false,
    val accessToken: String,
    val sign: String? = null,
    val ipAddressEnable: Boolean? = false,
    val keyword: String? = null,
) {

}
