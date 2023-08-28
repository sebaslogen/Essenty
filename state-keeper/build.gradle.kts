import com.arkivanov.gradle.bundle
import com.arkivanov.gradle.setupBinaryCompatibilityValidator
import com.arkivanov.gradle.setupMultiplatform
import com.arkivanov.gradle.setupPublication
import com.arkivanov.gradle.setupSourceSets

plugins {
    id("kotlin-multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.arkivanov.parcelize.darwin")
    id("com.arkivanov.gradle.setup")
}

setupMultiplatform()
setupPublication()
setupBinaryCompatibilityValidator()

android {
    namespace = "com.arkivanov.essenty.statekeeper"
}

kotlin {
    setupSourceSets {
        val android by bundle()

        common.main.dependencies {
            implementation(project(":utils-internal"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.6.0")
        }

        android.main.dependencies {
            implementation(deps.androidx.savedstate.savedstateKtx)
            implementation(deps.androidx.lifecycle.lifecycleRuntime)
        }

        android.test.dependencies {
            implementation(deps.robolectric.robolectric)
        }
    }
}
