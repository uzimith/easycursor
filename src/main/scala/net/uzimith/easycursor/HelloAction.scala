package net.uzimith.easycursor

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.diagnostic.Logger

class HelloAction extends AnAction {
  def actionPerformed(event: AnActionEvent) {
    val logger = Logger.getInstance(classOf[HelloAction])

    logger.debug("debug: action: hello action")
    logger.info("action: hello action")
  }
}
