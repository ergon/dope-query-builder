plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    `maven-publish`
    idea
}

group = "com.github.ergon"
version = findProperty("projectVersion")?.toString() ?: "unknown-version"

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
    implementation(project(":core"))
    testImplementation(kotlin("test"))
    testImplementation("com.couchbase.client:kotlin-client:1.5.0")
    testImplementation("org.testcontainers:couchbase:1.21.4")
    testImplementation("io.github.classgraph:classgraph:4.8.174")
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

sourceSets {
    create("integrationTest") {
        kotlin {
            srcDir("src/integrationTest/kotlin")
        }
        compileClasspath += sourceSets["main"].compileClasspath + sourceSets["test"].compileClasspath
        runtimeClasspath += sourceSets["main"].runtimeClasspath + sourceSets["test"].runtimeClasspath
    }
}

tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    mustRunAfter(tasks.named("test"))
    useJUnitPlatform()
}

idea.module {
    testSources.from(sourceSets["integrationTest"].kotlin.srcDirs)
}
