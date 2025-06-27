import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scaner = new Scanner(System.in);
        Random random = new Random();
        int secretNumber = random.nextInt(100)+ 1;
        int attempts = 0;
        int guess;
        System.out.println(" Привет , я загадал число от 1 до 100 попробуй отгадать!");
        while(true){
            System.out.println("Твоя догадка:");
            guess = scaner.nextInt();
            attempts++;
            if(guess < secretNumber){
                System.out.println("Слишком маленькое число");
            } else if(guess > secretNumber){
                System.out.println("Слишком большое число");
            }else {
                System.out.println("Ура ты победил");

                break;
            }
        }
        scaner.close();

    }
}