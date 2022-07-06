import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    id("com.github.rodm.teamcity-environments") version "1.4.1"
    id("com.github.rodm.teamcity-server") version "1.4.1"
}

group = "com.jamel"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.aliyun:alibaba-dingtalk-service-sdk:2.0.0") {
        exclude("log4j", "log4j")
    }
    implementation("cn.hutool:hutool-all:+")

    compileOnly("org.jetbrains.teamcity:server-api:+")
//    compileOnly("org.jetbrains.teamcity:server-web-api:+")
    testImplementation(kotlin("test"))
}


repositories {
//    maven {
//        url = uri("https://maven.aliyun.com/repository/public/")
//    }
    mavenCentral()
//    google()
//    maven {
//        url = uri("https://download.jetbrains.com/teamcity-repository")
//    }
//    maven {
//        url = uri("https://plugins.gradle.org/m2/")
//    }
//    maven {
//        url = uri("https://repo1.maven.org/maven2")
//    }
//    maven {
//        url = uri("https://plugins.gradle.org/m2")
//    }
}

teamcity {
    version = "2022.04"

    server {
        descriptor = file("${rootDir}/teamcity-plugin.xml")
        archiveName = "dingtalk"

        publish {
            channels = mutableListOf("Beta")
            token = "perm:SmFtZWwtanVu.OTItNjM1Mw==.5tarxmhNtmgi3cK06KoAqbSsT7KxfB"
            notes = "change notes"
        }
    }

    environments {
        baseHomeDir = "/Users/jamel/Downloads/Profiles/teamcity/gradle202204"
        downloadsDir = "/Users/jamel/Downloads/Profiles/teamcity/gradle202204"
        baseDataDir = "${rootDir}/data"

        create("teamcity2022.04") {
            version = "2022.04"
        }
    }
}

buildscript {
    repositories {
        mavenCentral()
        google()
        maven {
            url = uri("https://download.jetbrains.com/teamcity-repository")
        }
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven {
            url = uri("https://repo1.maven.org/maven2")
        }
        maven {
            url = uri("https://plugins.gradle.org/m2")
        }
    }
    dependencies {
        classpath("com.github.rodm:gradle-teamcity-plugin:1.4.1")
    }

}
tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}