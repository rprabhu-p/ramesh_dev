import java.util.Scanner;

public class StringTransform {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        //To get String
        String data = input.nextLine();
        input.close();

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
