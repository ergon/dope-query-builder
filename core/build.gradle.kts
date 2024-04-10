plugins {
    kotlin("jvm") version "1.9.22"
    application
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
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
    testImplementation(kotlin("test"))
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    implementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<GradleBuild>("cleanBuildFormatTest") {
    tasks = listOf("clean", "ktlintFormat", "build", "test")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }
        jvmToolchain {
            this.languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

application {
    mainClass.set("MainKt")
}
