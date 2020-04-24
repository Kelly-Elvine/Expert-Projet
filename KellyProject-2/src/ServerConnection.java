import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private Socket server;
    private BufferedReader in ;

    public ServerConnection(Socket s) throws IOException {
        server  = s;
        in  = new BufferedReader(new InputStreamReader(server.getInputStream()));

    }


    @Override
    public void run() {
        int count =0;

        try{
            while (true) {
                count++;
                String serverResp = in.readLine();
                if(serverResp == null)break;

                else if(count==1){
                    System.out.println("[Server]>: " + serverResp);
                }else{
                    System.out.println("[Client Says]>: " + serverResp);

                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}

