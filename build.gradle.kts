buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(sharedLibs.gradle.build.tools)
        classpath(sharedLibs.gradle.kotlin.plugin)
        classpath(sharedLibs.gradle.kotlin.serialization)
    }
}

plugins {
    id("com.google.devtools.ksp") version sharedLibs.versions.kotlin.ksp apply false
}