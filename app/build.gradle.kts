plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.phung.catastrophicapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.phung.catastrophicapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val catApiKey: String = project.findProperty("THE_CAT_API_KEY") as String
        buildConfigField("String", "CAT_API_KEY", "\"$catApiKey\"")

        val serverUrl: String = project.findProperty("SERVER_URL") as String
        buildConfigField("String", "SERVER_URL", "\"$serverUrl\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    testOptions { packaging { resources.excludes.add("META-INF/*") } }
}

dependencies {
    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.photoview)
    implementation(libs.androidx.recyclerview)
    implementation(libs.glide)
    kapt(libs.compiler)

    // koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    androidTestImplementation(libs.koin.test)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.adapter.rxjava3)

    // room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)

    // default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // testing
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)

    testImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.core.testing)

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)

    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.core)

    testImplementation(libs.mockito.inline)
    androidTestImplementation(libs.mockito.inline)

    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.kotlin)

    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.intents)
}