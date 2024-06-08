plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.woosung.bootcampfinalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.woosung.bootcampfinalproject"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        jniLibs {
            pickFirsts += setOf(
                "lib/arm64-v8a/libc++_shared.so",
                "lib/x86/libc++_shared.so",
                "lib/x86_64/libc++_shared.so",
                "lib/armeabi-v7a/libc++_shared.so",
                //"lib/arm64-v8a/libpytorch_jni.so",
                //"lib/x86/libpytorch_jni.so",
                //"lib/x86_64/libpytorch_jni.so",
                //"lib/armeabi-v7a/libpytorch_jni.so"
            )
            excludes += setOf(
                //"lib/arm64-v8a/libc++_shared.so",
                //"lib/x86/libc++_shared.so",
                //"lib/x86_64/libc++_shared.so",
                //"lib/armeabi-v7a/libc++_shared.so"
            )
        }
    }
}

    dependencies {
    //implementation("org.pytorch:pytorch_android:1.8.0")
    //implementation ("org.pytorch:pytorch_android_torchvision:1.8.0")
    //implementation ("com.google.mlkit:face-detection:16.1.6")
    //implementation ("org.opencv:opencv:4.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation ("androidx.camera:camera-camera2:1.3.3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}