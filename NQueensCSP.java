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
