pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "Petsify"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":shared:ui")
include(":feature:login")
include(":feature:signup")
include(":feature:main")
include(":feature:emails")
include(":feature:profile")
include(":feature:emaildetails")
include(":feature:composer")
include(":feature:composersettings")
include(":services")