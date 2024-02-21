plugins {
    kotlin("jvm") version "1.9.20"
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
    implementation("com.github.SchwarzIT.crystal-map:crystal-map-api:3.16.0")
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    implementation("com.couchbase.client:java-client:3.4.9")
    testImplementation("org.testcontainers:couchbase:1.19.0")
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("com.squareup:kotlinpoet:1.14.2")
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
