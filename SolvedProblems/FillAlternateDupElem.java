import java.util.Scanner;
import java.util.Stack;

public class FillAlternateDupElem {
    
    public static void main(String args[]) {
        Scanner inp = new Scanner(System.in);

        System.out.print("Enter no of value: ");
        int arr_size = 0;
        if(inp.hasNextInt()) {
            arr_size = inp.nextInt();
        }
        
        int[] arr = new int[arr_size];

        System.out.println("Enter elements :");
        for(int i = 0; i < arr_size; i++) {
            if(inp.hasNextInt()) {
                arr[i] = inp.nextInt();
            }
        }

        inp.close();

        try {
            int[] res = new int[arr_size];

            res = fillAlternateDupElem(arr, arr_size);

            for(int i = 0; i < arr_size; i++) {
                System.out.print(res[i] + " ");
            }
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public static int[] fillAlternateDupElem(int[] array, int size) {
        Stack <Integer> stack = new Stack<>();

        for(int i = 0; i < size; i++) {
            int first = array[i];
            stack.push(first);
            for(int j = i+1; j < size; j++) {
                int sec = array[j];
                if(stack.peek() == sec) {
                    array[j] = 0;
                    while(!stack.isEmpty()) {
                        stack.pop();
                    }
                }
                if(stack.isEmpty()) {
                    break;
                }
            }
            while(!stack.isEmpty()) {
                stack.pop();
            }
        }
        return array;
    }
}
