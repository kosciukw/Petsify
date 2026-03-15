plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
}

kotlin {
  androidTarget()

  jvmToolchain(libs.versions.javaVersion.get().toInt())

  sourceSets {
    commonMain.dependencies {
      implementation(projects.shared.core)
      implementation(libs.kotlinxCoroutinesCore)
    }
  }
}

android {
  namespace = "pl.kosciukw.petsify.shared.presentation.core"
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

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}
