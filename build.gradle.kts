plugins {
    java
    `java-library`
    `maven-publish`
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

apply {
    plugin("java")
}

//RECODE.RELEASE.PATCH.DEVELOPMENT
version = "1.0.0.0-SNAPSHOT"
group = "relampagorojo93"

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
    mavenLocal()

    //Papi
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    //Spigot
    maven("https://repo.md-5.net/content/repositories/snapshots/")
    maven("https://repo.md-5.net/content/repositories/releases/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {

    val mongoVersion = "3.10.2"
    implementation("org.mongodb:mongo-java-driver:$mongoVersion")

    val spigotVersion = "1.18.1-R0.1-SNAPSHOT"
    compileOnly("org.spigotmc:spigot:$spigotVersion")

    val papiVersion = "2.11.1"
    compileOnly("me.clip:placeholderapi:$papiVersion")

}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<ProcessResources>{
    filesMatching("**/*.yml"){
        expand(project.properties)
    }
}