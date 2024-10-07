plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.devicesmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.devicesmanager"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)
    implementation(libs.timber)

    implementation(libs.coroutines.core)
    implementation(libs.collections.immutable)

    // Orbit
    implementation(libs.orbit)
    implementation(libs.orbit.viewmodel)
    implementation(libs.orbit.compose)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.cmopose)
    implementation(libs.koin.cmopose.viewmodel)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.navigation)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}