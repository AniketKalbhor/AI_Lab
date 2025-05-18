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


/*
# Graph Coloring Problem Explanation

## Problem Statement:
The graph coloring problem involves assigning colors to vertices of a graph such that no adjacent vertices share the same color, using at most M colors. This is a classic constraint satisfaction problem with applications in scheduling, register allocation in compilers, map coloring, and more.

## Explanation of functions used:

- **solve()**: The main function that initiates the backtracking process from the first vertex (row 0).
- **backtrack()**: A recursive function that tries to assign colors to each vertex, moving row by row.
- **isSafe()**: Checks if a particular color assignment for a vertex doesn't conflict with its neighbors.
- **printSolution()**: Displays the final coloring assignment with a visual representation.

## Basic explanation of general approach:

The solution uses a backtracking algorithm, which is a depth-first search approach that explores potential solutions by trying each possible color for a vertex, then moving on to the next vertex. If at any point a vertex cannot be colored without creating a conflict, the algorithm backtracks to try a different color for a previous vertex.

This approach systematically explores the state space, pruning branches that cannot lead to valid solutions as early as possible. The algorithm guarantees finding a solution if one exists, or determining that no solution exists.

## Explanation of variables:

- **n**: Number of vertices in the graph
- **colors[]**: Array storing color assignments (1 to m) for each vertex
- **graph**: Adjacency list representation where graph.get(i) contains all neighbors of vertex i
- **m**: Maximum number of colors available to use
- **vertex**: Current vertex being processed in the backtracking
- **V**: Total number of vertices (similar to n)

## Flow of execution:

1. The user inputs the number of vertices, edges, and colors.
2. The program builds an adjacency list representation of the graph.
3. The backtracking algorithm starts at vertex 0 and tries to assign colors.
4. For each vertex, it tries each color (1 to m) and checks if it's safe to assign.
5. If safe, it makes the assignment and recursively processes the next vertex.
6. If all vertices get colored successfully, the solution is printed.
7. If no assignment works for a vertex, it backtracks to the previous vertex and tries a different color.
8. If backtracking exhausts all possibilities without finding a solution, it reports that no solution exists.

## Short State Space tree:

```
                      Start (No colors assigned)
                     /       |       \
           (V0=color1)  (V0=color2)  (V0=color3)
            /    \         /    \        /    \
   (V1=c1)   (V1=c2)   (V1=c1)  (V1=c3)  ...  ...
     |         |         |        |
   Invalid   (V2=c3)   Invalid  (V2=c2)
               |                  |
            (V3=c1)             ...
               |
            Solution!
```

In this tree:
- Each level represents a vertex being assigned a color
- Branches represent different color choices
- "Invalid" nodes are pruned because they violate constraints
- The algorithm traverses this tree using depth-first search with backtracking
- When it reaches a leaf node with all vertices colored, it has found a solution

The graph coloring problem is NP-complete, meaning the worst-case time complexity is exponential (O(m^n)), where m is the number of colors and n is the number of vertices.
 However, the backtracking approach with constraint checking significantly reduces the practical running time by quickly pruning invalid branches of the search tree.
*/
