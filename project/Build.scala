import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
  val appName         = "icm"
  val appVersion      = "0.9"

  val appDependencies = Seq(
    "de.undercouch" % "bson4jackson" % "2.2.0",
    "commons-io" % "commons-io" % "2.4",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.2.3",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.2.3",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.3",
    "org.mongodb" % "mongo-java-driver" % "2.11.2",
    "org.mongojack" % "mongojack" % "2.0.0-RC5",
    "org.jasypt" % "jasypt" % "1.9.1",
    javaCore
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
