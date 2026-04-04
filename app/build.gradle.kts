plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "pl.kosciukw.petsify"
  compileSdk = libs.versions.compileSdkVersion.get().toInt()

  defaultConfig {
    applicationId = "pl.kosciukw.petsify"
    minSdk = libs.versions.minSdkVersion.get().toInt()
    targetSdk = libs.versions.compileSdkVersion.get().toInt()
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

  buildFeatures {
    compose = true
  }
  testOptions {
    unitTests.isIncludeAndroidResources = true
    unitTests.all {
      it.useJUnitPlatform()
    }
  }
}

dependencies {
  implementation(projects.shared.servicesApi)
  implementation(projects.shared.ui)
  implementation(projects.feature.splash)
  implementation(projects.feature.login)
  implementation(projects.feature.signup)
  implementation(projects.feature.settings)
  implementation(projects.feature.main)
  implementation(projects.feature.addpet)
  implementation(projects.feature.emaildetails)
  implementation(projects.feature.composer)
  implementation(libs.bundles.androidx)
  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)
  implementation(libs.androidx.datastore.core.android)
  implementation(libs.androidx.datastore.preferences)

  implementation(libs.kotlinx.metadata.jvm)
  implementation(project(":services"))

  debugImplementation(libs.bundles.compose.debug)

  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.ui.test.junit4)

  testImplementation(libs.bundles.junit5)
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinxCoroutinesTest)
}
