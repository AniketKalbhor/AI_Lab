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

    static int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

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
