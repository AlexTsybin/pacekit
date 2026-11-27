import java.net.URI

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.dokka)

    `maven-publish`
}

val pacekitVersion = providers.gradleProperty("pacekitVersion")
group = "io.github.alextsybin"
version = pacekitVersion.get()

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
        moduleVersion.set(pacekitVersion)
        suppressInheritedMembers.set(true)
        failOnWarning.set(true)
    }

    dokkaSourceSets.main {
        reportUndocumented.set(true)

        samples.from("src/test/kotlin/io/github/alextsybin/pacekit/samples")

        sourceLink {
            localDirectory.set(file("src/main/kotlin"))

            remoteUrl.set(
                URI(
                    "https://github.com/AlexTsybin/pacekit/" +
                            "blob/main/pacekit/src/main/kotlin"
                )
            )

            remoteLineSuffix.set("#L")
        }
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

                url.set("https://github.com/AlexTsybin/pacekit")

                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set(
                            "https://www.apache.org/licenses/LICENSE-2.0.txt"
                        )
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("AlexTsybin")
                        name.set("Alex Tsybin")
                        url.set("https://github.com/AlexTsybin")
                    }
                }

                scm {
                    url.set(
                        "https://github.com/AlexTsybin/pacekit"
                    )

                    connection.set(
                        "scm:git:https://github.com/AlexTsybin/pacekit.git"
                    )

                    developerConnection.set(
                        "scm:git:ssh://git@github.com/AlexTsybin/pacekit.git"
                    )
                }
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
