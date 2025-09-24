

import java.util.HashMap;
import java.util.Scanner;

public class AlternateDublicate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the size of the array: ");
        int size = scanner.nextInt();
        int[] array = new int[size];

        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < size; i++) {    
            array[i] = scanner.nextInt();
        }

        System.out.println("Array after removing alternate duplicates:");

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < size; i++) {
            int val = array[i];
            int count = map.getOrDefault(val, 0) + 1;
            map.put(val, count);

            if(count % 2 == 0){
                array[i] = 0;
            }
        }

        scanner.close();

        System.out.println("Resultant Array:");
        for (int i = 0; i < size; i++) {
                System.out.print(array[i] + " ");
        }
    }
}
