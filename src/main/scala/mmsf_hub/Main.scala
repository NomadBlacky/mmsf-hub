package mmsf_hub

import mmsf_hub.common.model.BattleCard
import mmsf_hub.sss_viewer.SSSViewer
import mmsf_hub.sss_viewer.model.{CardTable, Server}
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

    // TODO: Read and apply all server data
    val table   = CardTable(Vector.fill(35)(BattleCard(1L, "キャノン")))
    val servers = Seq.fill(56)(Server(1, "オックス", table))

    ReactDOM.render(SSSViewer(servers), container)
  }
}
