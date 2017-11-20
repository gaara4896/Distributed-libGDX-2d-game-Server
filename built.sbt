name := "Distributed-libGDX-2d-game-Server"

version := "1.0"

scalaVersion := "2.12.3"

scalacOptions ++= Seq("-unchecked", "-deprecation")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.6",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.6",
    "com.typesafe.akka" %% "akka-remote" % "2.5.6",
    "com.badlogicgames.gdx" % "gdx" % "1.9.6"
)

cancelable in Global := true

connectInput in run := true

resolvers ++= Seq(
      "Sonatype OSS Snapshots" at
        "https://oss.sonatype.org/content/repositories/snapshots",
      "Sonatype OSS Releases" at
        "https://oss.sonatype.org/content/repositories/releases",
      "Typesafe Repository" at
        "http://repo.typesafe.com/typesafe/releases/"
    )

fork := true

val %/ = if (java.io.File.pathSeparator == "\\") "\\\\" else java.io.File.pathSeparator

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
