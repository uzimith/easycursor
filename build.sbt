ThisBuild / ideaPluginName := "easycursor"
ThisBuild / ideaEdition := IdeaEdition.Community
ThisBuild / ideaBuild := "183.4284.36"

lazy val easycursor = (project in file("."))
  .enablePlugins(SbtIdeaPlugin)
  .settings(
    organization := "net.uzimith",
    scalaVersion := "2.12.7",
    scalacOptions ++= Seq("-unchecked",
                          "-deprecation",
                          "-feature",
                          "-language:implicitConversions",
                          "-Ywarn-unused:imports"),
    Compile / console / scalacOptions -= "-Ywarn-unused:imports",
    Global / onLoad ~= { _.andThen("updateIdea" :: _) },
    assembly / assemblyExcludedJars ++= ideaFullJars.value,
    assembly / assemblyOption ~= { _.copy(includeScala = false) },
    ideaExternalPlugins += IdeaPlugin
      .Zip("scala-plugin", url("https://plugins.jetbrains.com/plugin/download?rel=true&updateId=45268"))
  )

lazy val ideaRunner = (project in file("target/tools"))
  .settings(
    scalaVersion := "2.12.7",
    autoScalaLibrary := false,
    unmanagedJars in Compile := ideaMainJars.value,
    unmanagedJars in Compile += file(System.getProperty("java.home")).getParentFile / "lib" / "tools.jar",
    Compile / compile := ((Compile / compile) dependsOn (easycursor / assembly)).value,
    Compile / run / mainClass := Some("com.intellij.idea.Main"),
    run / javaOptions := Seq(
      "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005",
      s"-Didea.home=${ideaBaseDirectory.value.getPath}",
      s"-Didea.system.path=${ideaTestSystemDir.value}",
      s"-Didea.config.path=${ideaTestConfigDir.value}",
      s"-Dplugin.path=${(easycursor / assembly / assemblyOutputPath).value}",
    ),
    run / fork := true
  )

easycursor / packagePluginZip := {
  val pluginJar = (easycursor / assembly).value
  val sources = Seq(pluginJar -> s"${(easycursor / ideaPluginName).value}/lib/${pluginJar.getName}")
  val out = (easycursor / target).value / s"${(easycursor / ideaPluginName).value}.zip"
  IO.zip(sources, out)
  out
}
