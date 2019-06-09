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

allprojects {
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

        // For REI's deps
        maven(url = "https://minecraft.curseforge.com/api/maven") {
            name = "CurseForge"
        }

        maven(url = "https://mod-buildcraft.com/maven")
        maven(url = "http://maven.abusedmaster.xyz/")
    }
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
    modImplementation("net.fabricmc:fabric-loader:" + v("fabric-loader"))
    modImplementation("net.fabricmc.fabric-api:fabric-api:" + v("fabric-api"))

    // Required and bundled mods
    modImplementation("com.jamieswhiteshirt:clothesline-fabric:" + v("clothesline"), excludeOldFabric)
    modImplementation("io.github.cottonmc:cotton-resources:" + v("cotton-resources"), excludeOldFabric)
    includedMod("io.github.cottonmc:cotton:" + v("cotton"), excludeOldFabric)

    // Mods with compat
    modImplementation("me.shedaniel:RoughlyEnoughItems:" + v("rei"))
    modImplementation("vivatech:vivatech:" + v("vivatech"))
    modImplementation("refined-machinery:RefinedMachinery:" + v("refined-machinery"))

    // Dev env mods
    modRuntime("com.jamieswhiteshirt:developer-mode:" + v("developer-mode"))
    modRuntime("io.github.cottonmc:cotton-energy:" + v("cotton-energy"), excludeOldFabric)
    modRuntime("abused_master.abusedlib:AbusedLib:" + v("abusedlib"), excludeOldFabric)
    modRuntime("com.github.NerdHubMC:Cardinal-Energy:" + v("cardinal-energy"), excludeOldFabric)
    modRuntime("com.github.NerdHubMC:Cardinal-Components-API:" + v("cardinal-components-api"), excludeOldFabric)

    // Other libraries
    compileOnly("com.google.code.findbugs:jsr305:3.0.2") { isTransitive = false }
}
