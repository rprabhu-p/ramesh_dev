import java.util.Arrays;
import java.util.Scanner;

public class ArrayBasic {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        int[] array = null;

        while(true) {
            System.out.println("***** Array sample app ******");
            System.out.println("Please choose your choice:");
            System.out.println("1. Input an array of Integers");
            System.out.println("2. Print the array");
            System.out.println("3. Sort the array");
            System.out.println("4. Search for an element");
            System.out.println("5. Fill the array with value");
            System.out.println("6. Copy the array");
            System.out.println("7. Exit");
            System.out.println("*******************************");

            int choice = sc.nextInt();

            switch(choice) {
                case 1:
                    System.out.println("Please enter size of array");
                    int s = sc.nextInt();
                    array = new int[s];
                    System.out.println("Enter the elements:");
                    for (int i = 0; i < s; i++){
                        array[i] = sc.nextInt();
                    }
                    break;
                case 2:
                    System.out.println("Array : " + Arrays.toString(array));
                    break;
                case 3:
                    Arrays.sort(array);
                    System.out.println("Sorted Array : " + Arrays.toString(array));
                    break;
                case 4:
                    System.out.println("Enter element to Search :");
                    int key = sc.nextInt();
                    Arrays.sort(array);
                    int index = Arrays.binarySearch(array, key);
                    if(index >= 0) {
                        System.out.println("Your element present in index : " + index);
                    }
                    else {
                        System.out.println("Entered element not found");
                    }
                    break;
                case 5:
                    System.out.println("Enter value to fill array : ");
                    int newVal = sc.nextInt();
                    Arrays.fill(array, newVal);
                    System.out.println("Your array now : " + Arrays.toString(array));
                    break;
                case 6:
                    int[] copyArray = Arrays.copyOf(array, 10);
                    System.out.println("Copied Array : " + Arrays.toString(copyArray));
                    break;
                case 7:
                    System.out.println("Good bye!");
                    sc.close();
                    return;
                default :
                    System.out.println("Please choose corect option.");
            }
        }
    } 
}
