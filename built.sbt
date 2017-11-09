name := "Distributed-libGDX-2d-game-Server"

version := "1.0"

scalaVersion := "2.12.3"

scalacOptions ++= Seq("-unchecked", "-deprecation")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.6",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.6",
    "com.typesafe.akka" %% "akka-remote" % "2.5.6",
    "org.scalafx" %% "scalafx" % "8.0.102-R11",
	"org.scalafx" %% "scalafxml-core-sfx8" % "0.4"
)

cancelable in Global := true

connectInput in run := true

fork := true