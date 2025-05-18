import java.util.*;

public class NQueensCSP {
    private int n;
    private int[] queens;

    public NQueensCSP(int n) {
        this.n = n;
        this.queens = new int[n];
        Arrays.fill(queens, -1);
    }

    public boolean solve() {
        return backtrack(0);
    }

    private boolean backtrack(int row) {
        if (row == n) {
            printSolution();
            return true;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                if (backtrack(row + 1)) {
                    return true;
                }
                queens[row] = -1;
            }
        }
        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    public void printSolution() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((queens[i] == j) ? "Q " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens: ");
        int n = scanner.nextInt();
        NQueensCSP nQueens = new NQueensCSP(n);
        if (!nQueens.solve()) {
            System.out.println("No solution exists.");
        }
        scanner.close();
    }
}


/*
# N-Queens Problem Explanation

## Problem Statement:
The N-Queens problem is a classic constraint satisfaction problem where N chess queens must be placed on an N×N chessboard so 
that no two queens threaten each other. This means no two queens can share the same row, column, or diagonal. The goal is to find 
a valid arrangement of queens where none are under attack.

## Explanation of Functions Used:

- **solve()**: The main entry point that initializes the backtracking process from row 0.
- **backtrack(row)**: A recursive function implementing backtracking to find a valid queen placement.
- **isSafe(row, col)**: Determines if placing a queen at a given position would violate any constraints.
- **printSolution()**: Displays the chessboard with queen positions marked by 'Q' and empty squares as '.'.

## Basic Explanation of General Approach:

The solution uses backtracking, a systematic approach to find solutions to constraint satisfaction problems:

1. Start with an empty board
2. Place queens one row at a time
3. For each queen, try all columns in the current row
4. Check if the placement is valid (doesn't attack previously placed queens)
5. If valid, proceed to the next row
6. If a conflict is found, backtrack by removing the queen and trying a different column
7. Continue until all N queens are placed or all possibilities are exhausted

### Constraints:
- No two queens can be in the same row (automatically satisfied by placing one queen per row)
- No two queens can be in the same column
- No two queens can be on the same diagonal

### Bounding Function:
The isSafe() method serves as the bounding function by checking if a placement violates any constraint with previously placed queens.

## Explanation of Variables:

- **n**: The size of the board and number of queens to place
- **queens[]**: An array where queens[i] represents the column position of the queen in row i
- **row**: Current row being processed in the backtracking algorithm
- **col**: Column being tested for queen placement

## Flow of Execution:

1. The program starts by creating an N×N board and an array to track queen positions
2. It calls solve() which initiates backtracking from row 0
3. For each row, the algorithm tries to place a queen in each column
4. The isSafe() function checks if the placement is valid by:
   - Ensuring no queen exists in the same column
   - Ensuring no queen exists on the same diagonal (checking with the formula |row1-row2| == |col1-col2|)
5. If a valid position is found, the queen is placed and the algorithm moves to the next row
6. If all queens are placed successfully (row == n), the solution is printed
7. If no valid position exists in the current row, the algorithm backtracks to try different positions for previously placed queens
8. The process continues until a solution is found or all possibilities are exhausted

## Short State Space Tree:

```
                               [Start]
                                  |
                     [Place queen in row 0]
                /       |       |       \
        [col 0]     [col 1]  [col 2]  [col 3]
           |           |        |        |
     [Row 1]       [Row 1]    [Row 1]  [Row 1]
    /  |  \       /  |  \     /  |  \   /  |  \
   ... ... ...   ... ... ... ... ... ... ... ... ...
```

The tree branches at each level (row), with each branch representing a column choice. Invalid paths are pruned by the 
isSafe() function, reducing the search space significantly. The backtracking algorithm explores this tree depth-first, r
eturning to try alternative paths when a branch leads to a contradiction.

The state space complexity grows factorially with N, but the constraints dramatically reduce the actual search space, making the algorithm efficient enough for moderate values of N.
*/
