import kotlinx.coroutines.*
import java.net.ServerSocket

fun main(args: Array<String>) {
    runBlocking {
        launch(Dispatchers.IO) {
            val serverSocket = ServerSocket(1234)
            serverSocket.accept().apply {
                FileServerThread(this).start()
            }
        }
        val clientJob = Job()
        launch(clientJob) {
            val client = FileClient()
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