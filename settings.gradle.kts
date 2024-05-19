@file:Suppress("UnstableApiUsage")

include(":core:uiTest")


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")

    }
}

rootProject.name = "Twitter Task"
include(":app")
include(":core")
include(":core:Network")
include(":core:ui")
include(":core:utils")
include(":feature:twitter")

