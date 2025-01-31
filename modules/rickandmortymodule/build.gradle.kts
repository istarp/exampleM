apply(from = "../common-library-module-config.gradle")

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    namespace = "nz.co.example.rickandmortymodule"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":modules:coremodule"))

    implementation(sharedLibs.bundles.compose)
    implementation(sharedLibs.bundles.android)
    implementation(platform(sharedLibs.ktor.bom))
    implementation(sharedLibs.bundles.ktor)
    implementation(sharedLibs.bundles.room)
    kapt(sharedLibs.bundles.room.compiler)

    implementation(sharedLibs.androidx.paging.runtime)
}