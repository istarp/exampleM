import nz.co.example.app.config.ProjectConfig
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    namespace = "nz.co.example.app"
    compileSdk = ProjectConfig.compileSdkVersion

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdkVersion
        targetSdk = ProjectConfig.targetSdkVersion
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "debug"
            keyPassword = "password"
            storeFile = file("../keystore/debug.keystore")
            storePassword = "password"
        }

        create("release") {
            val releaseKeystorePath: String by project
            val releaseKeystorePassword: String by project
            val releaseKeystoreKeyAlias: String by project
            val releaseKeystoreKeyPassword: String by project
            storeFile = file(releaseKeystorePath)
            storePassword = releaseKeystorePassword
            keyAlias = releaseKeystoreKeyAlias
            keyPassword = releaseKeystoreKeyPassword
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), File("proguard-rules.pro"))
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), File("proguard-rules.pro"))
        }
    }

    compileOptions {
        sourceCompatibility = ProjectConfig.jvmTarget
        targetCompatibility = ProjectConfig.jvmTarget
    }
    kotlinOptions {
        jvmTarget = ProjectConfig.kotlinJvmTarget
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlin.OptIn",
            "-Xuse-k2",
            "-Xcontext-receivers"
        )
    }

    kotlin {
        sourceSets.all {
            languageSettings.enableLanguageFeature("ExplicitBackingFields")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = sharedLibs.versions.androidx.compose.compiler.get()
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
            it.testLogging {
                exceptionFormat = TestExceptionFormat.FULL
                events = setOf(
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.FAILED,
                    TestLogEvent.STANDARD_OUT,
                    TestLogEvent.STANDARD_ERROR
                )
            }
        }
    }

    /**
     * PerModuleReportDependenciesTask is not compatible with configuration caching.
     * As a workaround, google suggested not to include dependency info.
     * Issue here - https://issuetracker.google.com/issues/162074215
     */
    dependenciesInfo {
        includeInBundle = false
        includeInApk = false
    }
}

dependencies {
    implementation(project(":modules:rickandmortymodule"))
    implementation(project(":modules:coremodule"))

    implementation(sharedLibs.bundles.kotlin)
    implementation(sharedLibs.bundles.android)
    implementation(sharedLibs.bundles.coil)
    implementation(sharedLibs.bundles.koin.android)
    implementation(platform(sharedLibs.compose.bom))
    implementation(sharedLibs.bundles.compose)
    implementation(sharedLibs.bundles.kotlin.serialization)

    implementation(sharedLibs.androidx.paging.compose)

    debugImplementation(sharedLibs.bundles.compose.debug)
}