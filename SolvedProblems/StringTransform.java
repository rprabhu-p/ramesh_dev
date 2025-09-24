import java.util.Scanner;

public class StringTransform {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        //To get String
        String data = input.nextLine();
        input.close();

        String newWord = "";

        for(int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if(ch >= 'A' && ch <= 'Z') {
                char mappedChar = (char)('Z' - (ch - 'A'));
                newWord += mappedChar;
            } else {
                newWord += ch;
            }
        }     
        System.out.println("Modified String: " + newWord);

        //Another approach
        try {
            String transformed = stringTransform(data);
            System.out.println(transformed);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String stringTransform(String data) {
        StringBuilder trans = new StringBuilder();

        for(char ch: data.toCharArray()) {
            if(ch >= 'A' && ch <= 'Z') {
                char temp = (char) ('Z' - (ch - 'A'));
                trans.append(temp);
            } else if(ch >= 'a' && ch <= 'z') {
                char temp = (char) ('z' - (ch - 'a'));
                trans.append(temp);
            } else {
                trans.append(ch);
            }            
        }       

        return trans.toString();
    }
}
