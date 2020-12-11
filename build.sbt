ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "com.lihaoyi"
ThisBuild / Compile / scalacOptions ++= Seq("-feature", "-deprecation")
ThisBuild / libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.5" % Test
ThisBuild / testFrameworks := Seq(new TestFramework("utest.runner.Framework"))

lazy val root = project.in(file("."))
  .aggregate(core, implicits, upack, upickle, ujson, ujsonTest, ujsonArgonaut, ujsonCirce, ujsonJson4s, ujsonPlay)
  .settings(
    publish / skip := true,
  )

val core = (project in file("core")).settings(
  name := "upickle-core",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src-jvm",
  Test / unmanagedSourceDirectories += baseDirectory.value / "test" / "src",
)

val implicits = (project in file("implicits")).settings(
  name := "upickle-implicits",
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.4",
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.2.0",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src-2",
  Test / unmanagedSourceDirectories += baseDirectory.value / "test" / "src",
).dependsOn(core)

val ujson = (project in file("ujson")).settings(
  name := "ujson",
  libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.4",
  libraryDependencies += "com.lihaoyi" %% "geny" % "0.6.2",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
).dependsOn(core)

val ujsonArgonaut = (project in file("ujson/argonaut")).settings(
  name := "ujson-argonaut",
  libraryDependencies += "io.argonaut" %% "argonaut" % "6.2.3",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
).dependsOn(ujson)

val ujsonCirce = (project in file("ujson/circe")).settings(
  name := "ujson-circe",
  libraryDependencies += "io.circe" %% "circe-parser" % "0.13.0",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
).dependsOn(ujson)

val ujsonJson4s = (project in file("ujson/json4s")).settings(
  name := "ujson-json4s",
  libraryDependencies += "org.json4s" %% "json4s-ast" % "3.6.7",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
).dependsOn(ujson)

val ujsonPlay = (project in file("ujson/play")).settings(
  name := "ujson-play",
  libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.4",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
).dependsOn(ujson)

val ujsonTest = (project in file("ujson/test")).settings(
  name := "ujson",
  libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.4",
  libraryDependencies += "com.lihaoyi" %% "geny" % "0.6.2",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test,
  libraryDependencies += "org.scalatestplus" %% "scalacheck-1-14" % "3.1.1.1" % Test,
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
  Test / unmanagedSourceDirectories += baseDirectory.value / "src",
  Test / unmanagedSourceDirectories += baseDirectory.value / "src-2",
  Test / unmanagedSourceDirectories += baseDirectory.value / "src-2-jvm",
).dependsOn(ujson)

val upack = (project in file("upack")).settings(
  name := "upickle-upack",
  libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.4",
  libraryDependencies += "com.lihaoyi" %% "geny" % "0.6.2",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
  Test / unmanagedSourceDirectories += baseDirectory.value / "test" / "src",
  Test / unmanagedSourceDirectories += baseDirectory.value / "test" / "src-jvm",
).dependsOn(ujson)

val upickle = (project in file("upickle")).settings(
  name := "upickle",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src",
  Compile / unmanagedSourceDirectories += baseDirectory.value / "src-jvm",
  Test / unmanagedSourceDirectories += baseDirectory.value / "test" / "src",
  Test / unmanagedSourceDirectories += baseDirectory.value / "test" / "src-2",
  Test / unmanagedSourceDirectories += baseDirectory.value / "test" / "src-jvm-2",
).dependsOn(ujsonArgonaut, ujsonCirce, ujsonJson4s, ujsonPlay, upack, implicits)
