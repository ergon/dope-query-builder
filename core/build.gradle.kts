plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    `maven-publish`
}

group = "com.github.ergon"
version = "0.0.4"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://jitpack.io")
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
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
