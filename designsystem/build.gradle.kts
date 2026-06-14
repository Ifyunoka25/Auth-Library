plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    `maven-publish`
}

group = "university.miva"
version = "1.0.0-SNAPSHOT"

android {
    namespace = "university.miva.designsystem"
    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

publishing {
    repositories {
        maven {
            name = "localMiva"
            url = uri("${rootProject.layout.buildDirectory.get().asFile}/local-maven")
        }
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/Ifyunoka25/Auth-Library")
            credentials {
                username = providers.gradleProperty("gpr.user")
                    .orElse(providers.environmentVariable("GITHUB_ACTOR"))
                    .orNull
                password = providers.gradleProperty("gpr.key")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                    .orNull
            }
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "university.miva"
                artifactId = "designsystem"
                version = project.version.toString()
                from(components["release"])
            }
        }
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.coil.compose)
    implementation(libs.coil)
    implementation(libs.coil.video)
    implementation(libs.coil.gif)
    implementation(libs.lottie.compose.v630)
    implementation(libs.compose.ui)
    implementation(libs.richeditor.compose)
    implementation(libs.compose.foundation)
    implementation(libs.kotlinx.serialization)
    implementation(libs.geoLocation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.bundles.compose.ui)
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.android.pdf.viewer)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.activity.compose)
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.youtubeplayer.core)
    implementation(libs.androidx.navigation.runtime.android)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.coil.svg)
    implementation(libs.androidx.security.crypto.v110)
    implementation(libs.balloon)
    implementation(libs.kdeviceinfo)
}
