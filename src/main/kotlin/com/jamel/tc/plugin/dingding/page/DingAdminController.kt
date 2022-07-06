package com.jamel.tc.plugin.dingding.page

import cn.hutool.extra.servlet.ServletUtil
import cn.hutool.json.JSONObject
import com.jamel.tc.plugin.dingding.DingRobotConfigStore
import com.jamel.tc.plugin.dingding.entity.DingRobotConfig
import jetbrains.buildServer.agent.impl.ServerLogger
import jetbrains.buildServer.controllers.BaseFormXmlController
import jetbrains.buildServer.controllers.XmlResponseUtil
import jetbrains.buildServer.log.Loggers
import jetbrains.buildServer.serverSide.SBuildServer
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.jdom.Element
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 *
 * @since 2022/05/14 13:56
 * @author gaojun
 **/
class DingAdminController(server: SBuildServer, webControllerManager: WebControllerManager, private val dingRobotConfigStore: DingRobotConfigStore) : BaseFormXmlController(server) {
    init {
        webControllerManager.registerController("/app/dingding", this)
    }

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        return ModelAndView("ding-edit-project-tab.jsp")
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse, xmlResponse: Element) {
        val paramMap = ServletUtil.getParamMap(request)
        val json = JSONObject(paramMap)

        dingRobotConfigStore.store(DingRobotConfig(json.getBool("dingNoticeEnabled") ?: false, json.getStr("accessToken"), json.getStr("sign"), json.getBool("ipAddressEnable") ?: false, json.getStr("keyword")))
    }
}