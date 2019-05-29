plugins {
    java
    idea
    id("fabric-loom") version "0.2.4-SNAPSHOT"
}

group = "io.github.cottonmc"

base {
    archivesBaseName = "SpinningMachinery"
}

val minecraft: String by ext
val modVersion = ext["mod-version"] ?: error("Version was null")
val localBuild = ext["local-build"].toString().toBoolean()
version = "$modVersion+$minecraft" + if (localBuild) "-local" else ""

if (localBuild) {
    println("Note: local build mode enabled in gradle.properties; all dependencies might not work!")
}

repositories {
    mavenCentral()
    if (localBuild) {
        mavenLocal()
    }

    // For cotton
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-release")
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-snapshot")

    // For clothesline and developer-mode
    maven(url = "https://maven.jamieswhiteshirt.com/libs-release/")
}

tasks.getByName<ProcessResources>("processResources") {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand(
            mutableMapOf(
                "version" to project.version
            )
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.getByName<Jar>("jar") {
    from("LICENSE")
}

minecraft {
}

inline fun DependencyHandler.includedMod(str: String, block: ExternalModuleDependency.() -> Unit = {}) {
    modImplementation(str, block)
    include(str, block)
}

dependencies {
    /**
     * Gets a version string with the [key].
     */
    fun v(key: String) = ext[key].toString()

    /**
     * Used for excluding Fabric API 0.2.x.
     */
    val excludeOldFabric: ExternalModuleDependency.() -> Unit = { exclude(module = "fabric") }

    minecraft("com.mojang:minecraft:$minecraft")
    mappings("net.fabricmc:yarn:" + v("minecraft") + '+' + v("mappings"))

    // Fabric
    modApi("net.fabricmc:fabric-loader:" + v("fabric-loader"))
    modApi("net.fabricmc.fabric-api:fabric-api:" + v("fabric-api"))

    // Other mods
    modApi("com.jamieswhiteshirt:clothesline-fabric:0.0.16", excludeOldFabric)
    includedMod("io.github.cottonmc:cotton:" + v("cotton"), excludeOldFabric)
    modRuntime("com.jamieswhiteshirt:developer-mode:1.0.11")
}
