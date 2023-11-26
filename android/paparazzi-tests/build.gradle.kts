plugins {
    alias(libs.plugins.hulkdx.android.library.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.hulkdx.findprofessional.paparazzi.tests"
}

dependencies {
    implementation(project(":android:feature:authentication"))
    implementation(project(":android:feature:home"))
    implementation(project(":android:core"))
    implementation(project(":android:app"))

    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation("androidx.compose.runtime:runtime")
    testImplementation("junit:junit:4.13.2")
}

// Disable release build type
androidComponents {
    beforeVariants { variant ->
        variant.enable = variant.buildType == "debug"
    }
}
