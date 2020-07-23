package mmsf_hub

import mmsf_hub.common.model.BattleCard
import mmsf_hub.sss_viewer.SSSViewer
import mmsf_hub.sss_viewer.model.{CardTable, Server, ServerType}
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, XMLHttpRequest}
import slinky.hot
import slinky.web.ReactDOM
import typings.csvParse.mod.Options
import typings.csvParse.{libSyncMod => parseCsv}

import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.{js, LinkingInfo}

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

    val xhr = new XMLHttpRequest()

    xhr.open("GET", "data/servers.csv")
    xhr.onload = { e =>
      if (xhr.status == 200) {
        loadApp(xhr, container)
      }
    }
    xhr.send()
  }

  private def loadApp(xhr: XMLHttpRequest, container: Element): Unit = {
    val rawServers =
      parseCsv(
        xhr.responseText,
        js.Dynamic.literal(columns = true).asInstanceOf[Options]
      ).asInstanceOf[js.Array[js.Dictionary[String]]]

    println(rawServers)

    val servers = rawServers.map { dict =>
      println(dict.keys)
      println(dict.values)
      Server(
        id = dict("id").toInt,
        serverType = ServerType.values(dict("type").toInt),
        level = dict("level").toInt,
        name = dict("name"),
        cardTable = CardTable(parseCards(dict("cards")))
      )
    }.toSeq

    ReactDOM.render(SSSViewer(servers), container)
  }

  private def parseCards(text: String): Vector[BattleCard] = {
    val cardNames = text.split(";")
    cardNames.map(name => BattleCard(1L, name)).toVector
  }
}
