apply(from = "../common-library-module-config.gradle")

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "nz.co.example.coremodule"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(sharedLibs.bundles.android)
}