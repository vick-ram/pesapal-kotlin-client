plugins {
    kotlin("jvm") version "2.2.20"
}

group = "com.pesapal"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:2.3.3")
    implementation("io.ktor:ktor-client-cio:2.3.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.3")
    implementation("io.ktor:ktor-serialization-gson:2.3.3")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation("io.ktor:ktor-client-mock:2.3.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}