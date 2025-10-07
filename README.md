# 🧾 Pesapal Kotlin Client (Unofficial SDK)

[![JitPack](https://jitpack.io/v/vick-ram/pesapal-kotlin-client.svg)](https://jitpack.io/#vick-ram/pesapal-kotlin-client)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue?logo=kotlin)](https://kotlinlang.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

> 🪙 An unofficial Kotlin SDK for integrating with the [Pesapal Payment Gateway API](https://developer.pesapal.com/).  
> Built with simplicity, type-safety, and modern Kotlin multiplatform patterns in mind.

---

## 🚀 Overview

The **Pesapal Kotlin Client** simplifies integration with the [Pesapal REST API](https://developer.pesapal.com/how-to-integrate/) by providing a lightweight, type-safe client built on top of **Ktor Client**.

With this SDK, you can:
- Authenticate and get access tokens.
- Register orders and track transactions.
- Handle IPN notifications.
- Initiate refunds or cancellations.
- Access well-structured response models.

---

## 📦 Installation

Add the dependency from **[JitPack](https://jitpack.io/#vick-ram/pesapal-kotlin-client)**.

### 1️⃣ Add the JitPack repository

**`settings.gradle.kts` or `build.gradle.kts`**
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.vick-ram:pesapal-kotlin-client:LATEST_VSERSION")
}
