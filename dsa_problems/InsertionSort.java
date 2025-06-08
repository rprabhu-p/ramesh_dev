package dsa_problems;

import java.util.Scanner;

public class InsertionSort {
    
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

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while((j >= 0) && (arr[j] > key)){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
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
