-keepattributes *Annotation*, InnerClasses,EnclosingMethod
-dontnote kotlinx.serialization.AnnotationsKt # serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class nz.co.example.**$$serializer { *; }
-keepclassmembers class nz.co.example.** {
    *** Companion;
}
-keepclasseswithmembers class nz.co.example.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# prevent these issues: https://issuetracker.google.com/issues/214733446
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}