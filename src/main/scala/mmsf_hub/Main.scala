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

    ReactDOM.render(SSSViewer(genDummyServers()), container)
  }

  // TODO: Read and apply all server data
  private def genDummyServers(): Seq[Server] = {
    (1 to 56).map { serverLevel =>
      val table = CardTable(Vector.fill(35)(BattleCard(1L, s"キャノン$serverLevel")))
      Server(serverLevel, "オックス", table)
    }
  }
}
