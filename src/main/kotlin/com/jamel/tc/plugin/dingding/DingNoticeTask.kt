package com.jamel.tc.plugin.dingding

import cn.hutool.core.codec.Base64
import cn.hutool.core.util.URLUtil
import cn.hutool.crypto.digest.HMac
import cn.hutool.crypto.digest.HmacAlgorithm
import com.dingtalk.api.DefaultDingTalkClient
import com.dingtalk.api.request.OapiRobotSendRequest
import com.jamel.tc.plugin.dingding.entity.DingRobotConfig
import com.jamel.tc.plugin.dingding.entity.DingRobotInfo
import java.lang.RuntimeException


/**
 *
 * @since 2021/07/11 00:53
 * @author gaojun
 **/
class DingNoticeTask(val config: DingRobotConfig) {

    fun send(robotInfo: DingRobotInfo) {
        if (!config.dingNoticeEnabled) return
        val mdStr = """
            [${robotInfo.buildName}](${robotInfo.projectUrl})  
            
            ---
            - 任务：[#${robotInfo.buildNumber}](${robotInfo.taskUrl})
            - 状态：<font color="${robotInfo.status.color}">${robotInfo.status.status}</font>
            - 持续时间：${robotInfo.duration}
            - 执行人：Started by GitLab push by ${robotInfo.userName}
            - 构建分支: ${robotInfo.branch}${"\n"}
        """.trimIndent()

        val request = OapiRobotSendRequest().apply {
            msgtype = "actionCard"
            setActionCard(OapiRobotSendRequest.Actioncard().apply {
                title = "Teamcity通知！"
                this.text = mdStr
                hideAvatar = "0"
                btnOrientation = "1"
                btns = listOf(OapiRobotSendRequest.Btns().apply {
                    title = "控制台"
                    actionURL = robotInfo.taskUrl
                })
            })
            setAt(OapiRobotSendRequest.At().apply {
                atMobiles = listOf("18974073482")
                isAtAll = false
            })
        }

        val timestamp = System.currentTimeMillis()
        val signData = HMac(HmacAlgorithm.HmacSHA256, config.sign!!.toByteArray()).digest("$timestamp\n${config.sign}")
        val sign = URLUtil.encode(Base64.encode(signData))
        val client = DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=${config.accessToken}&timestamp=$timestamp&sign=$sign")
        val response = client.execute(request)
        if (!response.isSuccess) {
            throw RuntimeException(response.errmsg)
//            when {
//                response.errmsg.contains("keywords not in content") -> throw Exception("消息内容中不包含任何关键词")
//                response.errmsg.contains("invalid timestamp") -> throw Exception("timestamp 无效")
//                response.errmsg.contains("sign not matc") -> throw Exception("签名不匹配")
//                response.errmsg.contains("not in whitelist") -> throw Exception("IP地址不在白名单")
//            }
        }
    }
}