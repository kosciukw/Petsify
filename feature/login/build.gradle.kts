plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.hilt.android.gradle.plugin)
  kotlin("kapt")
}

android {
  namespace = "pl.kosciukw.petsify.feature.login"
  compileSdk = libs.versions.compileSdkVersion.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdkVersion.get().toInt()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    val javaVersion = libs.versions.javaVersion.get()
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)
  }

  kotlinOptions {
    jvmTarget = libs.versions.javaVersion.get()
  }
  testOptions {
    unitTests.isIncludeAndroidResources = true
    unitTests.all {
      it.useJUnitPlatform()
    }
  }
}

dependencies {
  implementation(projects.shared.ui)
  implementation(projects.services)

  implementation(libs.hilt.android)
  implementation(libs.androidx.hilt.navigation.compose)
  kapt(libs.hilt.compiler)

  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  testImplementation(libs.bundles.junit5)
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinxCoroutinesTest)
}