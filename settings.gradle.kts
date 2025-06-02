rootProject.name = "ml-keycloak-security-starter"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    
    versionCatalogs {
        create("libs") {
            // Versionen
            version("spring-boot", "3.2.5")
            version("lombok", "1.18.38")
            
            // Spring Boot Dependencies
            library("spring-boot-starter-security", "org.springframework.boot", "spring-boot-starter-security").withoutVersion()
            library("spring-boot-autoconfigure", "org.springframework.boot", "spring-boot-autoconfigure").withoutVersion()
            library("spring-boot-configuration-processor", "org.springframework.boot", "spring-boot-configuration-processor").withoutVersion()
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            
            // Externe Dependencies
            library("lombok", "org.projectlombok", "lombok").versionRef("lombok")
        }
    }
}