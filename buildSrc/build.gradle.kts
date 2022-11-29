plugins {
    `kotlin-dsl`
}

val kotlinVersion = "1.7.21"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}

repositories {
    gradlePluginPortal()
}
