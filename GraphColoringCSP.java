import java.util.*;

public class GraphColoringCSP {

    static int V; // Number of vertices
    static List<List<Integer>> graph = new ArrayList<>();
    static int[] colors; // colors[i] = color assigned to vertex i

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        V = scanner.nextInt();
        colors = new int[V];

        // Initialize adjacency list
        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        System.out.print("Enter number of edges: ");
        int E = scanner.nextInt();
        System.out.println("Enter " + E + " edges (u v):");

        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u); // because the graph is undirected
        }

        System.out.print("Enter number of colors (m): ");
        int m = scanner.nextInt();

        if (solve(0, m)) {
            System.out.println("✅ Coloring possible:");
            for (int i = 0; i < V; i++)
                System.out.println("Vertex " + i + " ---> Color " + colors[i]);
        } else {
            System.out.println("❌ No solution exists using " + m + " colors.");
        }

        scanner.close();
    }

    // Recursive backtracking function
    private static boolean solve(int vertex, int m) {
        if (vertex == V) {
            return true; // All vertices are assigned
        }

        for (int color = 1; color <= m; color++) {
            if (isSafe(vertex, color)) {
                colors[vertex] = color;
                if (solve(vertex + 1, m))
                    return true;
                colors[vertex] = 0; // Backtrack
            }
        }

        return false; // No valid color found
    }

    // Check if the color can be assigned to vertex
    private static boolean isSafe(int vertex, int color) {
        for (int neighbor : graph.get(vertex)) {
            if (colors[neighbor] == color)
                return false;
        }
        return true;
    }
}
