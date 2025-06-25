plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("fr.brouillard.oss.gradle.jgitver") version "0.10.0-rc03"
}

group = "io.github.maxengineer90"
description = "Keycloak Spring Boot Security Integration"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
    withJavadocJar()
}

jgitver {
    strategy("MAVEN")
    nonQualifierBranches("main")
    versionPattern("\${M}.\${m}.\${p}")
    tagVersionPattern("\${v}")
    regexVersionTag("([0-9]+)\\.([0-9]+)\\.([0-9]+).*")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("showVersion") {
    doLast {
        println("=".repeat(50))
        println("Current branch: ${getCurrentBranch()}")
        println("Current version: ${project.version}")
        println("Is SNAPSHOT: ${version.toString().contains("SNAPSHOT")}")
        println("=".repeat(50))
    }
}

fun getCurrentBranch(): String {
    return try {
        val process = ProcessBuilder("git", "branch", "--show-current")
            .directory(project.rootDir)
            .start()
        process.inputStream.bufferedReader().readText().trim()
    } catch (e: Exception) {
        "unknown"
    }
}