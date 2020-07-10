addSbtPlugin("org.scalameta" % "sbt-scalafmt"        % "2.4.0")
addSbtPlugin("org.scala-js"  % "sbt-scalajs"         % "1.1.1")
addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.18.0")

libraryDependencies ++= Seq(
  "com.dorkbox" % "Notify" % "3.7"
)
