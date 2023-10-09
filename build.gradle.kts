// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.sonarqube") version "4.0.0.2929"
    id("jacoco")
}

apply(plugin = "jacoco")

subprojects {
    afterEvaluate {
        project.apply(from = "../jacoco-report.gradle.kts")
        sonarqube {

        }
    }
}