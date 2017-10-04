package net.uzimith.easycursor

import com.intellij.openapi.components._
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
  name = "EasyCursorConfig",
  reloadable = true,
  storages = Array(
    new Storage(StoragePathMacros.WORKSPACE_FILE),
    new Storage("easycursor.xml")
  )
)
class PluginConfig extends PersistentStateComponent[PluginConfig] {
  var count: Integer = 0

  override def getState: PluginConfig = this

  override def loadState(t: PluginConfig): Unit =
    XmlSerializerUtil.copyBean(t, this)

  def incriment(): Unit = {
    this.count = this.count + 1
  }

  def init(): Unit = {
    this.count = 0
  }
}

object PluginConfig {
  def getInstance(project: Project): PluginConfig =
    ServiceManager.getService(project, classOf[PluginConfig])
}
