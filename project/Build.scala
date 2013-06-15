import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "icm"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "net.vz.mongodb.jackson" %% "play-mongo-jackson-mapper" % "1.1.0",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
