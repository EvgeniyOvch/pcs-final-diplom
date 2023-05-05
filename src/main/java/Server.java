import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private final int port;
    private final BooleanSearchEngine searchResult;

    public Server(int port) throws IOException {
        this.port = port;
        searchResult = new BooleanSearchEngine(new File("pdfs"));
    }

    public void serverStart() {
        System.out.println("Сервер запущен на порту " + port + " ...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream())) {

                    String answer = in.readLine();
                    List<PageEntry> resultList = searchResult.search(answer);

                    ParserToJson parserToJson = new ParserToJson();
                    String gson = parserToJson.listToJson(resultList);
                    out.println(gson);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}