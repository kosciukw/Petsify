plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  androidTarget()
  iosX64()
  iosArm64()
  iosSimulatorArm64()

  jvmToolchain(libs.versions.javaVersion.get().toInt())

  sourceSets {
    commonMain {
      kotlin.srcDir("src/commonMain/kotlin")
      dependencies {
        implementation(projects.shared.core)
        implementation(projects.shared.presentationCore)
        implementation(projects.shared.servicesApi)
        implementation(libs.koin.core)
        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(compose.ui)
      }
    }

    androidMain {
      kotlin.srcDir("src/androidMain/kotlin")
      dependencies {
        implementation(libs.androidx.navigation)
        implementation(libs.koin.androidx.compose)
      }
    }

    androidUnitTest {
      kotlin.srcDir("src/test/java")
      dependencies {
        implementation(libs.bundles.junit5)
        implementation(libs.mockk)
        implementation(libs.kotlinxCoroutinesTest)
        implementation(libs.androidx.junit)
        implementation(libs.androidx.espresso.core)
      }
    }
  }
}

android {
  namespace = "pl.kosciukw.petsify.feature.splash"
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
}
