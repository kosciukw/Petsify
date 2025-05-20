plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.hilt.android.gradle.plugin)
  kotlin("kapt")
}

android {
  namespace = "pl.kosciukw.petsify.shared.ui"
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
  api(platform(libs.androidx.compose.bom))
  api(libs.bundles.compose)
  api(libs.androidx.material3)
  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)

  debugImplementation(libs.bundles.compose.debug)

  testImplementation(libs.bundles.junit5)
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinxCoroutinesTest)
}