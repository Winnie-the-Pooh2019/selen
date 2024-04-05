plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.seleniumhq.selenium:selenium-java:4.19.1")
//    implementation("io.github.bonigarcia:webdrivermanager:5.7.0")


//    implementation("org.slf4j:slf4j-api:2.0.9")
//    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
}


tasks.test {
    // Access system properties set with -D flags'

    systemProperty("mod", System.getProperty("mod"))
    systemProperty("email", System.getProperty("email"))
    systemProperty("pass", System.getProperty("pass"))

    useJUnitPlatform()
}

kotlin {
    jvmToolchain(19)
}