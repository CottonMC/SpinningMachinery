plugins {
    java
}

base {
    archivesBaseName = "SpinningJsonPlugin"
}

version = rootProject.version

dependencies {
    val jsonFactory = "0.5.0-beta.2-SNAPSHOT"
    implementation("io.github.cottonmc:json-factory:$jsonFactory")
    implementation("io.github.cottonmc:json-factory-gui:$jsonFactory")
    implementation("com.google.guava:guava:21.0")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}
