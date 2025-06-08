package dsa_problems;

import java.util.Scanner;

public class MergeSort {
    
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

        mergeSort(arr, 0, arr.length-1);

        System.out.println("After Sorted");
        for(int n : arr){
            System.out.print(n+" ");
        }
        System.out.println();

        sc.close();
        return;
     }

    public static void mergeSort(int[] arr, int l, int r) {

        if(l < r) {
            int mid = (l + r) /2;
            mergeSort(arr, l, mid);
            mergeSort(arr, mid + 1, r);

            merge(arr, l, mid, r);
        }
     }

     public static void merge(int[] arr, int l, int m, int r) {

        int n1 = m - l + 1;
        int n2 = r - m;

        int[] larr = new int[n1];
        int[] rarr = new int[n2];

        for(int x= 0 ; x < n1; x++){
            larr[x] = arr[l+x];
        }
        for(int x= 0 ; x < n2; x++){
            rarr[x] = arr[m+1+x];
        }

        int i = 0;
        int j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if(larr[i] <= rarr[j]) {
                arr[k] = larr[i];
                i++;
            } else {
                arr[k] = rarr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = larr[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = rarr[j];
            j++;
            k++;
        }
     }
}
