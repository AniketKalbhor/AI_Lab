//import java.util.*;
//
//class PuzzleState {
//    String board;
//    PuzzleState parent;
//
//    public PuzzleState(String board, PuzzleState parent) {
//        this.board = board;
//        this.parent = parent;
//    }
//
//    public boolean isGoal(String goal) {
//        return board.equals(goal);
//    }
//
//    public List<PuzzleState> getSuccessors() {
//        List<PuzzleState> successors = new ArrayList<>();
//        int zeroIndex = board.indexOf('0');
//        int row = zeroIndex / 3;
//        int col = zeroIndex % 3;
//
//        int[][] moves = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
//
//        for (int[] move : moves) {
//            int newRow = row + move[0];
//            int newCol = col + move[1];
//            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
//                int newIdx = newRow * 3 + newCol;
//                char[] chars = board.toCharArray();
//                // Swap 0 and newIdx
//                char temp = chars[zeroIndex];
//                chars[zeroIndex] = chars[newIdx];
//                chars[newIdx] = temp;
//                successors.add(new PuzzleState(new String(chars), this));
//            }
//        }
//
//        return successors;
//    }
//}
//
//public class PuzzleSolverBFS {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter initial state: ");
//        String start = sc.nextLine();
//
//        System.out.print("Enter goal state: ");
//        String goal = sc.nextLine();
//
//        solvePuzzle(start, goal);
//    }
//
//    public static void solvePuzzle(String start, String goal) {
//        PuzzleState initial = new PuzzleState(start, null);
//        Queue<PuzzleState> queue = new LinkedList<>();
//        Set<String> visited = new HashSet<>();
//
//        queue.offer(initial);
//        visited.add(initial.board);
//
//        while (!queue.isEmpty()) {
//            PuzzleState current = queue.poll();
//
//            if (current.isGoal(goal)) {
//                printSolution(current);
//                return;
//            }
//
//            for (PuzzleState neighbor : current.getSuccessors()) {
//                if (!visited.contains(neighbor.board)) {
//                    visited.add(neighbor.board);
//                    queue.offer(neighbor);
//                }
//            }
//        }
//
//        System.out.println("No solution found.");
//    }
//
//    public static void printSolution(PuzzleState state) {
//        List<String> path = new ArrayList<>();
//        while (state != null) {
//            path.add(state.board);
//            state = state.parent;
//        }
//        Collections.reverse(path);
//        System.out.println("Steps to reach the goal:");
//        for (String board : path) {
//            printBoard(board);
//            System.out.println();
//        }
//    }
//
//    public static void printBoard(String board) {
//        for (int i = 0; i < 9; i++) {
//            System.out.print(board.charAt(i) + " ");
//            if (i % 3 == 2) System.out.println();
//        }
//    }
//}
