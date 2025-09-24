

import java.util.Scanner;

public class LongPressKey {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the first string:");
        String str1 = sc.nextLine();
        System.out.println("Enter the second string:");
        String str2 = sc.nextLine();
        System.out.println(isLongPressed(str1, str2));
        sc.close();
    }
    public static boolean isLongPressed(String str1, String str2) {
        int i = 0, j = 0;

        while (j < str2.length()) {
            if (i < str1.length() && str1.charAt(i) == str2.charAt(j)) {
                i++;
                j++;
            } else if (j > 0 && str2.charAt(j) == str2.charAt(j - 1)) {
                j++;
            } else {
                return false;
            }
        }
        return i == str1.length();
    }
}
