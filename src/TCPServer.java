import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TCPServer {
    public static void main(String args[]) throws Exception {
        String clientSentence;
        String[] clientSentenceArray;
        String outputSentence = "";

        ServerSocket welcomeSocket = new ServerSocket(6789);


        while(true) {
            System.out.println("Waiting for connection");
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Connected");

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);

            clientSentenceArray = clientSentence.split(" ");

            for (int i = 0; i < clientSentenceArray.length; i++) {
                String word = clientSentenceArray[i];

                char wordEnd = word.charAt(word.length() - 1);
                boolean wordEndPunctuation = false;
                if (wordEnd == '?' || wordEnd == '!' || wordEnd == '.' || wordEnd == ',') {
                    wordEndPunctuation = true;
                }

                word = word.replaceAll("[?!.,]", "");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("codebook.txt"));
                    String line;
                    boolean isCodeFound = false;
                    while((line = reader.readLine()) != null) {
                        if (line.split("\t")[0].equals(word)) {
                            isCodeFound = true;
                            if (wordEndPunctuation) {
                                outputSentence += ' ' + line.split("\t")[1] + wordEnd;
                            } else {
                                outputSentence += ' ' + line.split("\t")[1];
                            }
//                            clientSentenceArray[i] = line.split("\t")[1];
//                            System.out.println("Will return to client: " + Arrays.toString(clientSentenceArray));
                            System.out.println("Building: " + outputSentence);
                        }
                    }
                    if (!isCodeFound) {
                        if (wordEndPunctuation) {
                            outputSentence += ' ' + word + wordEnd;
                        } else {
                            outputSentence += ' ' + word;
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Send: " + outputSentence);
            outToClient.writeBytes(outputSentence);
            outputSentence = "";
            outToClient.close();
        }
    }
}
