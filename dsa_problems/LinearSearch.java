package dsa_problems;

import java.util.Scanner;

public class LinearSearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of array :");
        int size = sc.nextInt();
        int arr[] = new int[size];

        System.out.println("Enter array elements :");
        for(int i = 0; i<size; i++){
            arr[i] = sc.nextInt();
        }

        System.out.println("Given array");
        for(int n : arr){
            System.out.print(n+" ");
        }
        
        System.out.println("Enter target to search");
        int target = sc.nextInt();

        int index = linearSearch(arr, target);

        if(index > 0) {
            System.out.println("Given terget present in index " + index);
        } else {
            System.out.println("Not found!");
        }

        sc.close();
        return;
     }

     public static int linearSearch(int[] arr, int target) {
        //LinearSearch
        for (int i = 0 ; i < arr.length; i++){
            if(arr[i] == target) 
                return i;
        }
        return -1;
     }
}
