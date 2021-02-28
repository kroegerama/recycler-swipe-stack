@file:Suppress("SpellCheckingInspection")

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
    id(Plugins.safeArgs)
}

android {
    with(Android) {
        compileSdkVersion(COMPILE_SDK)

        defaultConfig {
            minSdkVersion(MIN_SDK)
            targetSdkVersion(TARGET_SDK)

            applicationId = APP_ID
            versionCode = VERSION_CODE
            versionName = VERSION_NAME

            vectorDrawables.useSupportLibrary = true
            resConfigs(LANGUAGES)
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
            isCoreLibraryDesugaringEnabled = true
        }
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
        sourceSets.all {
            java.srcDir("src/$name/kotlin")
        }
        buildFeatures {
            viewBinding = true
        }

        buildTypes {
            all {
                resValue(
                    "string",
                    "version_string",
                    "Version $VERSION_NAME-$name (build $VERSION_CODE)"
                )
            }
            getByName("debug") {
                applicationIdSuffix = ".dbg"
            }
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
        configure<BasePluginConvention> {
            archivesBaseName = "$APP_ID-$VERSION_NAME-b$VERSION_CODE"
        }
    }
}

dependencies {
    val bom = project.property("magic.bom")
    implementation(platform("com.kroegerama:magic-bom:$bom"))
    kapt(platform("com.kroegerama:magic-bom:$bom"))
    coreLibraryDesugaring(platform("com.kroegerama:magic-bom:$bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")

    implementation("androidx.appcompat:appcompat")
    implementation("androidx.core:core-ktx")
    implementation("androidx.activity:activity-ktx")
    implementation("androidx.fragment:fragment-ktx")
    implementation("androidx.constraintlayout:constraintlayout")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.lifecycle:lifecycle-common-java8")
    implementation("androidx.lifecycle:lifecycle-process")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate")

    implementation("com.google.android.material:material")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout")
    implementation("androidx.recyclerview:recyclerview")

    implementation("androidx.navigation:navigation-fragment-ktx")
    implementation("androidx.navigation:navigation-ui-ktx")

    val kaiteki = "4.0.4"
    implementation("com.kroegerama.android-kaiteki:core:$kaiteki")
    implementation("com.kroegerama.android-kaiteki:recyclerview:$kaiteki")
    implementation("com.kroegerama.android-kaiteki:retrofit:$kaiteki")

    implementation("com.google.dagger:hilt-android")
    kapt("com.google.dagger:hilt-compiler")

    implementation("androidx.hilt:hilt-lifecycle-viewmodel")
    kapt("androidx.hilt:hilt-compiler")

    implementation("com.jakewharton.timber:timber")

    implementation("io.coil-kt:coil")

    debugImplementation("com.github.chuckerteam.chucker:library")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs")

    implementation(project(":library"))
}

kapt {
    correctErrorTypes = true
}