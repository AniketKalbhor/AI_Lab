
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

        int[][] moves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

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
        if (depth > MAX_DEPTH) {
            return false;
        }

        visited.add(current.board);

        if (current.isGoal(goal)) {
            printSolution(current);
            return true;
        }

        for (PuzzleStateDFS neighbor : current.getSuccessors()) {
            if (!visited.contains(neighbor.board)) {
                boolean found = dfs(neighbor, goal, visited, depth + 1);
                if (found) {
                    return true;
                }
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
            if (i % 3 == 2) {
                System.out.println();
            }
        }
    }
}

// Eight Puzzle Problem Solver using Depth-First Search
// Problem Statement:
// This code implements a solution to the classic Eight Puzzle problem using Depth-First Search (DFS). The Eight Puzzle consists of a 3×3 grid with 8 numbered tiles and one empty space. The goal is to rearrange the tiles from an initial configuration to a goal configuration by sliding tiles into the empty space.
// Explanation of functions used:
// The program uses several key functions to implement the DFS algorithm:

// dfs(): The recursive depth-first search function that explores states
// getSuccessors(): Generates possible next states by moving tiles
// printSolution(): Reconstructs and displays the solution path
// printBoard(): Visualizes a puzzle state in a readable format

// Basic explanation of general approach:
// The solution employs depth-first search with backtracking and a depth limit to avoid infinite loops. It represents puzzle states as strings where each character is a number (0-8), with '0' representing the empty space. The algorithm explores the state space by trying all possible moves, backtracking when necessary, until it either finds the goal configuration or exhausts all possibilities within the depth limit.
// Explanation of variables:

// board: String representation of the puzzle state
// parent: Reference to the previous state
// visited: Set of previously explored states
// MAX_DEPTH: Maximum search depth to prevent infinite loops
// zeroIndex: Position of the empty space (0) in the board

// Flow of execution:

// User inputs the initial and goal puzzle configurations
// The program initializes the starting state and begins DFS exploration
// For each state, it checks if the goal is reached
// If not, it generates all valid successor states by sliding tiles
// It recursively explores each unvisited successor
// If a path exceeds MAX_DEPTH or leads to a dead end, it backtracks
// When the goal is found, it reconstructs and displays the full solution path
// If no solution exists within the depth bound, it reports failure

// Short State Space tree:
// Initial State "123456780"
// ├── Move 0 down → "123456078"
// │   ├── Move 0 right → "123456708"
// │   │   └── ...
// │   └── Move 0 left → "123456087"
// │       └── ...
// └── Move 0 right → "123456870"
//     ├── Move 0 down → "123456870" (invalid - out of bounds)
//     └── Move 0 left → "123456780" (invalid - visited)
// Example Theory:
// 🔹 Constraints:

// Limited to 3×3 puzzle grid
// Only one tile can move at a time
// Only tiles adjacent to the empty space can move
// Maximum search depth of 50 to prevent infinite loops

// 🔹 Input:

// Initial configuration: A string representation of the starting puzzle state
// Goal configuration: A string representation of the desired puzzle state

// 🔹 Output:

// Sequence of board states from initial to goal configuration
// "No solution found" message if no solution exists within the depth limit

// ⚙️ Functions Explained

// dfs(): Implements recursive depth-first search with visited set tracking and depth limit
// getSuccessors(): Calculates valid moves by finding the empty space and attempting to move adjacent tiles
// isGoal(): Simple string comparison to check if current state matches goal state
// printSolution(): Reconstructs path by following parent references and displays in reverse order

// 📊 Variables Used

// board: String representing 3×3 grid in row-major order
// visited: HashSet to prevent cycles by tracking explored states
// MAX_DEPTH: Constant (50) defining maximum exploration depth
// path: ArrayList to store the solution sequence

// 🔁 Flow of Execution
// The search starts from the initial state, recursively exploring deeper into the state space. When the algorithm reaches a dead end or exceeds the depth limit, it backtracks and explores alternative paths. This process continues until either the goal state is found or all possibilities are exhausted. The algorithm uses a visited set to avoid re-exploring states and implements backtracking by removing states from the visited set when a path is exhausted.
// The DFS approach can find a solution quickly in lucky cases but may explore deep, unproductive paths before finding an optimal solution. The depth limit provides protection against infinite loops in unsolvable puzzles or those requiring excessive moves.
