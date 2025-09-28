import java.util.*;

public class MinimumSubstring {
    public static String findMinimumWindow(String s, String p) {
        int n = s.length();
        int m = p.length();
        if (n == 0 || m == 0 || m > n) return "";

        Map<Character, Integer> patternFreq = new HashMap<>();
        for(char c : p.toCharArray()){
            patternFreq.put(c, patternFreq.getOrDefault(c, 0) + 1);
        }

        String result = "";
        int minLength = Integer.MAX_VALUE;

        // Traverse string s
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == p.charAt(0)) {
                int si = i, pi = 0;
                Map<Character, Integer> windowFreq = new HashMap<>();
                StringBuilder candidate = new StringBuilder();

                while (si < n && pi < m) {
                    char sc = s.charAt(si);
                    candidate.append(sc);
                    if (sc == p.charAt(pi)) {
                        windowFreq.put(sc, windowFreq.getOrDefault(sc, 0) + 1);
                        if (windowFreq.get(sc) > patternFreq.get(sc)) {
                            break; // more than needed
                        }
                        pi++;
                    }
                    si++;
                }
                if (pi == m) {
                    boolean valid = true;
                    for (char c : patternFreq.keySet()) {
                        if (!windowFreq.containsKey(c) || !windowFreq.get(c).equals(patternFreq.get(c))) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        String window = candidate.toString();
                        if (window.length() < minLength || 
                            (window.length() == minLength && window.compareTo(result) < 0)) {
                            result = window;
                            minLength = window.length();
                        }
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking input from the console
        System.out.print("Enter string s: ");
        String s = scanner.nextLine();

        System.out.print("Enter pattern p: ");
        String p = scanner.nextLine();

        String output = findMinimumWindow(s, p);
        System.out.println("Output: " + (output.isEmpty() ? "(empty string)" : output));

        scanner.close();
    }
}
