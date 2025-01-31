plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }
}

repositories {
    mavenCentral()
    google()
}

val kotlinVersion = "1.9.25"
val gradleBuildTools = "8.6.1"

val kotlinGradlePluginDependency = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
val kotlinSerializationDependency = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
val gradleBuildToolsDependency = "com.android.tools.build:gradle:$gradleBuildTools"

dependencies {
    // in order to be able to use "kotlin-android" in the common script
    implementation(kotlinGradlePluginDependency)

    // in order to recognize the "plugins" block in the common script
    implementation(gradleBuildToolsDependency)
}

