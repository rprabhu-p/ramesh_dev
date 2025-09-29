
import java.util.*;

class SnakeLadderTest {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

class Board {
    final static int WinPoint = 100;
    Map<Integer, Integer> snakes = new HashMap<>();
    Map<Integer, Integer> ladders = new HashMap<>();

    public Board() {
        snakes.put(99, 54);
        snakes.put(70, 55);
        snakes.put(52, 42);
        snakes.put(25, 2);
        snakes.put(95, 72);

        ladders.put(6, 25);
        ladders.put(11, 40);
        ladders.put(60, 85);
        ladders.put(46, 90);
        ladders.put(17, 69);
    }

    public void displayInfo() {
        System.out.println("Snakes : " + snakes);
        System.out.println("Ladders : " + ladders);
    }

    public int checkPosition(int position) {
        if(snakes.containsKey(position)) {
            System.out.println("Snake bites...");
            return snakes.get(position);
        }
        if(ladders.containsKey(position)) {
            System.out.println("Climbing ladder...");
            return ladders.get(position);
        }

        return position;
    }

    public boolean isWin(int position){
        return position == WinPoint;
    }
}

class Dice {
    private final Random r = new Random();

    public int roll(){
        return r.nextInt(6) + 1;
    }
}

class Players {
    private final String name;
    private int position;
    private boolean started;

    public Players(String name) {
        this.name = name;
        this.position = 0;
        this.started = false;
    }

    public String getName(){
        return name;
    }

    public int getPosition(){
        return position;
    }

    public void move(int step, Board board){

        if(!started && step != 6){
            System.out.println(name + "need a 6 to start!");
            return;
        }
        if(!started && step == 6){
            started = true;
            System.out.println(name + "started the journey!");
        }

        int newPosition = position + step;
        if(newPosition > Board.WinPoint) {
            System.out.println(name + " rolled too high, stays at " + position);
            return;
        }

        newPosition = board.checkPosition(newPosition);
        position = newPosition;
        System.out.println(name + " moved to  position "+ position);
    }

    public boolean hasWon(Board board) {
        return board.isWin(position);
    }

}

class Game {
    private final Board board = new Board();
    private final Dice dice = new Dice();
    private final Scanner sc = new Scanner(System.in);
    private final List<Players> playList = new ArrayList<>();

    public void start() {
        System.out.println("Welcome to Snake Ladder game!.");
        board.displayInfo();

        setupPlayer();

        boolean gameOver = false;
        while(!gameOver){
            for(Players p : playList){
                System.out.println("\n" + p.getName() + "'s turn. Press \"r\" to roll dice:");
                String input = sc.nextLine();
                if(!"r".equalsIgnoreCase(input)){
                    System.out.println("Skipping turn...");
                    continue;
                }

                int roll = dice.roll();
                System.out.println(p.getName() + " rolled a " + roll);

                p.move(roll, board);

                if(p.hasWon(board)) {
                    System.out.println("Congratulations! " + p.getName() + " won the game!");
                    gameOver = true;
                    break;
                }
            }
        }
        sc.close();
    }

    public void setupPlayer(){
        System.out.print("Enter no of Players:");
        int n = sc.nextInt();
        sc.nextLine();

        for(int i = 1; i <= n; i++){
            System.out.print("Enter name for player "+ i + ":");
            String name = sc.nextLine();
            playList.add(new Players(name));
        }
    }
}