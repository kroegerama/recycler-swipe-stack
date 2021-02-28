import BuildConfig.configurePublish

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.mavenPublish)
    id(Plugins.signing)
}

android {
    compileSdkVersion(Android.COMPILE_SDK)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-module-name", "com.kroegerama.reswista")
    }
    sourceSets.all {
        java.srcDir("src/$name/kotlin")
    }
    buildFeatures {
        buildConfig = false
        viewBinding = true
    }

    defaultConfig {
        minSdkVersion(Android.MIN_SDK)
        targetSdkVersion(Android.TARGET_SDK)
        versionCode = 1
        versionName = P.projectVersion
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    val bom = project.property("magic.bom")
    implementation(platform("com.kroegerama:magic-bom:$bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")

    implementation("androidx.appcompat:appcompat")
    implementation("androidx.core:core-ktx")
    implementation("androidx.recyclerview:recyclerview")

    val kaiteki = "4.0.4"
    implementation("com.kroegerama.android-kaiteki:core:$kaiteki")
    implementation("com.kroegerama.android-kaiteki:recyclerview:$kaiteki")

    implementation("com.jakewharton.timber:timber")
}

afterEvaluate(configurePublish())