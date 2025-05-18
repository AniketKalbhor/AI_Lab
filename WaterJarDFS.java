
import java.util.*;

class StateDFS {

    int a, b;

    public StateDFS(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StateDFS StateDFS = (StateDFS) obj;
        return a == StateDFS.a && b == StateDFS.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}

public class WaterJarDFS {

    static final int JAR_A_CAPACITY = 4;
    static final int JAR_B_CAPACITY = 3;
    static final int TARGET = 2;

    public static void main(String[] args) {
        solveWaterJarProblem();
    }

    public static void solveWaterJarProblem() {
        Stack<StateDFS> stack = new Stack<>();
        Set<StateDFS> visited = new HashSet<>();
        Map<StateDFS, StateDFS> parent = new HashMap<>(); // To trace the path

        // Initial StateDFS: both jars are empty
        StateDFS initialStateDFS = new StateDFS(0, 0);
        stack.push(initialStateDFS);
        visited.add(initialStateDFS);

        while (!stack.isEmpty()) {
            StateDFS current = stack.pop();

            // Check if we have reached the goal
            if (current.a == TARGET) {
                printSolution(current, parent);
                return;
            }

            // Generate all possible next StateDFSs
            List<StateDFS> nextStateDFSs = generateNextStateDFSs(current);

            for (StateDFS next : nextStateDFSs) {
                if (!visited.contains(next)) {
                    stack.push(next);
                    visited.add(next);
                    parent.put(next, current); // Keep track of the StateDFS that led here
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static List<StateDFS> generateNextStateDFSs(StateDFS current) {
        int a = current.a, b = current.b;
        List<StateDFS> nextStateDFSs = new ArrayList<>();

        // Fill Jar A
        nextStateDFSs.add(new StateDFS(JAR_A_CAPACITY, b));

        // Fill Jar B
        nextStateDFSs.add(new StateDFS(a, JAR_B_CAPACITY));

        // Empty Jar A
        nextStateDFSs.add(new StateDFS(0, b));

        // Empty Jar B
        nextStateDFSs.add(new StateDFS(a, 0));

        // Pour from Jar A to Jar B
        int pourToB = Math.min(a, JAR_B_CAPACITY - b);
        nextStateDFSs.add(new StateDFS(a - pourToB, b + pourToB));

        // Pour from Jar B to Jar A
        int pourToA = Math.min(b, JAR_A_CAPACITY - a);
        nextStateDFSs.add(new StateDFS(a + pourToA, b - pourToA));

        return nextStateDFSs;
    }

    public static void printSolution(StateDFS goal, Map<StateDFS, StateDFS> parent) {
        List<StateDFS> path = new ArrayList<>();
        StateDFS current = goal;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        System.out.println("Solution found! Steps to achieve the goal:");
        for (StateDFS StateDFS : path) {
            System.out.println(StateDFS);
        }
    }
}

// # Water Jar

// Problem - DFS Approach Explanation

// ## Problem Statement:
// The Water Jar problem is a classic puzzle where we have two jars of different capacities (in this case, 4 and 3 liters) and we need to measure exactly 2 liters of water. The jars have no measurement markings. We can fill jars completely, empty them completely, or pour water from one jar to another. The solution is found using a Depth-First Search (DFS) algorithm to explore all possible states until the goal of having exactly 2 liters in one of the jars is reached.

// ## 🔹 Constraints:
// - We have two jars: Jar A with 4 liters capacity and Jar B with 3 liters capacity
// - Initially, both jars are empty (0,0)
// - The goal is to get exactly 2 liters in Jar A
// - The only allowed operations are: fill a jar completely, empty a jar completely, or pour water from one jar to another until either the source jar is empty or the destination jar is full

// ## 🔹 Input:
// - No direct input from the user (the jar capacities and target are hardcoded constants)

// ## 🔹 Output:
// - A sequence of jar states (a,b) that leads from the initial state (0,0) to a state where Jar A contains exactly 2 liters

// ## ⚙️ Functions Explained

// 1. **solveWaterJarProblem()**: The main function that implements the DFS algorithm using a stack to track states to explore. It maintains a set of visited states to avoid cycles and uses a map to track parent states for reconstructing the solution path.

// 2. **generateNextStates(StateDFS current)**: This function takes a current state and generates all possible next states by applying the six allowed operations:
//    - Fill Jar A completely
//    - Fill Jar B completely
//    - Empty Jar A completely
//    - Empty Jar B completely
//    - Pour water from Jar A to Jar B (until B is full or A is empty)
//    - Pour water from Jar B to Jar A (until A is full or B is empty)

// 3. **printSolution(StateDFS goal, Map<StateDFS, StateDFS> parent)**: Once the goal state is found, this function reconstructs and prints the sequence of steps from the initial state to the goal state using the parent map.

// ## 📊 Variables Used

// 1. **StateDFS class

//   **: Represents the state of the two jars at any point:
//    - `int a`: The amount of water in Jar A
//    - `int b`: The amount of water in Jar B

// 2. **Constants**:
//    - `JAR_A_CAPACITY`: The maximum capacity of Jar A (4 liters)
//    - `JAR_B_CAPACITY`: The maximum capacity of Jar B (3 liters)
//    - `TARGET`: The target amount of water to measure (2 liters)

// 3. **Data Structures**:
//    - `Stack<StateDFS>`: For the DFS frontier - states that need to be explored
//    - `Set<StateDFS>`: To track visited states and avoid cycles
//    - `Map<StateDFS, StateDFS>`: To track the parent of each state for path reconstruction

// ## 🔁 Flow of Execution

// 1. Start with an initial state (0,0) where both jars are empty
// 2. Push the initial state onto the stack and mark it as visited
// 3. While the stack is not empty:
//    - Pop a state from the stack
//    - Check if it's the goal state (Jar A contains exactly 2 liters)
//    - If it's the goal state, reconstruct and print the solution path
//    - If it's not the goal state, generate all possible next states
//    - For each unvisited next state:
//      - Push it onto the stack
//      - Mark it as visited
//      - Record its parent
// 4. If the stack becomes empty without finding a goal state, report that no solution exists

// ## Short State Space Tree:

// ```
//                       (0,0) [Initial State]
//                      /    \     \     \     \     \
//                     /      \     \     \     \     \
//                    /        \     \     \     \     \
//                (4,0)      (0,3)  (0,0)  (0,0)  (0,0)  (0,0)
//               /    \        |     [...]  [...]  [...]  [...]
//              /      \       |
//            (4,3)   (1,3)   (3,0)
//            /  \     / \     / \
//           /    \   /   \   /   \
//         [...] [...]  (1,0) [...] [...]
//                      /
//                     /
//                   (0,1)
//                    /
//                   /
//                  (4,1)
//                   /
//                  /
//                (2,3) [Goal State]
// ```

// Note: The actual state space is much larger, with many branches. This simplified tree shows just one possible path to the goal state.

// The DFS approach will explore deeper into one branch before backtracking and trying other possibilities, which can be efficient when solutions are deep in the state space. The Water Jar problem is well-suited for this approach because the total number of possible states is relatively small (5×4=20 states), and the solution requires a specific sequence of operations.
