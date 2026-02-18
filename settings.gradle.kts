@file:Suppress("UnstableApiUsage")

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://cache-redirector.jetbrains.com/kotlin.bintray.com/kotlin-plugin")
  }
}

//plugins {
//  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
//}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://jitpack.io") }
  }
}

rootProject.name = "KarbonIDE"
include(":app")

include(":editor:editor")
include(":editor:editor-lsp")
include(":editor:language-textmate")

include(":feature:plugin")
include(":feature:runner")
include(":feature:filetree")
include(":feature:settings")

include(":core:components")
include(":core:commons")

