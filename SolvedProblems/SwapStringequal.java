import java.util.*;

public class SwapStringequal {

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);

        System.out.println("Enter String A= ");
        String str1 = inp.nextLine();

        System.out.println("Enter String B=");
        String str2 = inp.nextLine();

        inp.close();

        int count = 0;

        for(int i = 0; i < str1.length(); i++) {
            if(str1.charAt(i) != str2.charAt(i)) {
                count++;
            }
        }
        
        if(count == 2) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }

        //another approachs
        if(!swapStringCheck(str1, str2)) {
            System.out.println("False");
        } else {
            System.out.println("True");
        }
    }
    
    public static boolean swapStringCheck(String str1, String str2) {

        if((str1.length() < 1)  || (str2.length() < 1)) return false;
        if(str1.equals(str2)) return false;
        if(str1.length() != str2.length()) return false;

        int[] diffTable = new int[2];
        int diffCount = 0;

        for(int i = 0; i < str1.length(); i++) {
            if(str1.charAt(i) != str2.charAt(i)) {
                if(diffCount >= 2) {
                    return false;
                } else {
                    diffTable[diffCount++] = i;               
                }
            } 
        }
        return (str1.charAt(diffTable[0]) == str2.charAt(diffTable[1]) &&
                str1.charAt(diffTable[1]) == str2.charAt(diffTable[0]));
    }
}
