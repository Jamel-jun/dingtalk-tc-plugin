package com.jamel.tc.plugin.dingding.page

import cn.hutool.json.JSONObject
import com.jamel.tc.plugin.dingding.DingRobotConfigStore
import jetbrains.buildServer.controllers.admin.projects.EditProjectTab
import jetbrains.buildServer.serverSide.ProjectManager
import jetbrains.buildServer.serverSide.SProject
import jetbrains.buildServer.users.SUser
import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.project.ProjectTab
import javax.servlet.http.HttpServletRequest

/**
 *
 * @since 2022/07/07 10:56
 * @author gaojun
 **/
class DingProjectTab(private val dingRobotConfigStore: DingRobotConfigStore, pagePlaces: PagePlaces, projectManager: ProjectManager, descriptor: PluginDescriptor) :
    EditProjectTab(pagePlaces, "ding", descriptor.getPluginResourcesPath("ding-edit-project-tab.jsp"), "钉钉机器人通知") {

//    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest, project: SProject, user: SUser?) {
//        val dingRobotConfig = dingRobotConfigStore.findSystem()
//        model["dingRobotConfig"] = JSONObject(dingRobotConfig).toBean(Map::class.java)
//        super.fillModel(model, request)
//    }

    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest) {
        super.fillModel(model, request)
    }


}