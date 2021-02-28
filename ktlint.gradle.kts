val ktlint by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.39.0")
}

val outputDir = File(project.buildDir, "reports/ktlint/")
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    group = "verification"

    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("--android", "--disabled_rules=max-line-length,import-ordering", "src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    group = "verification"

    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("--android", "--disabled_rules=max-line-length,import-ordering", "-F", "src/**/*.kt")
}

afterEvaluate {
    tasks.named("check") {
        dependsOn(ktlintCheck)
    }
}
