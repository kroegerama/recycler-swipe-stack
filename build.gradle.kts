import io.codearte.gradle.nexus.NexusStagingExtension

plugins {
    id(Plugins.androidApplication) apply false
    id(Plugins.androidLibrary) apply false
    kotlin("android") apply false
    id(Plugins.nexusStaging)
}

allprojects {
    configurations.all {
        resolutionStrategy.dependencySubstitution {
            substitute(module("org.jetbrains.trove4j:trove4j:20160824")).using(module("org.jetbrains.intellij.deps:trove4j:1.0.20181211"))
        }
    }
    repositories {
        google()
        mavenCentral()
    }
    version = P.projectVersion
    group = P.projectGroupId
    description = P.projectDescription
}
subprojects {
    apply {
        from(rootProject.file("ktlint.gradle.kts"))
    }
}

val clean by tasks.creating(Delete::class) {
    group = "build"
    delete(rootProject.buildDir)
}

configure<NexusStagingExtension> {
    val nexusStagingProfileId: String? by project
    val nexusUsername: String? by project
    val nexusPassword: String? by project

    packageGroup = group.toString()
    stagingProfileId = nexusStagingProfileId
    username = nexusUsername
    password = nexusPassword
}

afterEvaluate {
    val hookFile = File(rootProject.rootDir, "pre-commit")
    val destDir = File(rootProject.rootDir, ".git/hooks")
    val destFile = File(destDir, "pre-commit")

    if (destDir.exists() && destDir.isDirectory) {
        if (!destFile.exists()) {
            println("installing pre-commit hook")
            copy {
                from(hookFile)
                into(destDir)
                fileMode = Integer.parseUnsignedInt("755", 8)
            }
        } else {
            println("pre-commit hook already installed")
        }
    } else {
        println("no git folder found - cannot install git hook")
    }
}
