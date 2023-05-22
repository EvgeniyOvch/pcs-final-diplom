import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final int PORT = 8989;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        try (Socket clientSocket = new Socket(HOST, PORT)) {
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("БиЗнес");

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        }
    }
}
