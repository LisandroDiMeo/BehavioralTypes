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
                File fileToRead = new File(fileName);
                Scanner scanner = new Scanner(fileToRead);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    out.write(line.getBytes());
                }
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
//        if (command != null && command.equals("REQUEST")) {
//            // Read another line (since we send two lines).
//            String fileName = "";
//            try {
//                if (in != null)
//                    fileName = in.readLine();
//            } catch (IOException e ) {
//                fileName = "";
//            }
//            if (fileName == null) {
//                fileName = "";
//            }
//            File fileToRead = new File(fileName);
//            try {
//                Scanner scanner = new Scanner(fileToRead);
//                while(scanner.hasNext()) {
//                    String line = scanner.nextLine();
//                    if (out != null)
//                        out.write(line.getBytes());
//                }
//                return RequestState.FILE_SENT;
//            } catch(FileNotFoundException e) {
//                String fileNotFound = "FILE_NOT_FOUND\n";
//                out.write(fileNotFound.getBytes());
//                return RequestState.FILE_NOT_FOUND;
//            }
//        } else if (command != null && command.equals("CLOSE")) {
//            return RequestState.CLOSE_PROTOCOL;
//        }
//        return RequestState.NO_REQUEST;
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
