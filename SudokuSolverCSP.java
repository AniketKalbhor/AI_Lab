import java.util.*;

public class SudokuSolverCSP {
    static final int SIZE = 9; // 9x9 Sudoku
    static final int EMPTY = 0;

    public static void main(String[] args) {
        int[][] board = new int[SIZE][SIZE];
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Sudoku puzzle (use 0 for empty cells):");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = scanner.nextInt();
            }
        }

        if (solve(board)) {
            System.out.println("✅ Sudoku Solved:");
            printBoard(board);
        } else {
            System.out.println("❌ No solution exists.");
        }
        scanner.close();
    }

    // Backtracking solver
    private static boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;

                            if (solve(board))
                                return true;

                            board[row][col] = EMPTY; // Backtrack
                        }
                    }
                    return false; // No valid number found
                }
            }
        }
        return true; // All cells filled
    }

    // Check if placing num at (row, col) is valid
    private static boolean isValid(int[][] board, int row, int col, int num) {
        // Row check
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == num)
                return false;

        // Column check
        for (int i = 0; i < SIZE; i++)
            if (board[i][col] == num)
                return false;

        // 3x3 subgrid check
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = startRow; i < startRow + 3; i++)
            for (int j = startCol; j < startCol + 3; j++)
                if (board[i][j] == num)
                    return false;

        return true;
    }

    // Print Sudoku board
    private static void printBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0)
                System.out.println("------+-------+------");

            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0 && j != 0)
                    System.out.print("| ");
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
