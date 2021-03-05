package mmsf_hub.sss_viewer.model

import enumeratum.{Enum, EnumEntry}

case class Server(
    id: Int,
    serverType: ServerType,
    level: Int,
    name: String,
    cardTable: CardTable,
    accessCode: AccessCode
)

sealed trait ServerType extends EnumEntry

object ServerType extends Enum[ServerType] {
  def values: IndexedSeq[ServerType] = findValues

  case object SatelliteServer extends ServerType
  case object MeteorServer    extends ServerType
}
