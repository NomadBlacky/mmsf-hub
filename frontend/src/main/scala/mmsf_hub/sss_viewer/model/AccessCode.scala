package mmsf_hub.sss_viewer.model

case class AccessCode(commands: String) {
  require(commands.length == 6)
  private val commandsWithOrder: Seq[(Char, Int)] = commands.zipWithIndex.map { case (k, i) => (k, i + 1) }

  def orderOf(command: Char): Seq[Int] = {
    commandsWithOrder.collect { case (c, o) if command == c => o }
  }
}
