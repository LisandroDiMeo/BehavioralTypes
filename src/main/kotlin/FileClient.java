import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileClient")
public class FileClient {
    private Socket socket;
    protected OutputStream out;
    protected BufferedReader in;
    protected int lastByte;

    public boolean start() {
        try {
            socket = new Socket("localhost", 1234);
            out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean request(String filename) throws Exception {
        out.write("REQUEST\n".getBytes());
        byte[] fileNameBytes = (filename + "\n").getBytes();
        out.write(fileNameBytes);
        StringBuilder fileContent = new StringBuilder();
        String line = "";
        boolean fileExists = false;
        while ((line = in.readLine()) != null) {
            if (line == null || line.equals("FILE_NOT_FOUND")) {
                return false;
            } else {
                fileExists = true;
                fileContent.append(line);
            }
        }
        return fileExists ? true : false;

    }

    public void close() throws Exception {
        out.write("CLOSE\n".getBytes());
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        FileClient client = new FileClient();
        if (client.start()) {
            System.out.println("File client started!");
            if(client.request("example.txt")){
                client.close();
            } else {
                // Skip
            }
        } else {
            System.out.println("Could not start client!");
        }
    }
}