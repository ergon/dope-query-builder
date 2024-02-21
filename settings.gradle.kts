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

include(":src")
