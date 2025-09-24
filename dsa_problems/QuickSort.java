package dsa_problems;

import java.util.Scanner;

public class QuickSort {
    
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

        quickSort(arr, 0, arr.length-1);

        System.out.println("After Sorted");
        for(int n : arr){
            System.out.print(n+" ");
        }
        System.out.println();

        sc.close();
        return;
     }

     public static void quickSort(int[] arr, int low, int high) {
        if(low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
     }

     public static int partition(int[] arr, int low, int high) {

        int pivot = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++){
            if(arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
     }
}
