package com.jamel.tc.plugin.dingding.page

import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import com.jamel.tc.plugin.dingding.DingRobotConfigStore
import jetbrains.buildServer.controllers.admin.AdminPage
import jetbrains.buildServer.log.Loggers
import jetbrains.buildServer.serverSide.auth.Permission
import jetbrains.buildServer.web.openapi.*
import javax.servlet.http.HttpServletRequest

class DingAdminPage(pagePlaces: PagePlaces, descriptor: PluginDescriptor, private val dingRobotConfigStore: DingRobotConfigStore) : AdminPage(pagePlaces) {

    init {
        pluginName = "ding-plugin-name";
        includeUrl = descriptor.getPluginResourcesPath("ding-edit-project-tab.jsp");
        tabTitle = "钉钉机器人通知设置"
        register()
    }


    override fun isAvailable(request: HttpServletRequest): Boolean {
        return super.isAvailable(request) && checkHasGlobalPermission(request, Permission.CHANGE_SERVER_SETTINGS)
    }

    override fun getGroup(): String {
        return INTEGRATIONS_GROUP
    }

    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest) {
        val dingRobotConfig = dingRobotConfigStore.findSystem()
        model["dingRobotConfig"] = JSONObject(dingRobotConfig).toBean(Map::class.java)
        super.fillModel(model, request)
    }


}