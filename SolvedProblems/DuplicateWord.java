import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DuplicateWord {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();

        sc.close();

        Map<Character, Integer> m1 = new HashMap<>();
        for(char c : input.toCharArray()){
            m1.put(c, m1.getOrDefault(c, 0) + 1);
        }

        for(Map.Entry<Character, Integer> entry : m1.entrySet()){
            if(entry.getValue() > 1) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }
    }
}
