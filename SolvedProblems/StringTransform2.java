
import java.util.*;

class StringTransform2 {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        sc.close();

        int len = input.length();
        String newstr = "";
        int charlen = 0;
        String reverstr = "";
        int strlen = len;

        while(len  > 0){
             //find out char len
            if((input.charAt(len-1) >= 'A') && (input.charAt(len-1) <= 'Z') || 
            (input.charAt(len-1) >= 'a') && (input.charAt(len-1) <= 'z') ) {
                charlen++;
            }
            char temp = input.charAt(len-1);
            reverstr += temp + ""; 
            len--;
        }
        System.out.println("reversed:" + reverstr);
        System.out.println("char len:" + charlen);

        //swapping char
        for(int i = 0; i< strlen ; i++){
            char ch = reverstr.charAt(i);
            if((ch >= 'A') && (ch <= 'Z')) {
                char temp = (char)('Z' - (ch - 'A'));
                newstr += temp + "";
            }
            else if((ch >= 'a') && (ch <= 'z')) {
                char temp = (char)('z' - (ch - 'a'));
                newstr += temp + "";
            } else {
                int num = ch - '0'; // convert char digit to int
                int newnum = (num - charlen) % 10;
                if (newnum < 0) newnum += 10; // fix negative values
                newstr += newnum;                    
                System.out.print( "(" + ch + "-" + charlen + ")%" + 10 + " = " + newnum); System.out.println();
            }
        }
        //testing purpose...
        System.out.println("Result: " + newstr);

    }
}
/*
 * 
 * Example: 
Input: "210ZYX"
 Step 1: Reverse → "XYZ012"
 Step 2: Process characters:
 X → C (letter count = 1)
 Y → B (letter count = 2)
 Z → A (letter count = 3)
 0 → (0 - 3) % 10 = 7
 1 → (1 - 3) % 10 = 8
 2 → (2 - 3) % 10 = 9
Output: "CBA789"
 */