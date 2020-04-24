import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 9090;

    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        while (true) {
            System.out.println("[Server] attend qu'un client se connecte......");
            Socket client = listener.accept();
            System.out.println("[Server] Connecté au client");


            ClientHandler clientHandler = new ClientHandler(client,clients);
            clients.add(clientHandler);
            if(clients.size() == 1){

                PrintWriter out = new PrintWriter(client.getOutputStream(),true);
                out.println("Jouons à un jeu de devinette,fais deviner à l'autre client le mot 'Etude' sans toute fois l'utiliser sinon je le supprimerai! ");
            }
            pool.execute(clientHandler);

        }


    }
}