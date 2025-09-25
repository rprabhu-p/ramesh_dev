import java.util.*;

public class SnakeLaddeTest {
    public static void main (String args[]) {
        Game g = new Game();
        g.start();
    }
}

class Game{

    final static int win = 100;

    Map<Integer, Integer> s = new HashMap<>();
    Map<Integer, Integer> l = new HashMap<>();

    {
        s.put(98, 55);
        s.put(87, 66);
        s.put(70, 50);
        s.put(60, 49);
        s.put(40, 25);

        l.put(2, 22);
        l.put(9, 33);
        l.put(20, 42);
        l.put(27, 73);
        l.put(52, 97);
    }

    public int roll(){
        int n = 0;
        Random r = new Random();
        n = r.nextInt(7);
        return (n == 0 ? 1 : n);
    }

    public int calculatPosition(int p, int d) {
        int np = p + d;

        if(np > win) return p; 

        if(s.get(np) != null){
            System.out.println("Snake bites");
            np = s.get(np);
        }
        if(l.get(np) != null){
            System.out.println("Got Ladder");
            np = l.get(np);
        }

        return np;
    }

    public boolean isWin(int p){
        return (p == win ? true : false);
    }

    public void start(){
        int p1 = 0, p2 = 0, cur = -1;
        String play = null;
        int diceroll = 0;
        Scanner sc = new Scanner(System.in);

        do{
            System.out.println(cur == -1 ? "\nP1 Turn": "\nP2 Turn");
            System.out.print("Press \"R\" to Play: ");

            play = sc.nextLine();
            diceroll = roll();
            System.out.println("P1 Got :" + diceroll);

            if(cur == -1) {
                p1 = calculatPosition(p1, diceroll);
                System.out.println("P1's Position:" + p1);
                System.out.println("P2's Position:" + p2);
                if(isWin(p1)){
                    System.out.println("P1 Win.");
                    sc.close();
                    return;
                }
            }else {
                p2 = calculatPosition(p2, diceroll);
                System.out.println("P1's Position:" + p1);
                System.out.println("P2's Position:" + p2);
                if(isWin(p2)){
                    System.out.println("P2 Win.");
                    sc.close();
                    return;
                }
            }

            cur = -cur;

        } while("R".equals(play));

        sc.close();
        return;
    }
}
