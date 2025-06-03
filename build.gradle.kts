plugins {
    `java-library`
    `maven-publish`
    id("pl.allegro.tech.build.axion-release") version "1.15.2"
}

group = "io.github.maxengineer90"
description = "Keycloak Spring Boot Security Integration"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot BOM verwaltet alle Spring Dependencies automatisch
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.5"))
    
    // Spring Boot Dependencies (über Version Catalog)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.autoconfigure)
    compileOnly(libs.spring.boot.configuration.processor)
    
    // Lombok (über Version Catalog)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    
    // Test Dependencies (über Version Catalog)
    testImplementation(libs.spring.boot.starter.test) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

scmVersion {
    tag {
        prefix.set("")
    }
    repository {
        type.set("git")
    }
}

version = scmVersion.version

tasks.named("release") {
    dependsOn("test")
}

tasks.matching { it.name == "verifyRelease" }.configureEach {
    enabled = false
}

// Maven Publishing Configuration (GitHub Official)
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