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
