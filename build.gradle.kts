plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    `maven-publish`
}

group = "io.github.maxengineer90"
description = "Keycloak Spring Boot Security Integration"

val baseVersion = "0.2.0"
val isRelease = System.getenv("RELEASE") == "true" || project.hasProperty("release")

version = if (isRelease) {
    baseVersion
} else {
    "$baseVersion-SNAPSHOT"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MaxEngineer90/ml-keycloak-security-starter")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            
            suppressAllPomMetadataWarnings()
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("showVersion") {
    doLast {
        println("Current version: $version")
        println("Is release: $isRelease")
    }
}