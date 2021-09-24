rootProject.name = "Cryonics"

pluginManagement {
    val versions = object {
        val agePlugin = "1.0.0-SNAPSHOT"
        val androidGradlePlugin = "7.0.2"
        val gradleEnterprisePlugin = "3.6.4"
        val gradleVersionsPlugin = "0.39.0"
        val kotlinPlugin = "1.5.31"
        val protobuf = "0.8.17"
        val spotlessPlugin = "5.15.1"
    }
    plugins {
        id("com.android.application") version versions.androidGradlePlugin
        id("com.android.library") version versions.androidGradlePlugin
        id("com.diffplug.spotless") version versions.spotlessPlugin
        id("com.github.ben-manes.versions") version versions.gradleVersionsPlugin
        id("com.google.protobuf") version versions.protobuf
        id("com.gradle.enterprise") version versions.gradleEnterprisePlugin
        id("me.omico.age") version versions.agePlugin
        kotlin("android") version versions.kotlinPlugin
    }
    repositories {
        google()
        gradlePluginPortal()
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
        mavenCentral()
    }
}

enableFeaturePreview("VERSION_CATALOGS")

createVersionCatalog("androidx")
createVersionCatalog("okhttp4")
createVersionCatalog("protobuf3")

plugins {
    id("com.gradle.enterprise")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}

include(":cryonics")

fun createVersionCatalog(name: String) =
    dependencyResolutionManagement.versionCatalogs.create(name) {
        from(files("gradle/common-version-catalogs/$name.versions.toml"))
    }
