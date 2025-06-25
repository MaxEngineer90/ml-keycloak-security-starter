plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    `maven-publish` 
    id("fr.brouillard.oss.gradle.jgitver") version "0.10.0-rc03"
}

group = "io.github.maxengineer90"
description = "Keycloak Spring Boot Security Integration"




repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.6"))
    
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.spring.boot.starter.oauth2.resource.server)
    implementation(libs.spring.security.oauth2.jose)
    implementation(libs.jackson.databind)
    
    compileOnly(libs.spring.boot.configuration.processor)
    
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)
    
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    
    testImplementation(libs.spring.boot.starter.test) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(libs.spring.security.test)
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

jgitver {
  
    strategy("MAVEN")
    
    nonQualifierBranches("main")
    
    
    versionPattern("\${M}.\${m}.\${p}\${<meta.DIRTY_TEXT}")
    
 
    tagVersionPattern("\${v}")
    
  
    regexVersionTag("([0-9]+)\\.([0-9]+)\\.([0-9]+).*")
    
   
    qualifierBranchingPolicies {
       
        addBranchingPolicy("develop", "SNAPSHOT")
        
    
        addBranchingPolicy("feature/(.*)", "SNAPSHOT")
    }
    
    autoIncrementPatch(false) 
    useCommitDistance(false)
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
