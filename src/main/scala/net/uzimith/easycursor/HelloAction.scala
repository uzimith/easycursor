package net.uzimith.easycursor

import com.intellij.notification.{Notification, NotificationType, Notifications}
import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.diagnostic.Logger

class HelloAction extends AnAction {
  def actionPerformed(event: AnActionEvent) {
    val logger = Logger.getInstance(classOf[HelloAction])

    logger.debug("debug: action: hello action")
    logger.info("action: hello action")

    val config = PluginConfig.getInstance(event.getProject)

    val count = config.count

    Notifications.Bus.notify(
      new Notification("sample",
                       "Hello Plugin!",
                       s"Hello this is sample count:$count",
                       NotificationType.INFORMATION)
    )
    config.incriment()
  }
}
