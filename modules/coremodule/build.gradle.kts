apply(from = "../common-library-module-config.gradle")

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
}

android {
    namespace = "nz.co.example.coremodule"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(sharedLibs.bundles.android)
    implementation(platform(sharedLibs.ktor.bom))
    implementation(sharedLibs.bundles.ktor)
}