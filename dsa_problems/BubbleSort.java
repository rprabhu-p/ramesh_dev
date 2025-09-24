package dsa_problems;

import java.util.Scanner;

public class BubbleSort {
    
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
        
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if(arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
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
