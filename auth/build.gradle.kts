plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    `maven-publish`
}

group = "university.miva"
version = "1.0.0-SNAPSHOT"

android {
    namespace = "university.miva.auth"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug { isMinifyEnabled = false }
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
        buildConfig = true
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
                artifactId = "auth"
                version = project.version.toString()
                from(components["release"])
            }
        }
    }
}

dependencies {

    implementation(projects.designsystem)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.coil.compose)
    implementation(libs.coil)
    implementation(libs.coil.video)
    implementation(libs.coil.gif)
    implementation(libs.androidx.media3.exoplayer.v120)
    implementation(libs.androidx.media3.ui.v120)
    implementation(libs.androidx.media3.common)
    implementation(libs.compose.ui)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.coil.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.media3.ui)
    implementation(libs.bundles.androidx.media3)
    implementation(libs.androidx.material3.android)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutineTest)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.workmanager)
    implementation(libs.timber)
}
