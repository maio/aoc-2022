package cz.maio

object Utils {}

fun readResource(name: String): String {
    val resource = Utils.javaClass.getResource(name) ?: error("Resource $name not found")
    return resource.readText()
}
