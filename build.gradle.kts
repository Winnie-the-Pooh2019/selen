plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.seleniumhq.selenium:selenium-java:4.19.1")
}


tasks.test {
    // Access system properties set with -D flags'

    systemProperty("email", System.getProperty("email"))
    systemProperty("pass", System.getProperty("pass"))

    useJUnitPlatform()
}

kotlin {
    jvmToolchain(19)
}