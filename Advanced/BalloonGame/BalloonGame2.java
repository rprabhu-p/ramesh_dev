package BalloonGame;

import java.util.*;

public class BalloonGame2 {
    static char[][] matrix;
    static int m, n;

    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the matrix size(m*n): ");
        m = sc.nextInt();
        n = sc.nextInt();

        matrix = new char[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(matrix[i], '-');

        while (true) {
            System.out.print("Enter colm no: ");
            int col = sc.nextInt() - 1;
            System.out.print("Enter the color of the balloon: ");
            char color = sc.next().charAt(0);

            dropBalloon(col, color);
            printMatrix();

            if (isAnyRowFull()) {
                System.out.println("Row is filled completely. Program is terminated.");
                break;
            }

            System.out.print("Do you wish to continue(Y/N): ");
            char ch = sc.next().charAt(0);
            if (ch == 'N' || ch == 'n') {
                System.out.println("Program Stopped");
                break;
            }
        }
        sc.close();
    }

    private static void dropBalloon(int col, char color) {
        for (int i = m - 1; i >= 0; i--) {
            if (matrix[i][col] == '-') {
                matrix[i][col] = color;
                break;
            }
        }
    }

    private static boolean isAnyRowFull() {
        for (int i = 0; i < m; i++) {
            boolean full = true;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '-') full = false;
            }
            if (full) return true;
        }
        return false;
    }

    private static void printMatrix() {
        System.out.println("Contents of the matrix:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
}
