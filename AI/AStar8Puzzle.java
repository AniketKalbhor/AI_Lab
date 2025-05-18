import java.util.*;

class PuzzleState implements Comparable<PuzzleState> {
    int[][] board;
    int cost;
    int depth;
    int blankRow, blankCol;
    PuzzleState parent;

    static int[][] goal;

    public PuzzleState(int[][] board, int depth, PuzzleState parent) {
        this.board = board;
        this.depth = depth;
        this.parent = parent;
        this.cost = depth + manhattan(board);
        locateBlank();
    }

    private void locateBlank() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    return;
                }
    }

    private int manhattan(int[][] state) {
        int distance = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                int value = state[i][j];
                if (value != 0) {
                    int[] target = findInGoal(value);
                    distance += Math.abs(i - target[0]) + Math.abs(j - target[1]);
                }
            }
        return distance;
    }

    private int[] findInGoal(int value) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (goal[i][j] == value)
                    return new int[]{i, j};
        return new int[]{-1, -1}; // Should not happen
    }

    public boolean isGoal() {
        return Arrays.deepEquals(board, goal);
    }

    public List<PuzzleState> getNeighbors() {
        List<PuzzleState> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            int newRow = blankRow + d[0];
            int newCol = blankCol + d[1];
            if (isValid(newRow, newCol)) {
                int[][] newBoard = copyBoard(board);
                newBoard[blankRow][blankCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;
                neighbors.add(new PuzzleState(newBoard, depth + 1, this));
            }
        }
        return neighbors;
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < 3 && c >= 0 && c < 3;
    }

    private int[][] copyBoard(int[][] b) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++)
            copy[i] = b[i].clone();
        return copy;
    }

    @Override
    public int compareTo(PuzzleState other) {
        return Integer.compare(this.cost, other.cost);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PuzzleState) {
            return Arrays.deepEquals(this.board, ((PuzzleState) o).board);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}

public class AStar8Puzzle {

    public static void aStarSearch(int[][] initialBoard) {
        PuzzleState start = new PuzzleState(initialBoard, 0, null);
        PriorityQueue<PuzzleState> openList = new PriorityQueue<>();
        Set<PuzzleState> closedSet = new HashSet<>();

        openList.add(start);

        while (!openList.isEmpty()) {
            PuzzleState current = openList.poll();

            if (current.isGoal()) {
                System.out.println("\n✅ Solution found in " + current.depth + " moves.");
                printPath(current);
                return;
            }

            closedSet.add(current);

            for (PuzzleState neighbor : current.getNeighbors()) {
                if (!closedSet.contains(neighbor)) {
                    openList.add(neighbor);
                }
            }
        }

        System.out.println("❌ No solution found.");
    }

    private static void printPath(PuzzleState state) {
        List<PuzzleState> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = state.parent;
        }
        Collections.reverse(path);
        for (PuzzleState s : path) {
            printBoard(s.board);
            System.out.println();
        }
    }

    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int v : row)
                System.out.print((v == 0 ? " " : v) + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] initialBoard = new int[3][3];
        int[][] goalBoard = new int[3][3];

        System.out.println("Enter the initial state (use 0 for blank):");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                initialBoard[i][j] = scanner.nextInt();

        System.out.println("Enter the goal state (use 0 for blank):");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                goalBoard[i][j] = scanner.nextInt();

        PuzzleState.goal = goalBoard;

        aStarSearch(initialBoard);
    }
}
