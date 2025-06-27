import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private static final int MAX_PLAYERS = 10;
    private static final int PORT = 5555;
    private static int secretNumber;
    private static boolean gameRunning = false;
    private static List<ClientHandler> client = new ArrayList<ClientHandler>();
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
                ClientHandler clientThread = new ClientHandler(clientSocket);
            client.add(clientThread);
                clientThread.start();


            }
        }
        catch(IOException e){
            System.out.println("Ошибка сервера " + e.getMessage());
        }

    }
    public static synchronized void broadcast(String message){
        for(ClientHandler client : client){
            client.sendMessage(message);
        }
    }
    public static synchronized void removeClient(ClientHandler clients){
        client.remove(clients);
        System.out.println("Игрок отключился, Осталось игроков:" +client.size());
        broadcast("Игрок"+ clients.playerName+ "вышел из игры. Игроков онлайн:" + client.size());

    }
    public static synchronized void startGame(int number){
        secretNumber = number;
        gameRunning = true;
        broadcast("Игра началась, загадонно число, угадывай");
        System.out.println("Новая игра началась, агадываю число" + number);
    }
    public static synchronized void processGuess(ClientHandler clients, int guess){
        if(!gameRunning){
            clients.sendMessage("Игра не началась, ожидаем");
            return;
        }
        System.out.println(clients.playerName + "предложил"+ guess);
        broadcast("Игрок гадает" + clients.playerName + ":" + guess);
        if(guess< secretNumber){
            clients.sendMessage("Результат слишком мало");

        } else if (guess > secretNumber){
            clients.sendMessage("Слишком много");
        } else {
            gameRunning = false;
            broadcast("Победа" + clients.playerName + "Угадал число" + secretNumber + "!");
            System.out.println(clients.playerName + "Угадал число" + secretNumber + "!");
        }
    }
    private static class ClientHandler extends Thread{
        private Socket socket;
        private PrintWriter out;

        private BufferedReader in;
        private String playerName;
        public ClientHandler(Socket socket){
            this.socket = socket;

        }
        public void run(){
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                playerName = in.readLine();
                System.out.println(playerName + "Подключаемся" + socket.getInetAddress() + ")");
                broadcast("Новый игрок" + playerName + "Пресоидинился игроков онлайн" + client.size());
                out.println("Добро пожаловать в игру" + playerName + "Вы" + (client.size() == 1 ? "введущий": "участник"));

            }
        }
        public  void sendMessage(String message){
            out.println(message);
        }


    }

}

