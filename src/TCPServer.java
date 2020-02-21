import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String args[]) throws Exception {
        String clientSentence;
        String[] clientSentenceArray;
        String outputSentence = "";

        ServerSocket welcomeSocket = new ServerSocket(6789);


        while(true) {
            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            clientSentenceArray = clientSentence.split(" ");

            for (String word : clientSentenceArray) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("codebook.txt"));
                    String line;
                    while((line = reader.readLine()) != null) {
                        if (line.split("\t")[0].equals(word)) {
                            outputSentence += ' ' + line.split("\t")[1];
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            outToClient.writeBytes(outputSentence);
        }
    }
}
