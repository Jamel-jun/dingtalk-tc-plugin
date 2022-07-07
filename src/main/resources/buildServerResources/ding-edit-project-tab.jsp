<%@ page pageEncoding="UTF-8"%>
<%@ include file="/include.jsp"%>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<c:url value="/app/dingding" var="actionUrl" />


<bs:linkScript>
    ${teamcityPluginResourcesPath}js/ding-edit-project-config.js
</bs:linkScript>

<form id="deployDashboardForm" method="post" action="${actionUrl}" onsubmit="return BS.ServerConfigForm.submitSettings()  saveDeploymentDashboardConfig(this, '${actionUrl}')">

    <div class="editDeploySettingsPage">
        <p>
            <forms:checkbox name="dingNoticeEnabled" checked="${dingRobotConfig.dingNoticeEnabled}" onclick="$('dingNoticeConfig').hidden = !this.checked"/>
            <label>启用钉钉机器人插件</label>
        </p>

        <table id="dingNoticeConfig" class="runnerFormTable" ${dingRobotConfig.dingNoticeEnabled ? '' : 'hidden'}>
            <tr>
                <th class="noBorder">
                    <label>钉钉机器人access_token:<span class="mandatoryAsterix" title="Mandatory field">*</span></label>
                </th>
                <td class="noBorder">
                    <label><input type="text" class="textField" name="accessToken" maxlength="300" value="${dingRobotConfig.accessToken}"></label>
                </td>
            </tr>

            <tr class="groupingTitle">
                <td>安全参数设置</td>
                <td><span id="errorSpanSafeParams" hidden class="error">输入有误，三者需选其一</span></td>
            </tr>

            <tr>
                <th><label>加签:</label></th>
                <td>
                    <label><input type="text" class="textField" maxlength="100" name="sign" placeholder="请输入加密签名" value="${dingRobotConfig.sign}"></label>
                </td>
            </tr>

            <tr>
                <th><label>启用IP地址 (段)安全认证</label></th>
                <td>
                    <forms:checkbox name="dingNoticeEnabled" checked="${dingNoticeEnabled}" onclick="$('dingNoticeConfig').hidden = !this.checked"/>
                </td>
            </tr>
        </table>

        <div class="saveButtonsBlock">
            <forms:submit label="Save"/>
            <input type="hidden" id="projectExternalId" name="projectExternalId" value="${projectExternalId}"/>
            <forms:saving/>
        </div>
    </div>
</form>
