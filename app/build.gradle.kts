plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}
apply(plugin = "kotlin-parcelize")

android {
    namespace = "com.prajwal.verifone"
    compileSdk = 35
    signingConfigs {
        getByName("debug").apply {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            val storeFilePath = System.getenv("RELEASE_STORE_FILE") ?: project.properties["RELEASE_STORE_FILE"] as String
            storeFile = file(storeFilePath)
            storePassword = System.getenv("RELEASE_STORE_PASSWORD") ?: project.properties["RELEASE_STORE_PASSWORD"] as String
            keyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: project.properties["RELEASE_KEY_ALIAS"] as String
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD") ?: project.properties["RELEASE_KEY_PASSWORD"] as String
        }
    }

    defaultConfig {
        applicationId = "com.prajwal.verifone"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "ANALYTICS_PACKAGE", "\"com.prajwal.analyticsserviceapp\"")
            buildConfigField(
                "String",
                "ANALYTICS_SERVICE_CLASS",
                "\"com.prajwal.analyticsserviceapp.AnalyticsService\""
            )

        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "ANALYTICS_PACKAGE", "\"com.prajwal.analyticsserviceapp\"")
            buildConfigField(
                "String",
                "ANALYTICS_SERVICE_CLASS",
                "\"com.prajwal.analyticsserviceapp.AnalyticsService\""
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
        aidl = true
        compose = true
        buildConfig = true
    }
    sourceSets["main"].aidl.srcDirs("src/main/aidl")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
}
