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

include("core", "crystal-map-connector", "crystal-map-processor", "crystal-map-api")

includeBuild("../crystal-map") {
    dependencySubstitution {
        substitute(module("com.github.SchwarzIT.crystal-map:crystal-map-versioning-plugin")).using(project(":crystal-map-versioning-plugin"))
        substitute(module("com.github.SchwarzIT.crystal-map:crystal-map-processor")).using(project(":crystal-map-processor"))
        substitute(module("com.github.SchwarzIT.crystal-map:crystal-map-api")).using(project(":crystal-map-api"))
    }
}
