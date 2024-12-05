plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.chatbotsamplew"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chatbotsamplew"
        minSdk = 31
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
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("androidx.room:room-runtime:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
    implementation ("org.java-websocket:Java-WebSocket:1.5.2") // WebSocket kütüphanesi
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1") // ViewModel
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")  // LiveData
    implementation ("androidx.databinding:databinding-runtime:7.4.2") // DataBinding
    implementation ("androidx.recyclerview:recyclerview:1.3.1") // RecyclerView


}