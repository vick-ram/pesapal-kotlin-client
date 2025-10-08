plugins {
    kotlin("jvm") version "2.2.20"
    `maven-publish`
}

group = "com.pesapal"
version = "1.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:3.3.0")
    implementation("io.ktor:ktor-client-cio:3.3.0")
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

java {
    withSourcesJar()
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    jvmToolchain(8)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.github.vick-ram"
            artifactId = "pesapal-kotlin-client"
            version = version

            pom {
                name.set("Pesapal Kotlin SDK")
                description.set("Unofficial Kotlin SDK for Pesapal Payment API")
                url.set("https://github.com/vick-ram/pesapal-kotlin-client")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("vick-ram")
                        name.set("Vickram Odero")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/vick-ram/pesapal-kotlin-client.git")
                    developerConnection.set("scm:git:ssh://github.com/vick-ram/pesapal-kotlin-client.git")
                    url.set("https://github.com/vick-ram/pesapal-kotlin-client")
                }
            }
        }
    }
}