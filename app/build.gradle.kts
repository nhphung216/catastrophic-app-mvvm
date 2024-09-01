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
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    implementation(libs.koin.android)

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
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.paging.runtime.ktx)
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
    testImplementation(libs.androidx.runner)
    androidTestImplementation(libs.truth)

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

    implementation("org.mockito:mockito-android:5.13.0")

    // JUnit 4 for testing
    testImplementation("junit:junit:4.13.2")

    // MockK for mocking
    androidTestImplementation("io.mockk:mockk:1.13.7")
    testImplementation("io.mockk:mockk:1.13.7")

    // AndroidX Core Testing for LiveData testing
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    testImplementation("org.robolectric:robolectric:4.10.3")
}