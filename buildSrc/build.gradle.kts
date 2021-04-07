import java.util.Properties

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

plugins {
    `kotlin-dsl`
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("org.jetbrains.trove4j:trove4j:20160824")).using(module("org.jetbrains.intellij.deps:trove4j:1.0.20181211"))
    }
}

file("../gradle.properties").inputStream().use { input ->
    Properties().apply { load(input) }
}.run {
    stringPropertyNames().forEach { k ->
        ext.set(k, getProperty(k))
    }
}

dependencies {
    val bom = project.property("magic.bom")
    implementation(platform("com.kroegerama:magic-bom:$bom"))
    implementation("io.github.gradle-nexus:publish-plugin:1.0.0")

    implementation(gradleApi())

    //use kotlin version of the `kotlin-dsl` plugin here
    implementation("org.jetbrains.kotlin:kotlin-stdlib") {
        version { strictly("1.4.20") }
    }

    implementation(kotlin("gradle-plugin"))
    implementation("com.android.tools.build:gradle")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin")
    implementation("com.google.dagger:hilt-android-gradle-plugin")
}