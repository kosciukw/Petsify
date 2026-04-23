plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  androidTarget()
  iosX64()
  iosArm64()
  iosSimulatorArm64()

  jvmToolchain(libs.versions.javaVersion.get().toInt())

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.serialization)
      implementation(libs.kotlinxCoroutinesCore)
      implementation(libs.kotlin.reflect)
    }

    androidUnitTest.dependencies {
      implementation(libs.bundles.junit5)
      implementation(libs.mockk)
      implementation(libs.kotlinxCoroutinesTest)
      implementation(libs.androidx.junit)
      implementation(libs.androidx.espresso.core)
    }
  }
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

  testOptions {
    unitTests.isIncludeAndroidResources = true
    unitTests.all {
      it.useJUnitPlatform()
    }
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}
