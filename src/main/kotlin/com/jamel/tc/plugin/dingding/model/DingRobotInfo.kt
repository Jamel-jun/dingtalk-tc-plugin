package com.jamel.tc.plugin.dingding.model

import java.util.Date


class DingRobotInfo(
    val buildNumber: String,
    val buildName: String,
    val buildTypeId: String,
    val status: BuildStatus,
    val userName: String = "",
    val branch: String = "",
    serverUrl: String,
    buildId: Long,
    createDate: Date? = null,
    val duration: String = computeDuration(createDate),
    val projectUrl: String = "${serverUrl}/admin/editBuild.html?id=buildType:$buildTypeId",
    val taskUrl: String = "${serverUrl}/buildConfiguration/$buildTypeId/$buildId?hideProblemsFromDependencies=false&hideTestsFromDependencies=false",
) {

    companion object {
        private fun computeDuration(startTime: Date?): String {
            startTime ?: return ""
            val duration = (System.currentTimeMillis() / 1000) - (startTime.time / 1000)
            return if (duration < 60) {
                "$duration sec "
            } else if (duration in 61..3599) {
                val m = duration / 60
                val s = duration % 60
                "$m sec $s sec "
            } else {
                val h = duration / 3600
                val m = duration % 3600 / 60
                val s = duration % 3600 % 60
                if (h != 0L) "$h hour $m min $s sec " else "$m min $s sec "
            }
        }
    }
}
