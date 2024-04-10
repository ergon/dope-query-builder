pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        repositories {
            maven(url = "https://jitpack.io")
        }
    }
}

rootProject.name = "dope-query-builder"

include("core", "crystal-map-connector", "crystal-map-api")

//includeBuild("../crystal-map") {
//    dependencySubstitution {
//        substitute(module("com.github.SchwarzIT.crystal-map:crystal-map-api")).using(project(":crystal-map-api"))
//    }
//}
