package mmsf_hub

import mmsf_hub.domain.BattleCard
import mmsf_hub.sss_viewer.components.MainComponent
import mmsf_hub.sss_viewer.model.{AccessCode, CardTable, Server, ServerType}
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, XMLHttpRequest}
import slinky.hot
import slinky.web.ReactDOM
import typings.csvParse.mod.Options
import typings.csvParse.{libSyncMod => parseCsv}
import typings.datadogBrowserRum.mod.datadogRum
import typings.datadogBrowserRum.rumEntryMod.RumUserConfiguration

import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.{js, LinkingInfo}

object Main {
  @JSExportTopLevel("main")
  def main(): Unit = {
    if (LinkingInfo.developmentMode) {
      hot.initialize()
    }

    if (LinkingInfo.productionMode) {
      datadogRum.init(
        RumUserConfiguration(
          applicationId = "419628fb-f24d-4492-a091-7508e63a6f72",
          clientToken = "pub4ac0dc52bfdd0cb78e53eba312251341"
        ).setTrackInteractions(true)
      )
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

  // TODO: Move to SSSViewer
  private def loadApp(xhr: XMLHttpRequest, container: Element): Unit = {
    val rawServers =
      parseCsv(
        xhr.responseText,
        js.Dynamic.literal(columns = true).asInstanceOf[Options]
      ).asInstanceOf[js.Array[js.Dictionary[String]]]

    val servers = rawServers.map { dict =>
      Server(
        id = dict("id").toInt,
        serverType = ServerType.values(dict("type").toInt),
        level = dict("level").toInt,
        name = dict("name"),
        cardTable = CardTable(parseCards(dict("cards"))),
        accessCode = AccessCode(dict("access_code"))
      )
    }.toSeq

    ReactDOM.render(MainComponent(servers), container)
  }

  private def parseCards(text: String): Vector[BattleCard] = {
    val cardNames = text.split(";")
    cardNames.map(name => BattleCard(1L, name)).toVector
  }
}
