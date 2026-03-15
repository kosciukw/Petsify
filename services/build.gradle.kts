plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kosciukw.services"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        testOptions.targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(projects.shared.core)
    implementation(libs.koin.core)
    implementation(libs.kotlinxCoroutinesCore)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation(libs.okhttp.logging.interceptor)

    testImplementation(libs.bundles.junit5)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinxCoroutinesTest)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
