plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.hilt.android.gradle.plugin)
  kotlin("kapt")
}

android {
  namespace = "com.kosciukw.services"
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
}

dependencies {
  implementation(projects.shared.ui)

  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)
  implementation(libs.androidx.hilt.navigation.compose)

  implementation(libs.retrofit)
  implementation(libs.retrofit.gson)
  implementation(libs.gson)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)

  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  testImplementation(libs.junit)
}