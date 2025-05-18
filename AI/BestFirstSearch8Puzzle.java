//import java.util.*;
//
//class PuzzleState implements Comparable<PuzzleState> {
//    static int[][] goalBoard; // User-defined goal board
//
//    int[][] board;
//    int cost;
//    PuzzleState parent;
//
//    public PuzzleState(int[][] board, PuzzleState parent) {
//        this.board = board;
//        this.parent = parent;
//        this.cost = calculateHeuristic();
//    }
//
//    // Manhattan Distance Heuristic
//    private int calculateHeuristic() {
//        int distance = 0;
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board[i][j] != 0) {
//                    int value = board[i][j];
//                    for (int m = 0; m < 3; m++) {
//                        for (int n = 0; n < 3; n++) {
//                            if (goalBoard[m][n] == value) {
//                                distance += Math.abs(i - m) + Math.abs(j - n);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return distance;
//    }
//
//    @Override
//    public int compareTo(PuzzleState other) {
//        return Integer.compare(this.cost, other.cost);
//    }
//
//    public boolean isGoal() {
//        return Arrays.deepEquals(this.board, goalBoard);
//    }
//
//    public List<PuzzleState> generateSuccessors() {
//        List<PuzzleState> successors = new ArrayList<>();
//        int[] dx = {-1, 1, 0, 0};
//        int[] dy = {0, 0, -1, 1};
//        int zeroX = -1, zeroY = -1;
//
//        // Find the 0 (empty space)
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board[i][j] == 0) {
//                    zeroX = i;
//                    zeroY = j;
//                    break;
//                }
//            }
//        }
//
//        for (int k = 0; k < 4; k++) {
//            int newX = zeroX + dx[k];
//            int newY = zeroY + dy[k];
//
//            if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
//                int[][] newBoard = deepCopy(board);
//                newBoard[zeroX][zeroY] = newBoard[newX][newY];
//                newBoard[newX][newY] = 0;
//                successors.add(new PuzzleState(newBoard, this));
//            }
//        }
//        return successors;
//    }
//
//    private int[][] deepCopy(int[][] board) {
//        int[][] newBoard = new int[3][3];
//        for (int i = 0; i < 3; i++) {
//            newBoard[i] = board[i].clone();
//        }
//        return newBoard;
//    }
//
//    public void printBoard() {
//        for (int[] row : board) {
//            for (int num : row) {
//                System.out.print(num + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    public String boardToString() {
//        StringBuilder sb = new StringBuilder();
//        for (int[] row : board) {
//            for (int num : row) {
//                sb.append(num);
//            }
//        }
//        return sb.toString();
//    }
//}
//
//public class BestFirstSearch8Puzzle {
//    public static void solve(int[][] initialBoard) {
//        PriorityQueue<PuzzleState> openList = new PriorityQueue<>();
//        Set<String> visited = new HashSet<>();
//
//        PuzzleState startState = new PuzzleState(initialBoard, null);
//        openList.add(startState);
//        visited.add(startState.boardToString());
//
//        while (!openList.isEmpty()) {
//            PuzzleState current = openList.poll();
//            current.printBoard();
//
//            if (current.isGoal()) {
//                System.out.println("Goal state reached!");
//                printSolutionPath(current);
//                return;
//            }
//
//            for (PuzzleState neighbor : current.generateSuccessors()) {
//                if (!visited.contains(neighbor.boardToString())) {
//                    openList.add(neighbor);
//                    visited.add(neighbor.boardToString());
//                }
//            }
//        }
//        System.out.println("No solution found.");
//    }
//
//    public static void printSolutionPath(PuzzleState state) {
//        List<PuzzleState> path = new ArrayList<>();
//        while (state != null) {
//            path.add(state);
//            state = state.parent;
//        }
//        Collections.reverse(path);
//        System.out.println("Solution steps:");
//        for (PuzzleState step : path) {
//            step.printBoard();
//        }
//    }
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        int[][] initialBoard = new int[3][3];
//        int[][] goalBoard = new int[3][3];
//
//        System.out.println("Enter the initial board (3x3):");
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                initialBoard[i][j] = scanner.nextInt();
//            }
//        }
//
//        System.out.println("Enter the goal board (3x3):");
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                goalBoard[i][j] = scanner.nextInt();
//            }
//        }
//
//        PuzzleState.goalBoard = goalBoard;
//        solve(initialBoard);
//    }
//}
