plugins {
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
    kotlin("jvm") version "2.1.0"
}

group = "io.github.tacascer"

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.kotest)
    testImplementation(libs.kotest.property)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_hanab-infer_engine")
        property("sonar.organization", "tacascer-org")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.file("reports/kover/report.xml").get()}",
        )
    }
}

tasks.sonar {
    dependsOn(tasks.koverXmlReport)
}
