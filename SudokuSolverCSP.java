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




/*
I'll provide a comprehensive explanation of the Sudoku solver code with helpful context. Here's a text-based explanation 
formatted as requested:

# Sudoku Solver Explanation

## Problem Statement:
The code implements a backtracking algorithm to solve 9x9 Sudoku puzzles where some cells are pre-filled and others need to
be completed. A valid solution must satisfy the constraint that each row, column, and 3x3 subgrid contains digits 1-9 with no 
repetition. The empty cells are represented by 0.

## Explanation of functions used:
- `main()`: Entry point that takes input for the Sudoku board and attempts to solve it.
- `solve()`: Recursive backtracking function that tries to place values in empty cells.
- `isValid()`: Constraint checker that ensures a number can be placed in a given cell.
- `printBoard()`: Visualizes the current board state with formatting.

## Basic explanation of general approach:
The code uses a depth-first search with backtracking to solve the Sudoku puzzle as a constraint satisfaction problem (CSP). 
It systematically tries placing numbers in empty cells, validating against the three constraints (row, column, 3x3 box), 
and backtracking when reaching an invalid state. This continues until a complete solution is found or all possibilities are exhausted.

The key constraints are:
1. Each row must contain digits 1-9 without repetition
2. Each column must contain digits 1-9 without repetition
3. Each 3x3 subgrid must contain digits 1-9 without repetition

## Explanation of variables:
- `SIZE`: Constant representing the 9x9 board dimensions
- `EMPTY`: Constant indicating an empty cell (value 0)
- `board[][]`: 2D array representing the Sudoku grid
- `row` and `col`: Iterators for traversing the grid
- `num`: Value (1-9) being tested at a specific position
- `startRow` and `startCol`: Top-left coordinates of a 3x3 subgrid

## Flow of execution:
1. Program accepts a 9x9 grid input with zeros for empty cells
2. The `solve()` function searches for the first empty cell (containing 0)
3. When an empty cell is found, it tries placing values 1-9:
   - For each value, it calls `isValid()` to check all three constraints
   - If valid, it places the number and recursively calls `solve()` for the next empty cell
   - If the recursive call returns true, the solution propagates back
   - If placing a number leads to an invalid state, it backtracks by resetting the cell to 0
4. If no empty cells remain, the puzzle is solved
5. If no valid number can be placed in an empty cell, the function backtracks
6. The solution is printed with formatting if found, otherwise failure is reported

## Short State Space Tree:
```
Initial Board
    ↓
Try 5 at (0,0) → Valid
    ↓
    Try 3 at (0,1) → Valid
        ↓
        Try 7 at (0,2) → Not Valid
        ↓
        Try 8 at (0,2) → Valid
            ↓
            ... (continues with next empty cell)
                ↓
                Dead End → Backtrack to (0,2)
                    ↓
                    Try 9 at (0,2) → Valid
                        ↓
                        ... (solution path)
                            ↓
                            Final Solution
```

The key insight here is that the backtracking algorithm systematically explores all possible number placements 
with early pruning of invalid states, making it more efficient than a brute force approach that would test all 9^81 
possible configurations.
*/
