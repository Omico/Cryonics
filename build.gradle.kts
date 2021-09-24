import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import me.omico.age.dsl.configureAndroidCommon
import me.omico.age.dsl.configureSpotlessWithCommonRules
import me.omico.age.dsl.kotlinCompile

plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
    id("me.omico.age")
    kotlin("android") apply false
}

allprojects {
    group = "me.omico.cryonics"
    version = "1.0.0-SNAPSHOT"
    configureDependencyUpdates()
    configureSpotlessWithCommonRules()
    configureAndroidCommon {
        compileSdk = 31
        buildToolsVersion = "31.0.0"
        defaultConfig {
            minSdk = 24
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
    kotlinCompile {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

fun Project.configureDependencyUpdates() {
    apply(plugin = "com.github.ben-manes.versions")
    tasks.named("dependencyUpdates", DependencyUpdatesTask::class).configure {
        val stableList = listOf(
            "com.android.application",
            "com.android.library",
            "com.android.tools.lint",
            "com.google.protobuf",
            "com.squareup.okhttp3",
            "org.jetbrains.kotlin",
            "org.jetbrains.kotlin.android",
        )
        rejectVersionIf {
            when {
                stableList.contains(candidate.group) -> isNonStable(candidate.version)
                else -> false
            }
        }
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
