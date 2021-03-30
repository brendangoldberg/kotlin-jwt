buildscript {

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.30"))
    }
}

plugins {
    kotlin("jvm")
    id("kotlinx-serialization")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(Deps.Serialization.core)

    implementation("com.brendangoldberg:kotlin-jwt:1.3.1")
}