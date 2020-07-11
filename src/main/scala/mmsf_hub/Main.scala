package mmsf_hub

import mmsf_hub.sss_viewer.SSSViewer
import org.scalajs.dom
import slinky.hot
import slinky.web.ReactDOM

import scala.scalajs.LinkingInfo
import scala.scalajs.js.annotation.JSExportTopLevel

object Main {
  @JSExportTopLevel("main")
  def main(): Unit = {
    if (LinkingInfo.developmentMode) {
      hot.initialize()
    }

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    ReactDOM.render(SSSViewer(), container)
  }
}
