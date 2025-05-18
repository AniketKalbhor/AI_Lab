import java.util.*;

class PuzzleState implements Comparable<PuzzleState> {
    static int[][] goalBoard; // User-defined goal board

    int[][] board;
    int cost;
    PuzzleState parent;

    public PuzzleState(int[][] board, PuzzleState parent) {
        this.board = board;
        this.parent = parent;
        this.cost = calculateHeuristic();
    }

    // Manhattan Distance Heuristic
    private int calculateHeuristic() {
        int distance = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    int value = board[i][j];
                    for (int m = 0; m < 3; m++) {
                        for (int n = 0; n < 3; n++) {
                            if (goalBoard[m][n] == value) {
                                distance += Math.abs(i - m) + Math.abs(j - n);
                           }
                        }
                    }
               }
            }
        }
        return distance;
    }

    @Override
    public int compareTo(PuzzleState other) {
        return Integer.compare(this.cost, other.cost);
    }

    public boolean isGoal() {
        return Arrays.deepEquals(this.board, goalBoard);
    }
    public List<PuzzleState> generateSuccessors() {
        List<PuzzleState> successors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int zeroX = -1, zeroY = -1;

        // Find the 0 (empty space)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            int newX = zeroX + dx[k];
            int newY = zeroY + dy[k];
           if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
               int[][] newBoard = deepCopy(board);
                newBoard[zeroX][zeroY] = newBoard[newX][newY];
               newBoard[newX][newY] = 0;
                successors.add(new PuzzleState(newBoard, this));
            }
        }
        return successors;
   }
    private int[][] deepCopy(int[][] board) {
        int[][] newBoard = new int[3][3];
        for (int i = 0; i < 3; i++) {
            newBoard[i] = board[i].clone();
       }
        return newBoard;
  }

    public void printBoard() {
        for (int[] row : board) {
           for (int num : row) {
                System.out.print(num + " ");
            }
           System.out.println();
        }
        System.out.println();
   }

    public String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int num : row) {
               sb.append(num);
            }
        }
       return sb.toString();
    }


public class BestFirstSearch8Puzzle {
    public static void solve(int[][] initialBoard) {
        PriorityQueue<PuzzleState> openList = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

       PuzzleState startState = new PuzzleState(initialBoard, null);
       openList.add(startState);
        visited.add(startState.boardToString());

        while (!openList.isEmpty()) {
            PuzzleState current = openList.poll();
           current.printBoard();

            if (current.isGoal()) {
                System.out.println("Goal state reached!");
                printSolutionPath(current);
                return;
          }

            for (PuzzleState neighbor : current.generateSuccessors()) {
                if (!visited.contains(neighbor.boardToString())) {
                    openList.add(neighbor);
                    visited.add(neighbor.boardToString());
               }
            }
        }
        System.out.println("No solution found.");
   }
    public static void printSolutionPath(PuzzleState state) {
       List<PuzzleState> path = new ArrayList<>();
       while (state != null) {
           path.add(state);
           state = state.parent;
      }
       Collections.reverse(path);
       System.out.println("Solution steps:");
       for (PuzzleState step : path) {
           step.printBoard();
       }
  }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] initialBoard = new int[3][3];
        int[][] goalBoard = new int[3][3];

        System.out.println("Enter the initial board (3x3):");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               initialBoard[i][j] = scanner.nextInt();
           }
        }

        System.out.println("Enter the goal board (3x3):");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goalBoard[i][j] = scanner.nextInt();
            }
        }

        PuzzleState.goalBoard = goalBoard;
        solve(initialBoard);
    }
}




/*
# Explanation of 8-Puzzle Best First Search

## Problem Statement
The 8-puzzle is a classic sliding tile puzzle consisting of a 3×3 grid with 8 numbered tiles and one empty space. The goal is to 
rearrange the tiles from an initial configuration to a goal configuration by sliding tiles into the empty space. The Best First 
Search algorithm is used to find a solution path efficiently using heuristic guidance.

## Explanation of Functions Used

The BestFirstSearch8Puzzle implementation uses several key functions:

- **PuzzleState Class**: Encapsulates the puzzle state with board configuration, parent reference, and cost calculation
- **Manhattan Distance**: A heuristic function measuring the sum of horizontal and vertical distances of each tile from its goal position
- **generateSuccessors()**: Creates all possible next states by moving tiles adjacent to the empty space
- **solve()**: Main search algorithm using priority queue to expand states based on heuristic values
- **printSolutionPath()**: Reconstructs and displays the solution path from initial to goal state

## Basic Approach

Best First Search is a heuristic-guided search algorithm that selects the most promising states first. Unlike uniform-cost algorithms, 
it prioritizes states based on an estimated cost to reach the goal.

**Manhattan vs. Euclidean Distance**:
- Manhattan distance (used in this code) measures the sum of horizontal and vertical movements required for each tile to reach its 
goal position
- Euclidean distance would measure the straight-line distance between current and goal positions
- Manhattan is preferred for 8-puzzle because tiles can only move horizontally and vertically, making it a more accurate representation

of the actual moves required

## Explanation of Variables

- **board[][]**: 2D array representing the current puzzle configuration
- **cost**: Combined cost value (heuristic + depth) for prioritizing states
- **parent**: Reference to the previous state, enabling solution path reconstruction
- **visited**: Set of explored states to avoid cycles and repeated work
- **openList**: Priority queue that orders states by their heuristic values

## Flow of Execution

1. User inputs initial and goal puzzle configurations
2. Search begins by creating the initial state and adding it to the priority queue
3. While the queue is not empty:
   - Remove the state with the lowest cost
   - If it's the goal state, reconstruct and print the solution path
   - Otherwise, generate all possible successor states by moving tiles
   - Add valid successors to the priority queue
4. If the queue empties without finding the goal, report that no solution exists

## Short State Space Tree

```
Initial State (cost = depth + heuristic)
     |
     ├── Move Tile 1 → (Higher cost, explored later)
     |
     ├── Move Tile 2 → (Lower cost, explored first)
     |    |
     |    ├── Move Tile 3 → (Higher cost)
     |    |
     |    └── Move Tile 4 → (Lower cost) → Goal State!
     |
     └── Move Tile 3 → (Medium cost, explored second)
```

## Comparison with Other Methods

- **BFS**: Guarantees optimal solution but explores states level by level without guidance, potentially examining many more states
- **DFS**: Can get stuck in deep paths, may find non-optimal solutions, and might never terminate without depth limits
- **A* Search**: Similar to Best First Search but includes path cost from start, guaranteeing optimality while Best First 
earch may not
- **Bidirectional Search**: Searches from both initial and goal states but is complex to implement for 8-puzzle

**Best First Search Properties**:
- Time Complexity: O(bᵐ) in worst case where b is branching factor and m is maximum search depth
- Space Complexity: O(bᵐ) to store all generated nodes
- Not complete: May get trapped in infinite loops without proper cycle detection
- Not optimal: Unlike A*, it doesn't guarantee the shortest path because it only considers estimated cost to goal
- Uses significantly less memory than BFS for large problems
- Performs well when the heuristic is a good approximation of actual distance to goal

The 8-puzzle typically has a branching factor of about 3 and an average solution depth of 22 steps for random instances, 

making it a perfect candidate for heuristic-guided search approaches like Best First Search.
*/
