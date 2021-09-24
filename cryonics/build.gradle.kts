import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import me.omico.age.dsl.withKotlinAndroidMavenPublication

plugins {
    id("com.google.protobuf")
    id("com.android.library")
    kotlin("android")
}

withKotlinAndroidMavenPublication()

android {
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

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobuf3.versions.protobuf.get()}"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("kotlin") {
                    option("lite")
                }
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    compileOnly(androidx.datastore.datastore)
    compileOnly(okhttp4.okhttp)
    compileOnly(protobuf3.protobufKotlin)
}
