import jatyc.lib.Typestate;

@Typestate("FastFileClient")
public class FastFileClient extends FileClient {
    @Override
    public boolean request(String filename) throws Exception {
        out.write("REQUEST\n".getBytes());
        byte[] fileNameBytes = (filename + "\n").getBytes();
        out.write(fileNameBytes);
        StringBuilder fileContent = new StringBuilder();
        String line = "";
        boolean fileExists = false;
        while ((line = in.readLine()) != null) {
            if (line == null || line.equals("FILE_NOT_FOUND")) {
                System.out.println("CLIENT: File not found!");
                return false;
            } else {
                fileExists = true;
                if (line.equals("EOF")) break;
                fileContent.append(line + "\n");
                System.out.println("CLIENT: Received " + line + "\n");
            }
        }
        System.out.println("CLIENT: Content received was:\n" + fileContent);
        return fileExists;
    }

    public static void main(String[] args) throws Exception {
        FileClient client = new FastFileClient();
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
