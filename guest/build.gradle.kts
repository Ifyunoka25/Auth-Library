plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.parcelize)
    `maven-publish`
}

group = "university.miva"
version = "1.0.0-SNAPSHOT"


android {
    namespace = "university.miva.guest"
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

dependencies {
    implementation(projects.designsystem)
    implementation(projects.auth)

    implementation(libs.material)
    implementation(libs.material.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.timber)
    implementation(libs.androidx.activity.compose)
    implementation(libs.serialization.json)
    implementation(libs.bundles.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.ui.graphics)
    implementation(libs.coil.compose)
    implementation(libs.coil.video)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.youtubeplayer.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.work.work.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.bundles.androidx.lifecycle)

    testImplementation(libs.mockk)
    testImplementation(libs.coroutineTest)
    testImplementation(libs.turbine)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
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
                artifactId = "guest"
                version = project.version.toString()
                from(components["release"])
            }
        }
    }
}
