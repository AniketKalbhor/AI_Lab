
import java.util.*;

class State {

    int a, b;

    public State(int a, int b) {
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
        State state = (State) obj;
        return a == state.a && b == state.b;
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

public class WaterJarBFS {

    static final int JAR_A_CAPACITY = 4;
    static final int JAR_B_CAPACITY = 3;
    static final int TARGET = 2;

    public static void main(String[] args) {
        solveWaterJarProblem();
    }

    public static void solveWaterJarProblem() {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parent = new HashMap<>(); // To trace the path

        // Initial state: both jars are empty
        State initialState = new State(0, 0);
        queue.offer(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            // Check if we have reached the goal
            if (current.a == TARGET) {
                printSolution(current, parent);
                return;
            }

            // Generate all possible next states
            List<State> nextStates = generateNextStates(current);

            for (State next : nextStates) {
                if (!visited.contains(next)) {
                    queue.offer(next);
                    visited.add(next);
                    parent.put(next, current); // Keep track of the state that led here
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static List<State> generateNextStates(State current) {
        int a = current.a, b = current.b;
        List<State> nextStates = new ArrayList<>();

        // Fill Jar A
        nextStates.add(new State(JAR_A_CAPACITY, b));

        // Fill Jar B
        nextStates.add(new State(a, JAR_B_CAPACITY));

        // Empty Jar A
        nextStates.add(new State(0, b));

        // Empty Jar B
        nextStates.add(new State(a, 0));

        // Pour from Jar A to Jar B
        int pourToB = Math.min(a, JAR_B_CAPACITY - b);
        nextStates.add(new State(a - pourToB, b + pourToB));

        // Pour from Jar B to Jar A
        int pourToA = Math.min(b, JAR_A_CAPACITY - a);
        nextStates.add(new State(a + pourToA, b - pourToA));

        return nextStates;
    }

    public static void printSolution(State goal, Map<State, State> parent) {
        List<State> path = new ArrayList<>();
        State current = goal;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        System.out.println("Solution found! Steps to achieve the goal:");
        for (State state : path) {
            System.out.println(state);
        }
    }
}

// Water Jar Problem: BFS Approach Explanation
// Problem Statement:
// The Water Jar problem involves two jars with capacities of 4 liters (Jar A) and 3 liters (Jar B). The goal is to measure exactly 2 liters of water in Jar A through a series of operations: filling jars completely, emptying jars completely, or pouring water from one jar to another. The problem requires finding the shortest sequence of steps to achieve this goal.
// 🔹 Constraints:

// The jars have no measurement markings
// Only allowed operations are: fill a jar completely, empty a jar completely, or pour water from one jar to another until either the source jar is empty or the destination jar is full
// The goal is to have exactly 2 liters in Jar A (the 4-liter jar)

// 🔹 Input:
// No explicit input - the program uses fixed values:

// JAR_A_CAPACITY = 4
// JAR_B_CAPACITY = 3
// TARGET = 2 (liters in Jar A)

// 🔹 Output:
// The sequence of states (amount of water in each jar) to reach the goal state
// ⚙️ Functions Explained:
// solveWaterJarProblem():

// Main function that implements BFS to find the shortest path to the goal
// Uses a queue to store states to be explored, a set to track visited states, and a map to trace the path

// generateNextStates(State current):

// Generates all possible next states from the current state
// Implements the six possible operations:

// Fill Jar A completely
// Fill Jar B completely
// Empty Jar A completely
// Empty Jar B completely
// Pour from Jar A to Jar B (until B is full or A is empty)
// Pour from Jar B to Jar A (until A is full or B is empty)



// printSolution(State goal, Map<State, State> parent):

// Traces back the path from goal to initial state using the parent map
// Reverses the path and prints the sequence of states

// 📊 Variables Used:
// State class:

// a: amount of water in Jar A (liters)
// b: amount of water in Jar B (liters)
// parent: reference to previous state (used for path reconstruction)

// In solveWaterJarProblem():

// queue: stores states to be explored in BFS order
// visited: set of states already visited to avoid cycles
// parent: maps each state to its parent state for path reconstruction

// 🔁 Flow of Execution:

// Start with the initial state (0,0) - both jars empty
// Add initial state to the queue and mark it as visited
// While the queue is not empty:

// Dequeue the next state to explore
// If the current state is the goal state (2 liters in Jar A), reconstruct and print the path
// Otherwise, generate all possible next states:

// For each valid next state not already visited:

// Add it to the queue
// Mark it as visited
// Store its parent for path reconstruction

// If the queue empties without finding a solution, report that no solution exists

// Short State Space Tree:
//                         (0,0) Initial State
//                           /|\
//                          / | \
//             Fill A      /  |  \     Fill B
//                        /   |   \
//                    (4,0)  (0,0) (0,3)
//                     /|\    [...]  /|\
//                    / | \         / | \
//                   /  |  \       /  |  \
//               (4,3)(0,0)(1,3) (...)(...)(...)
//                /|   [...]  /|
//               / |         / |
//              /  |        /  |
//          (0,3)(4,0)  (1,0)(0,1)
//           /|   [...]  [...]
//          / |
//         /  |
//     (3,0)(0,3)
//      /    [...]
//     /
//  (3,3)
//   /|
//  / |
// /  |
// (4,2) Goal State!
// The BFS approach ensures that the shortest path to the goal state is found by exploring all states at a given depth before moving to the next depth level. This is particularly important for problems like the Water Jar where multiple solution paths may exist, but we want the most efficient one.
