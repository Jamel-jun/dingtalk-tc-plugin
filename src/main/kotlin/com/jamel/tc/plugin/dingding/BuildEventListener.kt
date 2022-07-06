package com.jamel.tc.plugin.dingding

import com.jamel.tc.plugin.dingding.entity.BuildStatus
import com.jamel.tc.plugin.dingding.entity.DingRobotConfig
import com.jamel.tc.plugin.dingding.entity.DingRobotInfo
import jetbrains.buildServer.serverSide.*
import jetbrains.buildServer.util.EventDispatcher
import java.util.*

/**
 * 构建队列监听器
 * @since 2021/07/11 01:33
 * @author gaojun
 **/
class BuildEventListener(eventDispatcher: EventDispatcher<BuildServerListener>, private val server: SBuildServer, private val configStore: DingRobotConfigStore): BuildServerAdapter() {

    init {
        eventDispatcher.addListener(this)
    }

    override fun buildTypeAddedToQueue(build: SQueuedBuild) {
        val username = getVcsRootProperty(build.buildType, "username") ?: ""
        val branch = getVcsRootProperty(build.buildType, "branch") ?: ""
        DingNoticeTask(configStore.findSystem()).send(
            DingRobotInfo(
                buildName = build.buildType.projectName,
                buildTypeId = build.buildType.externalId,
                serverUrl = server.rootUrl,
                buildNumber = build.buildTypeId,
                buildId = build.buildType.buildNumbers.buildCounter,
                status = BuildStatus.TO_QUEUE,
                userName = username,
                branch = branch,
            )
        )
    }

    override fun buildStarted(build: SRunningBuild) {
        build.buildType?.let {
            dingNotify(build, it.project, BuildStatus.START, build.queuedDate)
        }
    }

    override fun buildFinished(build: SRunningBuild) {
        build.buildType?.let {
            dingNotify(build, it.project, if (build.buildStatus.isSuccessful) BuildStatus.SUCCESS else BuildStatus.FAIL, build.clientStartDate)
        }
    }

    override fun buildInterrupted(build: SRunningBuild) {
        build.buildType?.let {
            dingNotify(build, it.project, BuildStatus.INTERRUPTED, build.clientStartDate)
        }
    }

    private fun dingNotify(build: SRunningBuild, project: SProject, status: BuildStatus, duration: Date?) {
        val username = getVcsRootProperty(build, "username") ?: ""
        val branch = getVcsRootProperty(build, "branch") ?: ""
        try {
            DingNoticeTask(configStore.findSystem()).send(
                DingRobotInfo(
                    buildName = project.name,
                    buildTypeId = build.buildTypeExternalId,
                    serverUrl = server.rootUrl,
                    buildNumber = build.buildNumber,
                    buildId = build.buildId,
                    status = status,
                    createDate = duration,
                    userName = username,
                    branch = branch,
                )
            )
        } catch (e: Exception) {
            build.buildLog.error("发送钉钉通知失败", e.message, null, null, null, null)
        }
    }

    private fun getVcsRootProperty(build: SBuildType, propertyName: String): String? = build.vcsRootEntries.firstOrNull()?.vcsRoot?.getProperty(propertyName)
    private fun getVcsRootProperty(build: SRunningBuild, propertyName: String): String? = build.vcsRootEntries.firstOrNull()?.vcsRoot?.getProperty(propertyName)

}