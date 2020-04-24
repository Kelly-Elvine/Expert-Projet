import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 9090;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP,PORT);

        ServerConnection serverConnection =  new ServerConnection(socket);

        BufferedReader keyBoard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        new Thread(serverConnection).start();

    while (true){
        System.out.println("> ");
        String command = keyBoard.readLine();

        if(command.equals("quit")) break;

        out.println(command);
    }

        socket.close();
        System.exit(0);

    }
}
