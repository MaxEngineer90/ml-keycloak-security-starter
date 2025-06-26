plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    `maven-publish`
    id("pl.allegro.tech.build.axion-release") version "1.18.11"
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

scmVersion {
    tag {
        prefix.set("v")
        versionSeparator.set("")
    }
    
    nextVersion {
        suffix.set("SNAPSHOT")
        separator.set("-")
    }
    
    versionIncrementer("incrementPatch")
    
    repository {
        type.set("git")
    }
    
    branchVersionIncrementer = mapOf(
        "feature/.*" to "incrementMinor",
        "fix/.*" to "incrementPatch",
        "develop" to "incrementMinor"
    )
    
    // SNAPSHOT für alle Branches außer main
    snapshotCreator { version, position ->
        if (position.branch != "main") {
            "${version.toString()}-SNAPSHOT"
        } else {
            version.toString()
        }
    }
}

version = scmVersion.version

tasks.test {
    useJUnitPlatform()
}

tasks.named("release") {
    dependsOn("test", "build")
}

tasks.matching { it.name == "verifyRelease" }.configureEach {
    enabled = false
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
        register<MavenPublication>("gpr") {
            from(components["java"])
            
            pom {
                name.set("ML Keycloak Security Starter")
                description.set("Keycloak Spring Boot Security Integration")
                url.set("https://github.com/MaxEngineer90/ml-keycloak-security-starter")
                
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                
                developers {
                    developer {
                        id.set("MaxEngineer90")
                        name.set("Max Engineer")
                        email.set("max@example.com")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/MaxEngineer90/ml-keycloak-security-starter.git")
                    developerConnection.set("scm:git:ssh://github.com:MaxEngineer90/ml-keycloak-security-starter.git")
                    url.set("https://github.com/MaxEngineer90/ml-keycloak-security-starter")
                }
            }
        }
    }
}