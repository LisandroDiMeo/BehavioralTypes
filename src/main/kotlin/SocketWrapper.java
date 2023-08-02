import java.net.Socket;

public class SocketWrapper {
    public Socket socket;
    SocketWrapper(Socket s) {
        socket = s;
    }

    public Socket getSocket() {
        return socket;
    }
}
