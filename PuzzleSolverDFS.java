import java.util.*;

class PuzzleStateDFS {
    String board;
    PuzzleStateDFS parent;

    public PuzzleStateDFS(String board, PuzzleStateDFS parent) {
        this.board = board;
        this.parent = parent;
    }

    public boolean isGoal(String goal) {
        return board.equals(goal);
    }

    public List<PuzzleStateDFS> getSuccessors() {
        List<PuzzleStateDFS> successors = new ArrayList<>();
        int zeroIndex = board.indexOf('0');
        int row = zeroIndex / 3;
        int col = zeroIndex % 3;

        int[][] moves = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int newIdx = newRow * 3 + newCol;
                char[] chars = board.toCharArray();
                // Swap 0 and newIdx
                char temp = chars[zeroIndex];
                chars[zeroIndex] = chars[newIdx];
                chars[newIdx] = temp;
                successors.add(new PuzzleStateDFS(new String(chars), this));
            }
        }

        return successors;
    }
}

public class PuzzleSolverDFS {
    static final int MAX_DEPTH = 50; // Prevent infinite loops

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter initial state: ");
        String start = sc.nextLine();

        System.out.print("Enter goal state: ");
        String goal = sc.nextLine();

        PuzzleStateDFS initial = new PuzzleStateDFS(start, null);
        Set<String> visited = new HashSet<>();
        boolean found = dfs(initial, goal, visited, 0);

        if (!found) {
            System.out.println("No solution found (or exceeds depth limit).");
        }
    }

    public static boolean dfs(PuzzleStateDFS current, String goal, Set<String> visited, int depth) {
        if (depth > MAX_DEPTH) return false;

        visited.add(current.board);

        if (current.isGoal(goal)) {
            printSolution(current);
            return true;
        }

        for (PuzzleStateDFS neighbor : current.getSuccessors()) {
            if (!visited.contains(neighbor.board)) {
                boolean found = dfs(neighbor, goal, visited, depth + 1);
                if (found) return true;
            }
        }

        // 💡 Backtrack: allow re-visiting this node in another path
        visited.remove(current.board);
        return false;
    }

    public static void printSolution(PuzzleStateDFS state) {
        List<String> path = new ArrayList<>();
        while (state != null) {
            path.add(state.board);
            state = state.parent;
        }
        Collections.reverse(path);
        System.out.println("Steps to reach the goal:");
        for (String board : path) {
            printBoard(board);
            System.out.println();
        }
    }

    public static void printBoard(String board) {
        for (int i = 0; i < 9; i++) {
            System.out.print(board.charAt(i) + " ");
            if (i % 3 == 2) System.out.println();
        }
    }
}
