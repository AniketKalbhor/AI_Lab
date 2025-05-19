
import java.util.*;

class State {

    int missionariesLeft, cannibalsLeft, boat; // boat: 0 = left, 1 = right
    State parent;

    public State(int m, int c, int b) {
        this.missionariesLeft = m;
        this.cannibalsLeft = c;
        this.boat = b;
    }

    public boolean isValid() {
        int mRight = 3 - missionariesLeft;
        int cRight = 3 - cannibalsLeft;

        // Check bounds
        if (missionariesLeft < 0 || cannibalsLeft < 0 || missionariesLeft > 3 || cannibalsLeft > 3) {
            return false;
        }

        // Missionaries should not be outnumbered on either bank
        if ((missionariesLeft > 0 && missionariesLeft < cannibalsLeft)
                || (mRight > 0 && mRight < cRight)) {
            return false;
        }

        return true;
    }

    public boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0 && boat == 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) {
            return false;
        }
        State s = (State) obj;
        return missionariesLeft == s.missionariesLeft
                && cannibalsLeft == s.cannibalsLeft
                && boat == s.boat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionariesLeft, cannibalsLeft, boat);
    }

    @Override
    public String toString() {
        return "[Left -> M: " + missionariesLeft + ", C: " + cannibalsLeft + ", Boat: " + (boat == 0 ? "L" : "R") + "]";
    }
}

public class MissionariesAndCannibalsBFS {

    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        State initialState = new State(3, 3, 0);
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.offer(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.isGoal()) {
                printPath(current);
                return;
            }

            for (State next : generateNextStates(current)) {
                if (next.isValid() && !visited.contains(next)) {
                    next.parent = current;
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static List<State> generateNextStates(State s) {
        List<State> successors = new ArrayList<>();

        // Possible moves: (m, c)
        int[][] moves = {
            {1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}
        };

        for (int[] move : moves) {
            int m = move[0];
            int c = move[1];

            State newState;
            if (s.boat == 0) { // Boat on the left side
                newState = new State(s.missionariesLeft - m, s.cannibalsLeft - c, 1);
            } else { // Boat on the right side
                newState = new State(s.missionariesLeft + m, s.cannibalsLeft + c, 0);
            }

            successors.add(newState);
        }

        return successors;
    }

    public static void printPath(State goal) {
        List<State> path = new ArrayList<>();
        State current = goal;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        System.out.println("Solution found! Steps to achieve the goal:");
        for (State s : path) {
            System.out.println(s);
        }
    }
}

// Missionaries and Cannibals Problem Explanation
// Problem Statement:
// The Missionaries and Cannibals problem is a classic river crossing puzzle where three missionaries and three cannibals must cross a river using a boat that can hold at most two people. The constraint is that at no point should cannibals outnumber missionaries on either bank, or the missionaries will be eaten. The goal is to find a sequence of moves that safely gets everyone across the river.
// Theory:
// 🔹 Constraints:

// The boat can carry at most 2 people
// The boat needs at least 1 person to move across
// Cannibals cannot outnumber missionaries on either bank (unless there are 0 missionaries on that bank)
// All 3 missionaries and 3 cannibals must cross from left bank to right bank

// 🔹 Initial State: 3 missionaries, 3 cannibals, and the boat on the left bank
// 🔹 Goal State: 0 missionaries, 0 cannibals on the left bank, and the boat on the right bank
// ⚙️ Functions Explained

// State Class: Represents a configuration in the problem

// Tracks number of missionaries and cannibals on the left bank and boat position
// Contains validation logic to check if a state is safe
// Implements equality and hash code for state comparison in sets


// solve(): The main driver function that implements BFS

// Creates initial state and search structures (queue and visited set)
// Performs breadth-first traversal of the state space
// Terminates when goal is found or all possible states are explored


// generateNextStates(): Generates all possible next states from a current state

// Implements the five possible moves: (1,0), (2,0), (0,1), (0,2), (1,1)
// Takes boat position into account when generating new states


// printPath(): Reconstructs and displays the solution path

// Traces back from goal to initial state using parent pointers
// Reverses the path to show steps in correct order



// 📊 Variables Used

// missionariesLeft, cannibalsLeft: Track number of people on the left bank
// boat: Indicates boat position (0 = left bank, 1 = right bank)
// parent: Reference to the previous state, used to reconstruct the solution path
// queue: BFS queue to track states to explore
// visited: HashSet to track already visited states
// moves: 2D array of possible moves (missionaries, cannibals)

// 🔁 Flow of Execution

// Create the initial state (3,3,0) and add it to the queue and visited set
// While the queue is not empty:

// Dequeue a state
// Check if it's the goal state (0,0,1)
// If yes, reconstruct and print the solution path
// If no, generate all possible next states by applying moves
// For each valid next state not already visited:

// Set its parent to the current state
// Add it to the visited set
// Enqueue it for exploration




// If the queue becomes empty without finding a goal state, report no solution

// Short State Space Tree:
// Initial [M:3, C:3, Boat:L]
// ├── [M:2, C:2, Boat:R] (move 1M,1C right)
// │   ├── [M:3, C:2, Boat:L] (move 1M left)
// │   │   └── [M:1, C:2, Boat:R] (move 2M right)
// │   │       └── [M:2, C:2, Boat:L] (move 1M left)
// │   │           └── [M:0, C:2, Boat:R] (move 2M right)
// │   │               └── [M:0, C:3, Boat:L] (move 1C left)
// │   │                   └── [M:0, C:1, Boat:R] (move 2C right)
// │   │                       └── [M:1, C:1, Boat:L] (move 1M,1C left)
// │   │                           └── [M:0, C:0, Boat:R] (move 1M,1C right) ✓ GOAL
// │   └── [M:2, C:3, Boat:L] (move 0C,1C left)
// ├── [M:2, C:3, Boat:R] (move 1M,0C right)
// └── [M:3, C:2, Boat:R] (move 0M,1C right)
// This BFS approach systematically explores the state space level by level, ensuring that the first solution found is the shortest possible path. The state representation and validation logic are crucial for correctly modeling the problem constraints, while the parent references allow reconstruction of the solution path once the goal is reached.
