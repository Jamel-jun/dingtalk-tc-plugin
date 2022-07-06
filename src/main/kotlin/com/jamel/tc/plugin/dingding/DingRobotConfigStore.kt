package com.jamel.tc.plugin.dingding

import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.IoUtil
import cn.hutool.core.util.SystemPropsUtil.getProps
import cn.hutool.setting.dialect.Props
import com.jamel.tc.plugin.dingding.entity.DingRobotConfig
import jetbrains.buildServer.serverSide.SProject
import jetbrains.buildServer.serverSide.SProjectFeatureDescriptor
import jetbrains.buildServer.serverSide.ServerPaths
import org.apache.logging.log4j.core.impl.ThrowableFormatOptions.FILE_NAME

/**
 * Config storage functions that allows finding project hierarchy dependent
 * configuration, or storing configuration on projects (as project features).
 */
class DingRobotConfigStore(private val serverPaths: ServerPaths) {

  init {
    FileUtil.touch(getFilePath())
  }

  private fun getFilePath(): String = serverPaths.configDir + FILE_NAME
  companion object {
    private const val type = "ding"

    private const val FILE_NAME = "ding.properties"
  }

  /** The config found at this level or first one up the hierarchy. */
  fun findAvailable(project: SProject): DingRobotConfig {
    return find(project.getAvailableFeaturesOfType(type))
  }

  /** The config found at this level only. */
  fun findOwn(project: SProject): DingRobotConfig {
    return find(project.getOwnFeaturesOfType(type))
  }

  private fun find(features: Collection<SProjectFeatureDescriptor>): DingRobotConfig {
    TODO()
//    return features.stream()
//          .map { DingRobotConfig.fromMap(it.parameters) }
//          .filter { it.isEnabled() }
//          .findFirst()
//          .orElse(DingRobotConfig.disabled)
  }

  fun findSystem(): DingRobotConfig {
    val props = Props(getFilePath())
    return DingRobotConfig(props.getBool("dingNoticeEnabled"), props.getStr("accessToken"), props.getStr("sign"), props.getBool("ipAddressEnable"), props.getStr("keyword"))
  }

  fun store(config: DingRobotConfig) {
    val props = Props().apply {
      put("dingNoticeEnabled", config.dingNoticeEnabled.toString())
      put("accessToken", config.accessToken)
      put("sign", config.sign)
      config.ipAddressEnable?.run { put("ipAddressEnable", this.toString()) }
      config.keyword?.run { put("keyword", this) }
    }
    props.store(getFilePath())
  }

  fun store(project: SProject, config: DingRobotConfig) {
//    val props = getProps()
//    props["username"] = username
//    props.store(serverPaths.configDir + "ding.properties")
//    TODO()
//    val features = project.getOwnFeaturesOfType(type)
//
//    val shouldAdd = features.isEmpty() && config.isEnabled()
//    val shouldUpdate = !features.isEmpty() && config.isEnabled()
//    val shouldRemove = !features.isEmpty() && !config.isEnabled()
//
//    when {
//      shouldAdd -> add(config, project)
//      shouldUpdate -> update(config, project, features.first())
//      shouldRemove -> remove(project, features.first())
//    }
  }

  private fun add(config: DingRobotConfig, project: SProject) {
//    project.addFeature(type, config.toMap())
//    project.persist()
  }

  private fun update(config: DingRobotConfig, project: SProject, feature: SProjectFeatureDescriptor) {
//    project.updateFeature(feature.id, type, config.toMap())
//    project.persist()
  }

  private fun remove(project: SProject, feature: SProjectFeatureDescriptor) {
    project.removeFeature(feature.id)
    project.persist()
  }

}
