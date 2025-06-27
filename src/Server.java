import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.*;

import static sun.management.jmxremote.ConnectorBootstrap.PropertyNames.PORT;

public class Server {
    private static final int MAX_PLAYERS = 10;
    private static final int PORT = 5555;
    private static int secretNumber;
    private static boolean gameRunning = false;
    private static List<ClientHandler> client = new ArrayList<Object>();
    public static void main(String[] args){
        System.out.println("Сервер запущен, ожидание игроков" + PORT + "...");
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
                Socket clientSocket = serverSocket.accept();
            if(client.size()>= MAX_PLAYERS){
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("SERVER_FULL");
                clientSocket.close();
                continue;
            }
            ClientHander clientHander = new ClientHander(clientSocket);

            }
        }

    }
    private static class ClientHander extends Thread{


    }

}

