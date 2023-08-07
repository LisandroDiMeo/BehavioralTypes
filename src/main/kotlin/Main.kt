import kotlinx.coroutines.*
import java.net.ServerSocket

/**
 * Added this simple program to launch in two coroutines both server and client
 * and see how they work.
 */
fun main(args: Array<String>) {
    val clientReadingMode = args.getOrNull(0) ?: "NORMAL"

    runBlocking {
        launch(Dispatchers.IO) {
            val serverSocket = ServerSocket(1234)
            serverSocket.accept().apply {
                FileServerThread(this).start()
            }
        }
        val clientJob = Job()
        launch(clientJob) {
            val client = if (clientReadingMode == "NORMAL") FileClient() else FastFileClient()
            println("CLIENT: Reading at $clientReadingMode speed")
            if (client.start()) {
                if (client.request("example.txt")) {
                    client.close()
                }
            } else {
                println("Could not start client!")
            }
        }
    }
}