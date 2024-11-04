import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class SnakeLadder {
    public static void main(String args[]) {
        Gameplay game = new Gameplay();
        game.start();
    }
}

class Gameplay {
    final static int winpoint = 100;

    static Map<Integer, Integer> snake = new HashMap<>();
    static Map<Integer, Integer> ladder = new HashMap<>();

    {
        snake.put(99, 54);
        snake.put(70, 55);
        snake.put(52, 42);
        snake.put(25, 2);
        snake.put(95, 72);

        ladder.put(6, 25);
        ladder.put(11, 40);
        ladder.put(60, 85);
        ladder.put(46, 90);
        ladder.put(17, 69);
    }

    //Rondam number generate
    public int rollDice() {
        int n = 0;
        Random r = new Random();
        n = r.nextInt(7);
        return (n == 0 ? 1 : n);
    }

    public int calculatePlayerValue(int position, int diceValue) {
        int newPosition = position + diceValue;

        if(newPosition > winpoint) return position;

        if(snake.get(newPosition) != null) {
            System.out.println("Oops..Swallowed by snake..");
            newPosition = snake.get(newPosition);
        }

        if(ladder.get(newPosition) != null) {
            System.out.println("Yay!, Climping the ladder..");
            newPosition = ladder.get(newPosition);
        }

        return newPosition;
    }

    public boolean isWin(int position) {
        return winpoint == position;
    }

    public void start() {
        int player1 = 0, player2 = 0;
        int currPlayer = -1;

        Scanner input = new Scanner(System.in);

        String rpressed = null;

        int diceRoll = 0;

        do {
            System.out.println(currPlayer == -1 ? "\n\nPlayer1's turn:" : "\n\nPlayer2's turn:");
            System.out.println("press \"r\" to rollm the dice");
            
            rpressed = input.next();
            diceRoll = rollDice();

            if(currPlayer == -1) {
                player1 = calculatePlayerValue(player1, diceRoll);
                System.out.println("Player1's position:" + player1);
                System.out.println("Player2's position:" + player2);
                System.out.println("-----------------------------");
                if(isWin(player1)) {
                    System.out.println("Congratulations! First player won");
                    input.close();
                    return;
                }
            } else {
                player2 = calculatePlayerValue(player2, diceRoll);
                System.out.println("Player1's position:" + player1);
                System.out.println("Player2's position:" + player2);
                System.out.println("-----------------------------");
                if(isWin(player2)) {
                    System.out.println("Congratulations! Second player won");
                    input.close();
                    return;
                }
            }
            System.out.println("Current : " + currPlayer);
            currPlayer = -currPlayer;

        }while("r".equals(rpressed));

        input.close();
        return;
    }
}