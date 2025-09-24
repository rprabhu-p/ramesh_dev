package dsa_problems;

import java.util.Scanner;

public class SelectionSort {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of array :");
        int size = sc.nextInt();
        int arr[] = new int[size];

        System.out.println("Enter array elements :");
        for(int i = 0; i<size; i++){
            arr[i] = sc.nextInt();
        }

        System.out.println("Before Sorted");
        for(int n : arr){
            System.out.print(n+" ");
        }
        System.out.println();
        
        int index = -1;
        for (int i = 0; i < arr.length-1; i++) {

            index = i;

            for (int j = i + 1; j < arr.length; j++) {
                if(arr[index] > arr[j]) {
                    index = j;
                }
            }
            
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }


        System.out.println("After Sorted");
        for(int n : arr){
            System.out.print(n+" ");
        }
        System.out.println();

        sc.close();
        return;
     }
}
