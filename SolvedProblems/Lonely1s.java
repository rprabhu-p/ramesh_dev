

import java.util.Scanner;

public class Lonely1s {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter number of columns:");
        int cols = sc.nextInt();

        int[][] matrix = new int[rows][cols];
        System.out.println("Enter the matrix elements (0s and 1s):");   
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        sc.close();

        int count = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++){
                if(matrix[i][j] == 1) {
                    boolean lonely = true;

                    for(int k = 0; k < rows; k++){
                        if(k != i && matrix[k][j] == 1) {
                            lonely = false;
                            break;
                        }
                    }
                    if(lonely) {
                        for(int k = 0; k < cols; k++) {
                            if(k != j && matrix[i][k] == 1) {
                                lonely = false;
                                break;
                            }
                        }
                    }
                    if(lonely) {
                        count++;
                    }
                }
            }
        }

        System.out.println("Number of lonely 1s: " + count);
    }
}
