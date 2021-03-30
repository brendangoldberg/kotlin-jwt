plugins {
    kotlin("jvm")
    id("kotlinx-serialization")
    id("jacoco")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

group = "com.brendangoldberg"
version = Config.version

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(Deps.Serialization.core)

    testImplementation(Deps.Jupiter.core)
    testImplementation(Deps.Google.Truth.core)
}

val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"

fun getRepoUrl(): String {
    if (version.toString().endsWith("SNAPSHOT")) {
        return snapshotsRepoUrl
    }
    return releasesRepoUrl
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        finalizedBy(jacocoTestReport)
    }

    mavenPublish {
        nexus {
            baseUrl = "https://s01.oss.sonatype.org/service/local/"
            repositoryUsername = project.property("mavenCentralRepositoryUsername").toString()
            repositoryPassword = project.property("mavenCentralRepositoryPassword").toString()
        }
    }
}

publishing {
    repositories {
        withType<MavenArtifactRepository> {
            if (name == "local") {
                return@withType
            }
            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(getRepoUrl())
            credentials {
                username = project.property("mavenCentralRepositoryUsername").toString()
                password = project.property("mavenCentralRepositoryPassword").toString()
            }
        }
    }
}