plugins {
    kotlin("jvm") version "1.9.20"
}

group = "ch.ergon"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    repositories {
        maven(url = "https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.SchwarzIT.crystal-map:crystal-map-api:3.17.0")
    implementation(kotlin("reflect"))
    implementation(project(":core"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
