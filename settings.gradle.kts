import org.gradle.kotlin.dsl.support.listFilesOrdered

rootProject.name = "advent-of-code-2022"

include("shared")

settingsDir
    .listFilesOrdered()
    .filter { it.isDirectory && it.name.startsWith("day") }
    .forEach {
        include(it.name)
    }

