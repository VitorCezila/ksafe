// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("org.sonarqube") version "4.0.0.2929"
    id("jacoco")
}

apply(plugin = "jacoco")

subprojects {
    afterEvaluate {
        project.apply(from = "../jacoco-report.gradle.kts")
        sonar {
            properties {
                property("sonar.projectName", "PasswordManager")
                property("sonar.projectKey", "PasswordManager")
                property("sonar.host", "http://localhost:9000")
                property("sonar.sources", "src/main/java")
                property("sonar.tests", "src/test/")
                property("sonar.core.codeCoveragePlugin", "jacoco")
                property("sonar.language", "kotlin")
                property("sonar.sourceEncoding", "UTF-8")
                property("sonar.verbose", true)
                property(
                    "sonar.androidLint.reportPaths",
                    "$buildDir/reports/lint-results-debug.xml"
                )
                property(
                    "sonar.junit.reportPaths",
                    "$buildDir/test-results/testDebugUnitTest/"
                )
                property(
                    "sonar.jacoco.reportPaths",
                    "$buildDir/reports/jacocoHtml/"
                )
                property(
                    "sonar.exclusions", "**/samples/**" +
                            "**/*Test*/**" +
                            "build/**" +
                            "*.json," +
                            "**/*test*/**," +
                            "**/.gradle/**," +
                            "**/R.class"
                )
            }
        }

        rootProject.tasks["sonar"]
            .dependsOn("${this.path}:test")
            .mustRunAfter("${this.path}:clean")
    }
}