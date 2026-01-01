import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.hilt.android)
}

val props = Properties()
if (rootProject.file("local.properties").exists()) {
    props.load(rootProject.file("local.properties").inputStream())
}
val apiKey: String? = props.getProperty("API_KEY", "")
val OMDB_API_KEY: String? = props.getProperty("OMDB_API_KEY", "")
val OMDB_BASE_URL: String? = props.getProperty("OMDB_BASE_URL", "https://www.omdbapi.com/")

android {

    namespace = "com.ramesh.sample"
    compileSdk {
        version = release(36)
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            buildConfigField("String", "BASE_URL", "\"https://api-dev.example.com\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.example.com\"")
        }
    }

    defaultConfig {
        applicationId = "com.ramesh.sample"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "OMDB_API_KEY", "\"$OMDB_API_KEY\"")
        buildConfigField("String", "OMDB_BASE_URL", "\"$OMDB_BASE_URL\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.room.runtime)
    implementation(libs.volley)
    kapt(libs.room.compiler)
    implementation(libs.identity.doctypes.jvm)
    implementation(libs.room.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}