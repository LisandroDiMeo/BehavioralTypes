import jatyc.lib.Typestate;
import java.net.*;
import java.io.*;
import java.util.*;

@Typestate("FileServer")
public class FileServer {
    private Socket socket;
    protected OutputStream out;
    protected BufferedReader in;
    protected String lastFilename;

    private String request = "";

    public boolean start(SocketWrapper socketWrapper) {
        try {
            socket = socketWrapper.getSocket();
            out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean attendRequest() {
        if (request.equals("REQUEST")) {
            try {
                String fileName = in.readLine();
                if (fileName == null)
                    fileName = "";
                System.out.println("SERVER: Reading " + fileName);
                File fileToRead = new File(fileName);
                Scanner scanner = new Scanner(fileToRead);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine() + "\n";
                    System.out.println("SERVER: Sending line: \n" + line);
                    out.write(line.getBytes());
                }
                String eof = "EOF\n";
                out.write(eof.getBytes());
                System.out.println("SERVER: File content sended...");
                return true;
            }
            catch (IOException failedToRead) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean hasRequest() {
        String command;
        try {
            if (in != null){
                command = in.readLine();
                if (command != null){
                    if (command.equals("REQUEST") || command.equals("CLOSE")) {
                        request = command;
                        return true;
                    }
                }
                else {
                    return false;
                }
            }
            else
                return false;
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public void close() {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (socket != null)
                socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        new FileServerThread(serverSocket.accept()).start();
    }
}
