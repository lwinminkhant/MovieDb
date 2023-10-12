import java.io.FileNotFoundException
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.31"
}

android {
    var releaseSigning = signingConfigs.getByName("debug")

    val signingProperties = getProperties("signingConfigs.properties")

    /*releaseSigning = signingConfigs.create("release") {
        storeFile = file(getProperty(signingProperties, "storeFile"))
        storePassword = getProperty(signingProperties, "keyAlias")
        keyAlias = getProperty(signingProperties, "storePassword")
        keyPassword = getProperty(signingProperties, "keyPassword")
    }*/



    lint {
        baseline = file("lint-baseline.xml")
    }

    namespace = "com.lmkhant.moviedb"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.lmkhant.moviedb"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val apiKey: String = getProperty(getProperties("local.properties"), "API_KEY")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = releaseSigning
            //signingConfig = signingConfigs.getByName("debug")
        }
        debug {
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
fun getProperties(fileName: String): Properties {
    val properties = Properties()
    val file = rootProject.file(fileName)
    if (file.exists()) {
        properties.load(project.rootProject.file(fileName).reader())
    } else {
        project.logger.error("$fileName doesn't exist")
    }
    return properties
}

fun getProperty(properties: Properties, name: String): String {
    return properties.getProperty(name) ?: "$name missing"
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")


    implementation("androidx.compose.material:material-icons-extended")
    testImplementation("junit:junit:4.12")

    //Room
    val roomVersion = "2.5.2"
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")


    //Hilt
    ksp("com.google.dagger:hilt-compiler:2.47")
    implementation("com.google.dagger:hilt-android:2.47")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    val glideVersion = "4.14.2"
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    ksp("com.github.bumptech.glide:compiler:$glideVersion")

    //Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.airbnb.android:lottie-compose:5.2.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.1")

    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.4")
    //testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")

    //Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")

    implementation("com.google.code.gson:gson:2.10")
    implementation("com.google.accompanist:accompanist-insets:0.31.5-beta")

    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.5")

    //Destination Navigation
    implementation("io.github.raamcosta.compose-destinations:core:1.8.42-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.8.42-beta")
    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    // Coroutine test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}