rootProject.name = "ml-keycloak-security-starter"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            // Spring Boot BOM – Version wird im build.gradle.kts importiert
            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").withoutVersion()
            library("spring-boot-starter-security", "org.springframework.boot", "spring-boot-starter-security").withoutVersion()
            library("spring-boot-starter-oauth2-client", "org.springframework.boot", "spring-boot-starter-oauth2-client").withoutVersion()
            library("spring-boot-starter-oauth2-authorization-server", "org.springframework.boot", "spring-boot-starter-oauth2-authorization-server").withoutVersion()
            library("spring-boot-devtools", "org.springframework.boot", "spring-boot-devtools").withoutVersion()
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library("spring-security-test", "org.springframework.security", "spring-security-test").withoutVersion()

            // Externe Bibliotheken mit expliziten Versionen
            version("lombok", "1.18.30")
            version("junit-platform", "1.10.2")
            library("lombok", "org.projectlombok", "lombok").versionRef("lombok")
            library("junit-platform-launcher", "org.junit.platform", "junit-platform-launcher").versionRef("junit-platform")
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").withoutVersion()
            library("junit-platform-launcher", "org.junit.platform", "junit-platform-launcher").withoutVersion()

        }
    }
}
