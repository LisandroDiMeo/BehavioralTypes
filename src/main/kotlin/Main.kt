import java.io.File

fun main(args: Array<String>) {
    println("Hello World!")

    val f = File("example.txt")
    f.bufferedReader().lines().forEach { println(it) }

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}