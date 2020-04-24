import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;
    private ArrayList<String> etudes = new ArrayList<>();
    public ClientHandler(Socket clientSo, ArrayList<ClientHandler> clientHandlers) throws IOException {
        this.client = clientSo;
        this.clients = clientHandlers;
        in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);
    }

    @Override
    public void run() {

        try{
            while(true){
                String req = in.readLine();

                if(!req.isEmpty()){

                    outToAll(req);
                }

                else{
                    out.println("Le message est vide");
                }

            }
        } catch (IOException e) {
            System.err.println("IOE exception in ClientHandler");
            System.err.println(e.getStackTrace());
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void outToAll(String msg) {
        for(ClientHandler client: clients){

            client.out.println(stringProbabilityDelete(trimString(msg)));
        }
    }


    public String trimString(String msg){

        //On peut rajouter d'autres mots-clés que le serveur va supprimer dans le message du 1er client.

        etudes.add("étude");
        etudes.add("étudiant");
        etudes.add("note");

        for (String m:etudes) {
            if(msg.toLowerCase().contains(m)){

                String tempWord = m + "";
                msg = msg.toLowerCase().replaceAll(tempWord, "*****");
            }
        }

        return msg;

    }



    public int randomNumber(){

        int min = 1;
        int max = 4;
        int range = max-min +1;
        //trouve un chiffre aleatoire entre 1-4
        int random =((int) (Math.random() * range) +min);
        return random;

    }

    public String stringProbabilityDelete(String msg){

        StringBuilder newMsg = new StringBuilder(msg);

        int i =0;
        //trouver la longeur de 30% du message
        int length = Math.round(newMsg.length()*30/100);

        //appelle la fonction random,si elle retourne 3 ;il est executé ce qu'il y'a à l'intérieur de la fonction (on peut choisir n'importe quoi comme chiffre à condition qu'il soit compris entre 1-4)
        if(randomNumber() == 3){

            while(i < length) {

                //On remplace par le Char choisi à partir la longueur 'i' dans le String Builder. Puisqu'on avait calculé la longueur du StringBuilder; ici il ne sera remplacé par le Char que les 30% de ce dernier.
                newMsg.setCharAt(i,'x');
                i++;
            }

        }
        return newMsg.toString();

    }


}
