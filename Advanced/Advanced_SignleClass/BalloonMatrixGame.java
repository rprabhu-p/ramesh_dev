package Advanced_SignleClass;

import java.util.*;

public class BalloonMatrixGame {

    static char[][] matrix;
    static int m, n;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Get matrix size
        System.out.print("Enter the matrix size(m*n): ");
        m = sc.nextInt();
        n = sc.nextInt();

        matrix = new char[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(matrix[i], '-');
        }

        boolean stop = false;
        while (!stop) {
            System.out.print("Enter colm no: ");
            int col = sc.nextInt() - 1; // user enters 1-based index
            System.out.print("Enter the color of the balloon: ");
            char color = sc.next().charAt(0);

            dropBalloon(col, color);

            printMatrix();

            // Check rule ii: Stop if any row is completely filled
            if (isAnyRowFull()) {
                System.out.println("Row is filled completely. Program is terminated.");
                break;
            }

            // Check rule iv & v: Burst 3-in-a-row or 3-in-a-column
            checkBurst();

            System.out.print("Do you wish to continue(Y/N): ");
            char choice = sc.next().charAt(0);
            if (choice == 'N' || choice == 'n') {
                stop = true;
                System.out.println("Program Terminated.");
            }
        }
        sc.close();
    }

    // Drop balloon into column (rule iii also handled here)
    private static void dropBalloon(int col, char color) {
        // If column is full, find first free column from left
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
                return;
            }
        }
    }

    // Check if column is completely filled
    private static boolean isColumnFull(int col) {
        for (int i = 0; i < m; i++) {
            if (matrix[i][col] == '-') return false;
        }
        return true;
    }

    // Find first free column from left
    private static int findFirstFreeColumn() {
        for (int j = 0; j < n; j++) {
            if (!isColumnFull(j)) return j;
        }
        return -1; // no free column
    }

    // Print the matrix
    private static void printMatrix() {
        System.out.println("Contents of the matrix:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Rule ii: Check if any row is completely filled
    private static boolean isAnyRowFull() {
        for (int i = 0; i < m; i++) {
            boolean full = true;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '-') {
                    full = false;
                    break;
                }
            }
            if (full) return true;
        }
        return false;
    }

    // Rule iv & v: Burst balloons if 3 continuous in row or column
    private static void checkBurst() {
        boolean burst = false;

        // Vertical burst (3 same in a column)
        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= m - 3; i++) {
                char c = matrix[i][j];
                if (c != '-' && c == matrix[i + 1][j] && c == matrix[i + 2][j]) {
                    matrix[i][j] = matrix[i + 1][j] = matrix[i + 2][j] = '-';
                    burst = true;
                }
            }
        }

        // Horizontal burst (3 same in a row)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= n - 3; j++) {
                char c = matrix[i][j];
                if (c != '-' && c == matrix[i][j + 1] && c == matrix[i][j + 2]) {
                    matrix[i][j] = matrix[i][j + 1] = matrix[i][j + 2] = '-';
                    burst = true;
                }
            }
        }

        if (burst) {
            System.out.println("Burst happened!");
            printMatrix();
        }
    }
}
