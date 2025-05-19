import java.util.*;

class Node implements Comparable<Node> {
    int x, y;
    int gCost, hCost, fCost;
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Node other) {
        return Integer.compare(this.fCost, other.fCost);
    }
}

public class AStarRouteFinding {

    static int[][] grid = {
            { 0, 1, 0, 0, 0 },
            { 0, 1, 0, 1, 0 },
            { 0, 0, 0, 1, 0 },
            { 1, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0 }
    };

    static int rows = grid.length;
    static int cols = grid[0].length;
    static int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static List<Node> findPath(Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[][] closedList = new boolean[rows][cols];

        start.gCost = 0;
        start.hCost = heuristic(start, goal);
        start.fCost = start.gCost + start.hCost;
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.x == goal.x && current.y == goal.y) {
                return reconstructPath(current);
            }

            closedList[current.x][current.y] = true;

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (!isValid(newX, newY) || grid[newX][newY] == 1 || closedList[newX][newY]) {
                    continue;
                }

                Node neighbor = new Node(newX, newY);
                int tentativeG = current.gCost + 1;

                if (tentativeG < neighbor.gCost || !openList.contains(neighbor)) {
                    neighbor.gCost = tentativeG;
                    neighbor.hCost = heuristic(neighbor, goal);
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = current;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return new ArrayList<>(); // No path found
    }

    public static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    public static int heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Node start = new Node(0, 0);
        Node goal = new Node(4, 4);

        List<Node> path = findPath(start, goal);

        if (!path.isEmpty()) {
            System.out.println("Path found:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}

// A* Route Finding Algorithm Explanation
// Problem Statement:
// The AStarRouteFinding class implements the A* search algorithm to find the shortest path from a start point to a goal point in a 2D grid. The algorithm navigates through a grid where 0 represents passable cells and 1 represents obstacles or walls. It uses the Manhattan distance heuristic to guide the search toward the goal efficiently.
// Theory:
// 🔹 A* is an informed search algorithm that combines the advantages of both Dijkstra's algorithm (guaranteeing the shortest path) and greedy best-first search (using heuristics to guide the search).
// 🔹 A* uses an evaluation function f(n) = g(n) + h(n) where:

// g(n) is the cost to reach node n from the start node
// h(n) is the heuristic that estimates the cost from n to the goal
// f(n) is the estimated total cost of the path through node n

// 🔹 The algorithm maintains two lists:

// Open list: Contains nodes that have been discovered but not fully explored
// Closed list: Contains nodes that have been fully explored

// 🔹 The Manhattan distance heuristic (sum of horizontal and vertical distances) is used as it's admissible for grid-based movement with no diagonal movement.
// Constraints:
// 🔹 Movement is restricted to four directions: up, down, left, right (no diagonal movement)
// 🔹 Grid cells with value 1 represent obstacles that cannot be traversed
// 🔹 The algorithm assumes there's a valid path between start and goal
// Input:
// 🔹 A 2D grid where 0 represents passable cells and 1 represents obstacles
// 🔹 Start coordinates (x, y)
// 🔹 Goal coordinates (x, y)
// Output:
// 🔹 A sequence of coordinates representing the shortest path from start to goal
// 🔹 "No path found" message if no valid path exists
// ⚙️ Functions Explained:

// findPath(Node start, Node goal):

// The main A* algorithm implementation
// Uses a priority queue to always explore the most promising node first
// Returns the complete path if found, or an empty list if no path exists


// isValid(int x, int y):

// Checks if a position is within the grid boundaries
// Returns true if the coordinates are valid, false otherwise


// heuristic(Node a, Node b):

// Calculates the Manhattan distance between two nodes
// Manhattan distance = |x₁-x₂| + |y₁-y₂|


// reconstructPath(Node current):

// Traces back from the goal node to the start node using parent references
// Creates and returns the complete path in correct order


// main(String[] args):

// Sets up a sample grid and start/goal positions
// Calls the findPath function and displays the result



// 📊 Variables Used:

// Node Class Variables:

// x, y: Coordinates of the node in the grid
// gCost: Cost from start to this node
// hCost: Estimated cost from this node to goal (heuristic)
// fCost: Total estimated cost (gCost + hCost)
// parent: Reference to the previous node in the path


// AStarRouteFinding Class Variables:

// grid: 2D array representing the environment (0=passable, 1=obstacle)
// rows, cols: Dimensions of the grid
// directions: Array of possible movement directions (up, down, left, right)



// 🔁 Flow of Execution:

// Initialize the open list (priority queue) with the start node
// Set the start node's g-cost to 0 and calculate its h-cost and f-cost
// While the open list is not empty:
// a. Get the node with the lowest f-cost from the open list
// b. If this node is the goal, reconstruct and return the path
// c. Add the current node to the closed list
// d. For each neighbor of the current node:
// i. Skip if it's an obstacle or already in the closed list
// ii. Calculate the tentative g-cost (current g-cost + 1)
// iii. If the neighbor isn't in the open list or has a better g-cost:
// - Update the neighbor's g-cost, h-cost, f-cost, and parent
// - Add to open list if not already there
// If the open list becomes empty without reaching the goal, return an empty path

// Short State Space Tree:
// Start (0,0) [f=8]
// │
// ├── (0,1) [f=8]
// │   │
// │   ├── (0,2) [f=8]
// │   │   │
// │   │   └── ... → (2,2) → (3,2) → (4,2) → (4,3) → Goal (4,4)
// │   │
// │   └── (1,1) [f=8] ✗ (obstacle)
// │
// └── (1,0) [f=8]
//     │
//     └── ... (other paths with higher f-costs)
// The algorithm always explores the node with the lowest f-cost first, efficiently guiding the search toward the goal while avoiding obstacles.