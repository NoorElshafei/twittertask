@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")


plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    compileSdk = ConfigData.compileSdk
    namespace = NamceSpace.Core.network

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
        buildConfig = true
    }
}

dependencies {
    api(libs.bundles.networking)
    implementation(libs.timber)

    implementation(libs.hilt)
    kapt(libs.hiltDaggerCompiler)

    implementation(project(Modules.Core.utils))

    testImplementation(libs.bundles.unitTest)

}