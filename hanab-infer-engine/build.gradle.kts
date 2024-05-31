plugins {
    alias(libs.plugins.sonar)
    kotlin("jvm") version "2.0.0"
}

group = "io.github.tacascer"

dependencies {
    testImplementation(kotlin("test"))
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
    }
}
