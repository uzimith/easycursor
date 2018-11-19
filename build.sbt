ThisBuild / ideaPluginName := "easycursor"
ThisBuild / ideaEdition := IdeaEdition.Community
ThisBuild / ideaBuild := "183.4284.93"

lazy val easycursor = (project in file("."))
  .settings(
    organization := "net.uzimith",
    scalaVersion := "2.12.7",
    packageMethod := PackagingMethod.Standalone(),
    packageLibraryMappings := Seq.empty
  )

lazy val ideaRunner = createRunnerProject(easycursor, "ideaRunner")
