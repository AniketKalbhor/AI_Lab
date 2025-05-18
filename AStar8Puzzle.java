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




/*
# 8-Puzzle Problem with A* Algorithm: Comprehensive Explanation

## Problem Statement:
The 8-puzzle is a sliding puzzle consisting of a 3×3 grid with 8 numbered tiles and one empty space. The goal is to 
rearrange the tiles from a given initial configuration to a specified goal configuration by sliding tiles into the empty space. 
This is a classic problem in artificial intelligence used to demonstrate search algorithms.

## Explanation of Functions Used:

- **A* Search**: The primary algorithm that efficiently finds the shortest path from initial to goal state
- **Manhattan Distance Heuristic**: Estimates the distance to goal by summing the horizontal and vertical distances 
each tile must move
- **Priority Queue**: Maintains the open list sorted by f(n) = g(n) + h(n)
- **Hash Set**: Tracks visited states to avoid cycles and repetition
- **Backtracking**: Reconstructs the solution path once the goal is reached

## Basic Approach and Heuristic Comparison:

The A* algorithm combines the advantages of both uniform-cost search (which considers path cost g(n)) and greedy 
best-first search (which considers heuristic h(n)). It uses the evaluation function f(n) = g(n) + h(n) to determine 
which nodes to explore next.

**Manhattan vs. Euclidean Distance:**
- **Manhattan Distance**: Sum of horizontal and vertical distances each tile is from its goal position
- **Euclidean Distance**: Straight-line distance between current position and goal position

Manhattan distance is preferred for the 8-puzzle because:
1. It's more accurate for grid-based movement where only horizontal and vertical moves are allowed
2. It's admissible (never overestimates) and consistent (satisfies triangle inequality)
3. It's computationally simpler than Euclidean distance
4. For grid-based puzzles, Manhattan distance corresponds more closely to the minimum number of moves required

## Explanation of Variables:

- **board**: 2D array representing the current state of the puzzle
- **cost**: The f(n) value (path cost g(n) + heuristic h(n))
- **depth**: Tracks the g(n) value (cost so far from start)
- **blankRow/blankCol**: Position of the empty space
- **parent**: Reference to the previous state for path reconstruction
- **goal**: Target configuration we're trying to reach

## Flow of Execution:

1. **Initialization**:
   - Create initial state from input board
   - Initialize priority queue (open list) and visited set (closed list)
   - Add initial state to open list

2. **Main Search Loop**:
   - Retrieve state with lowest f(n) value from priority queue
   - Check if it's the goal state; if so, reconstruct and return path
   - Otherwise, generate all valid neighbor states by moving the blank tile
   - For each valid neighbor not already visited:
     * Calculate f(n) = g(n) + h(n)
     * Add to priority queue with appropriate priority
     * Mark as visited
   - Repeat until goal is found or queue is empty

3. **Path Reconstruction**:
   - Once goal is found, follow parent pointers back to start
   - Reverse the path to get the sequence from start to goal

## State Space Tree (Simplified):
```
Initial State (depth 0)
    |
    ├── Move Up (depth 1, f=5)
    |   |
    |   ├── Move Left (depth 2, f=6) [CLOSED]
    |   └── Move Right (depth 2, f=4) ⭐
    |       |
    |       └── Move Down (depth 3, f=5) ⭐
    |
    ├── Move Down (depth 1, f=7) [CLOSED]
    |
    ├── Move Left (depth 1, f=6) [CLOSED]
    |
    └── Move Right (depth 1, f=8) [CLOSED]
```

⭐ = States on optimal path toward solution

## Comparison with Other Methods:

1. **BFS (Breadth-First Search)**:
   - Guaranteed to find shortest path but explores all states at depth d before depth d+1
   - For 8-puzzle, much less efficient than A* since it ignores heuristic information
   - Space complexity is problematic for deeper puzzles

2. **DFS (Depth-First Search)**:
   - Memory efficient but not guaranteed to find shortest path
   - May get trapped in infinite paths without proper cycle detection
   - Poor choice for 8-puzzle as solution depth is unknown

3. **Iterative Deepening**:
   - Combines BFS completeness with DFS memory efficiency
   - Still less efficient than A* for 8-puzzle as it ignores heuristic information

4. **Greedy Best-First Search**:
   - Uses only heuristic h(n), ignoring path cost g(n)
   - Faster than A* but not guaranteed to find optimal solution
   - Can get trapped in local minima

5. **A*** (our implementation):
   - Guarantees optimal solution when using admissible heuristic
   - More efficient than uninformed search methods
   - Balances path cost and heuristic information
   - Can solve most 8-puzzle instances quickly with Manhattan distance
   - State-of-the-art approach for this problem when memory is sufficient

A* with Manhattan distance represents the ideal balance of efficiency and optimality for the 8-puzzle problem, 
which is why it's the most commonly taught and used approach for this classic AI problem.
*/
