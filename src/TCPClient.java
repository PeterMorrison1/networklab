import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
    public static void main(String args[]) throws Exception {
        while(true) {
            String sentence = "";
            String serverSentence;

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            Socket clientSocket = new Socket("192.168.1.65", 6789);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            sentence = inFromUser.readLine();
            if (sentence.matches("[@#$%&*()_+=|<>{}~]")) {
                clientSocket.close();
            }

            outToServer.writeBytes(sentence + '\n');

            serverSentence = inFromServer.readLine();

            System.out.println("FROM SERVER: " + serverSentence);
        }

        // Anything thats not a '?', '!', '.', ',' is considered a special character which ends the connection

    }
}
