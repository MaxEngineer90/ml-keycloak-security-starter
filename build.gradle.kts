plugins {
    `java-library`
    `maven-publish`
    id("pl.allegro.tech.build.axion-release") version "1.15.2"
}

group = "io.github.maxlamm"
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

// Maven Publishing Configuration
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/maxlamm/ml-keycloak-security-starter")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GH_TOKEN")
            }
        }
    }
}