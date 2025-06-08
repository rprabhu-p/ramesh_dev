package dsa_problems;

import java.util.Scanner;

public class BinarySearch {
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
        System.out.println();
        
        System.out.println("Enter target to search");
        int target = sc.nextInt();

        int index = binarySearch(arr, target);

        if(index == -1) {
            System.out.println("Not found!");
        } else {
            System.out.println("Given terget present in index " + index);
        }

        sc.close();
        return;
     }

     public static int binarySearch(int[] arr, int x) {
     
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == x) return mid;
            if (arr[mid] < x) low = mid + 1;
            else high = mid - 1;
        }

        return -1;
     }
     
}
