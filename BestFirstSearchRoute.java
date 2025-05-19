
import java.util.*;

class Cell implements Comparable<Cell> {

    int x, y;
    int heuristic;
    Cell parent;

    public Cell(int x, int y, int heuristic, Cell parent) {
        this.x = x;
        this.y = y;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    @Override
    public int compareTo(Cell other) {
        return Integer.compare(this.heuristic, other.heuristic);
    }
}

public class BestFirstSearchRoute {

    static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static void bestFirstSearch(int[][] grid, int sx, int sy, int gx, int gy) {
        int rows = grid.length;
        int cols = grid[0].length;

        PriorityQueue<Cell> openList = new PriorityQueue<>();
        boolean[][] visited = new boolean[rows][cols];

        Cell start = new Cell(sx, sy, manhattan(sx, sy, gx, gy), null);
        openList.add(start);
        visited[sx][sy] = true;

        while (!openList.isEmpty()) {
            Cell current = openList.poll();

            if (current.x == gx && current.y == gy) {
                System.out.println("Goal reached!");
                printPath(current);
                return;
            }

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (isValid(newX, newY, rows, cols, grid, visited)) {
                    visited[newX][newY] = true;
                    int h = manhattan(newX, newY, gx, gy);
                    Cell neighbor = new Cell(newX, newY, h, current);
                    openList.add(neighbor);
                }
            }
        }

        System.out.println("No path found.");
    }

    private static int manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private static boolean isValid(int x, int y, int rows, int cols, int[][] grid, boolean[][] visited) {
        return x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 0 && !visited[x][y];
    }

    private static void printPath(Cell goal) {
        List<Cell> path = new ArrayList<>();
        while (goal != null) {
            path.add(goal);
            goal = goal.parent;
        }
        Collections.reverse(path);
        for (Cell cell : path) {
            System.out.println("(" + cell.x + ", " + cell.y + ")");
        }
    }

    public static void main(String[] args) {
        int[][] grid = {
            {0, 0, 0, 0},
            {1, 1, 0, 1},
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
        };

        int startX = 0, startY = 0;
        int goalX = 4, goalY = 3;

        bestFirstSearch(grid, startX, startY, goalX, goalY);
    }
}

// Best-

// First Search
// Route Finding
// Algorithm Explanation
// Problem Statement:
// This program implements
// a Best-
// First Search
// algorithm to
// find a
// path from
// start coordinates
// to goal
// coordinates on a 2D
// grid.The algorithm
// navigates through
// a grid where 0
// represents an
// open cell and 1
// represents an obstacle/wall. The search
// prioritizes cells
// that are
// closer to
// the goal
// according to
// the Manhattan
// distance heuristic.Theory:🔹Best-
// First Search
// is a
// search algorithm
// that explores
// a graph
// by expanding
// the most
// promising node
// according to
// a specified
// heuristic function.🔹
// Unlike breadth-
// first search
// which explores
// all neighbors
// at a
// given depth, best-
// first search
// prioritizes nodes
// that appear
// closer to
// the goal.🔹
// The Manhattan

// distance heuristic (|x1-x2| + |y1-y2|) is used to estimate how close a cell is to the goal.
// 🔹 This is a greedy strategy that always chooses the path that looks best at the moment.
// Constraints:
// 🔹 The grid layout must be represented as a 2D array where 0 indicates traversable space and 1 indicates a wall/obstacle.
// 🔹 The algorithm assumes movement in only four directions: up, down, left,

// and right (no diagonal movement).
// 🔹 Start and goal coordinates must be valid positions within the grid boundaries.
// Input:
// 🔹 A 2D

// grid (integer array) where 0 represents walkable cells and 1 represents obstacles.
// 🔹

// Start coordinates (sx, sy) on the grid.
// 🔹

// Goal coordinates (gx, gy) on the grid.
// Output:
// 🔹 A sequence of coordinates representing the path from start to goal, if one exists.
// 🔹 A message indicating "No path found" if no valid path exists.
// ⚙️ Functions Explained:
// bestFirstSearch:

// Takes the grid and start/goal coordinates as input.
// Manages the main search process using a priority queue to always explore the most promising cell first.
// Uses a visited array to avoid revisiting cells and prevent infinite loops.
// When the goal is reached, reconstructs and prints the path.

// manhattan:

// Calculates the Manhattan distance between two points (x1,y1) and (x2,y2).
// This heuristic estimates how close a cell is to the goal by summing the absolute differences in x and y coordinates.
// Lower values indicate cells that are closer to the goal.

// isValid:

// Checks if a given position is valid for traversal by verifying:

// It's within grid boundaries
// The cell is not

// an obstacle (value 0)
// The cell hasn't been visited before



// printPath:

// Reconstructs the path from start to goal using parent references.
// Stores the path in reverse order then prints it correctly by reversing the collection.

// 📊 Variables Used:
// Cell Class:

// x, y: Coordinates of the cell in the grid
// heuristic: Manhattan distance to the goal
// parent: Reference to the previous cell in the path

// In bestFirstSearch:

// openList: A priority queue that always provides the cell with the lowest heuristic value
// visited: A boolean array tracking which cells have been explored
// start: The initial cell with its heuristic value
// current: The cell being currently examined
// directions: Array defining the four possible movement directions

// 🔁 Flow of Execution:

// Initialize the priority queue with the start cell
// Mark the start cell as visited
// While the priority queue is not empty:

// Poll the cell with lowest heuristic value
// If this cell is the goal, reconstruct and print the path
// Otherwise, for each of the four possible directions:

// Calculate the new position
// If the new position is

// valid (in bounds, not a wall, not visited):

// Mark it as visited
// Calculate its heuristic value
// Add it to the priority queue with the current cell as its parent






// If the queue becomes empty without finding the goal, report that no path exists

// Short State Space Tree:
//                       (0,0)h=7
//                       /    \
//                 (0,1)h=6   (1,0)h=6
//                 /    \       /    \
//         (0,2)h=5  (1,1)h=5  ...   ...
//           /          |
//          ...        ...
// In this tree, each node represents a cell with its coordinates and heuristic value. The search always expands the node with the lowest heuristic value first, which means it gravitates toward cells that appear to be closer to the goal.
// The key difference from other search algorithms is that best-first search doesn't necessarily explore all nodes at a given depth before moving deeper - it always chooses the most promising node based solely on the heuristic function.
