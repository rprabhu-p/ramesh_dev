package Advanced.BalloonGame;

import java.util.*;

public class BalloonGame5 {
    static char[][] matrix;
    static int m, n;

    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the matrix size(m*n): ");
        m = sc.nextInt();
        n = sc.nextInt();

        matrix = new char[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(matrix[i], '-');

        boolean stop = false;
        while (!stop) {
            System.out.print("Enter colm no: ");
            int col = sc.nextInt() - 1;
            System.out.print("Enter the color of the balloon: ");
            char color = sc.next().charAt(0);

            dropBalloon(col, color);
            printMatrix();
            checkBurstRow();

            System.out.print("Do you wish to continue(Y/N): ");
            char ch = sc.next().charAt(0);
            if (ch == 'N' || ch == 'n') {
                stop = true;
                System.out.println("Program Terminated.");
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

    private static void checkBurstRow() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= n - 3; j++) {
                char c = matrix[i][j];
                if (c != '-' && c == matrix[i][j + 1] && c == matrix[i][j + 2]) {
                    matrix[i][j] = matrix[i][j + 1] = matrix[i][j + 2] = '-';
                    System.out.println("Burst happened!");
                    printMatrix();
                }
            }
        }
    }

    private static void printMatrix() {
        System.out.println("Contents of the matrix:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
}
