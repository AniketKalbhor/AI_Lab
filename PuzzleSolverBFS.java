import java.util.*;

class PuzzleState {
   String board;
   PuzzleState parent;

   public PuzzleState(String board, PuzzleState parent) {
       this.board = board;
       this.parent = parent;
   }

   public boolean isGoal(String goal) {
       return board.equals(goal);
   }

   public List<PuzzleState> getSuccessors() {
       List<PuzzleState> successors = new ArrayList<>();
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
               successors.add(new PuzzleState(new String(chars), this));
           }
       }

       return successors;
   }
}

public class PuzzleSolverBFS {
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       System.out.print("Enter initial state: ");
       String start = sc.nextLine();

       System.out.print("Enter goal state: ");
       String goal = sc.nextLine();

       solvePuzzle(start, goal);
   }

   public static void solvePuzzle(String start, String goal) {
       PuzzleState initial = new PuzzleState(start, null);
       Queue<PuzzleState> queue = new LinkedList<>();
       Set<String> visited = new HashSet<>();

       queue.offer(initial);
       visited.add(initial.board);

       while (!queue.isEmpty()) {
           PuzzleState current = queue.poll();

           if (current.isGoal(goal)) {
               printSolution(current);
               return;
           }

           for (PuzzleState neighbor : current.getSuccessors()) {
               if (!visited.contains(neighbor.board)) {
                   visited.add(neighbor.board);
                   queue.offer(neighbor);
               }
           }
       }

       System.out.println("No solution found.");
   }

   public static void printSolution(PuzzleState state) {
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

// PuzzleSolverBFS: 8-Puzzle Solver Using Breadth-First Search
// Problem Statement:
// The 8-puzzle is a sliding puzzle consisting of a 3×3 grid with 8 numbered tiles and one empty space. A tile adjacent to the empty space can slide into that space. The goal is to rearrange the tiles from an initial configuration to reach a specific goal configuration by sliding tiles into the empty space.
// For example, transforming this initial state:
// 1 2 3
// 4 0 5
// 6 7 8
// To this goal state:
// 1 2 3
// 4 5 6
// 7 8 0
// Where '0' represents the empty space.
// 🔹 Constraints:

// Only one tile can be moved at a time
// Only tiles adjacent to the empty space can be moved
// The puzzle may not be solvable from every initial configuration

// 🔹 Input:

// Initial state of the 3×3 grid (as a string where '0' represents the empty space)
// Goal state of the 3×3 grid

// 🔹 Output:

// Sequence of moves (states) that transform the initial state to the goal state
// "No solution found" message if no solution exists

// Theory:
// Breadth-First Search (BFS) is a graph traversal algorithm that explores all neighbor nodes at the present depth before moving to nodes at the next depth level. In the context of the 8-puzzle:

// BFS guarantees finding the shortest path solution (in terms of the number of moves)
// It explores states level by level, starting from the initial state
// It keeps track of visited states to avoid cycles and redundant exploration
// It uses a FIFO (First-In-First-Out) queue data structure for processing states

// ⚙️ Functions Explained
// Main Functions:

// main():

// Entry point of the program
// Reads the initial and goal states from user input
// Calls solvePuzzle() to find and print the solution


// solvePuzzle():

// Implements the BFS algorithm to find a path from the initial state to the goal state
// Creates and initializes the queue and visited set
// Processes states until either finding the goal or exhausting all possibilities


// getSuccessors():

// Generates all possible next states by moving the empty space (0) in four directions
// For each direction, checks if the move is valid and creates a new state
// Returns a list of valid next states


// printSolution():

// Reconstructs the solution path from the goal state to the initial state using parent references
// Reverses the path to show states from initial to goal
// Prints each state in a readable format


// printBoard():

// Helper function that prints a 3×3 board from its string representation
// Formats the output to show the board as a grid



// State Class Functions:

// Constructor: Creates a new state with the given board configuration and parent reference
// isGoal(): Checks if the current state matches the goal state
// getSuccessors(): Generates all possible next states from the current state

// 📊 Variables Used
// Main Variables:

// start: String representation of the initial puzzle state
// goal: String representation of the target puzzle state
// queue: Queue data structure to implement BFS (stores states to be processed)
// visited: Set data structure to track visited states (prevents cycles)

// State Class Variables:

// board: String representation of the current board configuration
// parent: Reference to the parent state (for path reconstruction)
// zeroIndex: Position of the empty space (0) in the board string
// row/col: Row and column position of the empty space
// newRow/newCol: Position after a potential move
// newIdx: Index in the string representation after a potential move
// chars: Character array to manipulate the board configuration

// 🔁 Flow of Execution

// Initialization:

// Program starts and reads the initial and goal states
// Creates the initial state object and initializes BFS data structures
// Adds the initial state to the queue and the visited set


// BFS Main Loop:

// While the queue is not empty:

// Dequeue the next state to process
// Check if it's the goal state; if yes, reconstruct and print the path
// If not, generate all possible next states
// For each next state, if not visited:

// Mark as visited
// Add to the queue






// State Generation:

// For the current state, find the position of the empty space (0)
// Try moving in all four directions (up, down, left, right)
// For each valid move:

// Create a new board configuration by swapping the empty space
// Create a new state object with the new configuration
// Set the current state as its parent




// Solution Reconstruction:

// Starting from the goal state, follow parent references to the initial state
// Store each state in the path
// Reverse the path to get the sequence from initial to goal
// Print each step of the solution


// Termination:

// If goal state is found, print the solution and exit
// If queue becomes empty without finding the goal, report "No solution found"



// Short State Space Tree Example:
// For a simple 2×2 puzzle:
// Initial: "102" (0 is empty space)
// Goal: "120"
// State Space Tree:
//                    "102"
//                     / \
//                    /   \
//                "012"   "120" (Goal)
//                 /
//                /
//            "210"
// In this example:

// Starting with "102"
// Two possible moves: swap 0 with 1 → "012", or swap 0 with 2 → "120"
// "120" is the goal state, so the solution is found in one step
// The path is ["102", "120"]

// For larger puzzles like the 8-puzzle, the state space tree is much larger with many branches, making BFS essential for finding the shortest path efficiently.