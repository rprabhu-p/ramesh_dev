package Advanced.BalloonGame;

import java.util.*;

public class BalloonGame3 {
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

            System.out.print("Do you wish to continue(Y/N): ");
            char ch = sc.next().charAt(0);
            if (ch == 'N' || ch == 'n') {
                stop = true;
                System.out.println("Program terminated.");
            }
        }
        sc.close();
    }

    private static void dropBalloon(int col, char color) {
        if (isColumnFull(col)) {
            col = findFirstFreeColumn();
            if (col == -1) {
                System.out.println("Matrix is full. Program terminated.");
                System.exit(0);
            }
        }
        for (int i = m - 1; i >= 0; i--) {
            if (matrix[i][col] == '-') {
                matrix[i][col] = color;
                break;
            }
        }
    }

    private static boolean isColumnFull(int col) {
        for (int i = 0; i < m; i++) if (matrix[i][col] == '-') return false;
        return true;
    }

    private static int findFirstFreeColumn() {
        for (int j = 0; j < n; j++) if (!isColumnFull(j)) return j;
        return -1;
    }

    private static void printMatrix() {
        System.out.println("Contents of the matrix:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
}
