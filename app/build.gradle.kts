plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.movinsight"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movinsight"
        minSdk = 25
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            buildConfigField("String", "RAPID_API_KEY", "\"<Replace with api key>\"")
            buildConfigField("String", "OMDB_API_KEY", "\"<Replace with api key>\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "RAPID_API_KEY", "\"<Replace with api key>\"")
            buildConfigField("String", "OMDB_API_KEY", "\"<Replace with api key>\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.test:core-ktx:1.5.0")
    val fragmentVersion = "1.6.2"

    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore-ktx:24.8.1")
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("androidx.room:room-runtime:2.6.0")//2.2.5
    implementation("androidx.room:room-ktx:2.6.0")//2.2.5
    //implementation("com.google.dagger:dagger:2.9")//2.9
    kapt("androidx.room:room-compiler:2.6.0")//2.2.5
    //kapt("android.arch.persistence.room:compiler:1.1.1")//1.1.1
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("com.google.code.gson:gson:2.8.6")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    //test mock
    testImplementation("org.mockito:mockito-core:2.19.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
}