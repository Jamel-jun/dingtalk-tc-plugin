package com.jamel.tc.plugin.dingding.page

import com.jamel.tc.plugin.dingding.DingRobotConfigStore
import jetbrains.buildServer.serverSide.ProjectManager
import jetbrains.buildServer.serverSide.SBuildType
import jetbrains.buildServer.users.SUser
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.WebControllerManager
import jetbrains.buildServer.web.openapi.buildType.BuildTypeTab
import javax.servlet.http.HttpServletRequest

/**
 *
 * @since 2022/07/07 10:56
 * @author gaojun
 **/
class DingBuildTypeTab(private val dingRobotConfigStore: DingRobotConfigStore, webControllerManager: WebControllerManager, descriptor: PluginDescriptor, projectManager: ProjectManager) :
    BuildTypeTab("ding", "钉钉机器人通知", webControllerManager, projectManager, descriptor.getPluginResourcesPath("ding-edit-project-tab.jsp")) {

    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest, buildType: SBuildType, user: SUser?) {
        super.fillModel(model, request)
    }


}