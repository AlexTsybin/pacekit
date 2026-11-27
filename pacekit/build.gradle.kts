plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.dokka)

    `maven-publish`
}

group = "io.github.alextsybin"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withSourcesJar()
}
kotlin {
    explicitApi()

    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation {
        enabled.set(true)
    }

    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dokka {
    dokkaPublications.html {
        moduleName.set("PaceKit")
        moduleVersion.set("0.1.0")
        suppressInheritedMembers.set(true)
        failOnWarning.set(true)
    }

    dokkaSourceSets.main {
        reportUndocumented.set(true)

        samples.from("src/test/kotlin/io/github/alextsybin/pacekit/samples")
    }
}

val dokkaJavadocJar = tasks.register<Jar>("dokkaJavadocJar") {
    description = "Packages Dokka HTML documentation"
    group = "documentation"

    archiveClassifier.set("javadoc")

    from(
        tasks.dokkaGeneratePublicationHtml.flatMap {
            it.outputDirectory
        }
    )
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "pacekit"

            from(components["java"])

            artifact(dokkaJavadocJar)

            pom {
                name.set("PaceKit")
                description.set(
                    "A type-safe Kotlin library for running pace, " +
                        "speed, and finish time calculations."
                )
            }
        }
    }
}

tasks.named<Jar>("sourcesJar") {
    from("src/test/kotlin") {
        include("io/github/alextsybin/pacekit/samples/**")
    }
}

dependencies {
    testImplementation(kotlin("test-junit"))
}
