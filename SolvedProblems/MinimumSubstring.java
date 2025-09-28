import java.util.*;

public class MinimumSubstring {

    public static String minOrderedWindow(String s, String p) {
        int n = s.length();
        int m = p.length();
        if (m == 0 || n == 0 || m > n) return "";

        Map<Character, Integer> pFreq = new HashMap<>();
        for (char c : p.toCharArray()) {
            pFreq.put(c, pFreq.getOrDefault(c, 0) + 1);
        }

        String result = "";
        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            // Try to match pattern from index i if it matches first character
            if (s.charAt(i) == p.charAt(0)) {
                int si = i;
                int pi = 0;
                Map<Character, Integer> windowFreq = new HashMap<>();
                StringBuilder temp = new StringBuilder();

                while (si < n && pi < m) {
                    char sc = s.charAt(si);
                    temp.append(sc);
                    if (sc == p.charAt(pi)) {
                        windowFreq.put(sc, windowFreq.getOrDefault(sc, 0) + 1);
                        if (windowFreq.get(sc) > pFreq.get(sc)) {
                            break;
                        }
                        pi++;
                    }
                    si++;
                }
                if (pi == m) {
                    boolean valid = true;
                    for (char c : pFreq.keySet()) {
                        if (!windowFreq.getOrDefault(c, 0).equals(pFreq.get(c))) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        String window = temp.toString();
                        if (window.length() < minLen || 
                            (window.length() == minLen && window.compareTo(result) < 0)) {
                            result = window;
                            minLen = window.length();
                        }
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter string s: ");
        String s = scanner.nextLine();

        System.out.print("Enter pattern p: ");
        String p = scanner.nextLine();

        String result = minOrderedWindow(s, p);
        System.out.println("Output: " + (result.isEmpty() ? "(empty string)" : result));
    }
}
