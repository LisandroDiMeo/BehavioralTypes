import java.net.*;

public class FileServerThread extends Thread {
    private Socket socket;

    public FileServerThread(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        FileServer server = new FileServer();
        SocketWrapper socketWrapper = new SocketWrapper(socket);
        if (server.start(socketWrapper)) {
            while (true){
                if (server.hasRequest()) {
                    if(!server.attendRequest()) {
                        server.close();
                        break;
                    }
                }
            }
        }
    }
}
