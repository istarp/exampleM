package nz.co.example.app.config

import org.gradle.api.JavaVersion
import java.text.SimpleDateFormat
import java.util.*

object ProjectConfig {
    const val compileSdkVersion = 35 // Should match whatnever is in ModuleConfig.kt
    const val minSdkVersion = 26 // Should match whatnever is in ModuleConfig.kt
    const val targetSdkVersion = 35 // Should match whatnever is in ModuleConfig.kt
    const val applicationId = "nz.co.example.app"

    val versionCode = getDateTimestampForVersionCode()
    const val version = "0.0.1"
    const val versionName = version

    val jvmTarget = JavaVersion.VERSION_17
    const val kotlinJvmTarget = "17"
}

fun getDateTimestampForVersionCode(): Int = getFormattedDate("yyMMddHH").toInt()

private fun getFormattedDate(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault(Locale.Category.FORMAT))
    return simpleDateFormat.format(Date())
}