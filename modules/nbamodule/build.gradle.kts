import com.android.build.api.dsl.LibraryBuildType

apply(from = "../common-library-module-config.gradle")

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    namespace = "nz.co.example.nbamodule"
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
           setApiKey()
        }
        release {
            setApiKey()
        }
    }
}

fun LibraryBuildType.setApiKey() {
    val apiKey: String by project
    buildConfigField("String", "API_KEY", apiKey)
}

dependencies {
    implementation(project(":modules:coremodule"))

    implementation(sharedLibs.bundles.compose)
    implementation(sharedLibs.bundles.android)
    implementation(sharedLibs.bundles.retrofit)
    implementation(sharedLibs.bundles.room)
    kapt(sharedLibs.bundles.room.compiler)

    implementation(sharedLibs.androidx.paging.runtime)
}