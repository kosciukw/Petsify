plugins {
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.compose)
}

kotlin {
  iosX64()
  iosArm64()
  iosSimulatorArm64()

  jvmToolchain(libs.versions.javaVersion.get().toInt())

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "PetsifyIosApp"
      isStatic = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(projects.shared.core)
      implementation(projects.shared.presentationCore)
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
    }

    iosMain.dependencies {
      implementation(compose.ui)
    }
  }
}
