@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin)
}

android {
    compileSdk = ConfigData.compileSdk
    namespace = NamceSpace.Core.ui

    defaultConfig {
        minSdk = ConfigData.minSdk
    }

    buildTypes {
        getByName(BuildTypeDebug.name) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }

        getByName(BuildTypeRelease.name) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            consumerProguardFiles("proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.kotlinJvmTarget.get()
    }


    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.bundles.kotlinCoroutines)
    implementation(libs.bundles.navigationComponent)
    implementation(libs.coil)
    implementation(libs.timber)

}