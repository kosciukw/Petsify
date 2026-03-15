plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.hilt.android.gradle.plugin)
  kotlin("kapt")
}

android {
  namespace = "pl.kosciukw.petsify.shared.core"
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
  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.navigation)
  implementation(libs.kotlinx.serialization)
  implementation(libs.kotlin.reflect)

  testImplementation(libs.bundles.junit5)
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinxCoroutinesTest)

  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}
