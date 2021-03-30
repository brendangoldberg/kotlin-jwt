buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.30"))
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.30")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.30")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.14.2")
    }

}

allprojects {
    repositories {
        mavenCentral()
        maven(url = "https://dl.bintray.com/kotlin/dokka")
        jcenter {
            content {
                includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            }
        }
    }
}