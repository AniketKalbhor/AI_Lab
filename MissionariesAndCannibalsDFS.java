
import java.util.*;

class State {

    int missionariesLeft, cannibalsLeft, boat; // 0 = left, 1 = right
    State parent;

    public State(int m, int c, int b) {
        this.missionariesLeft = m;
        this.cannibalsLeft = c;
        this.boat = b;
    }

    public boolean isValid() {
        int mRight = 3 - missionariesLeft;
        int cRight = 3 - cannibalsLeft;

        if (missionariesLeft < 0 || cannibalsLeft < 0 || missionariesLeft > 3 || cannibalsLeft > 3) {
            return false;
        }

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
        return "[Left: M=" + missionariesLeft + ", C=" + cannibalsLeft + ", Boat=" + (boat == 0 ? "L" : "R") + "]";
    }
}

public class MissionariesAndCannibalsDFS {

    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        State initialState = new State(3, 3, 0);
        Set<State> visited = new HashSet<>();

        State goalState = dfs(initialState, visited);
        if (goalState != null) {
            printPath(goalState);
        } else {
            System.out.println("No solution found.");
        }
    }

    public static State dfs(State current, Set<State> visited) {
        visited.add(current);

        if (current.isGoal()) {
            return current;
        }

        for (State next : generateNextStates(current)) {
            if (next.isValid() && !visited.contains(next)) {
                next.parent = current;
                State result = dfs(next, visited);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    public static List<State> generateNextStates(State s) {
        List<State> successors = new ArrayList<>();
        int[][] moves = {
            {1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}
        };

        for (int[] move : moves) {
            int m = move[0];
            int c = move[1];

            State newState;

            if (s.boat == 0) { // Boat on left: move people to right
                newState = new State(s.missionariesLeft - m, s.cannibalsLeft - c, 1);
            } else { // Boat on right: move people to left
                newState = new State(s.missionariesLeft + m, s.cannibalsLeft + c, 0);
            }

            if (newState.isValid()) {
                successors.add(newState);
            }
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
        System.out.println("Solution found! Steps:");
        for (State s : path) {
            System.out.println(s);
        }
    }
}

// Missionaries and Cannibals Problem Solution Using DFS
// Problem Statement:
// The Missionaries and Cannibals problem is a classic river crossing puzzle. Three missionaries and three cannibals need to cross a river using a boat that can hold at most two people. If cannibals outnumber missionaries on either bank, the missionaries will be eaten. The goal is to find a sequence of boat crossings that safely gets everyone to the other side.
// Theory:
// 🔹 Constraints:

// The boat can carry at most 2 people at a time
// The boat cannot cross the river by itself (at least 1 person must be in it)
// If cannibals outnumber missionaries on either bank, the missionaries are eaten
// Initially, all 3 missionaries and 3 cannibals are on the left bank
// The goal is to get everyone safely to the right bank

// 🔹 Input: No explicit input; the

// problem starts with the initial state (3,3,0) - 3 missionaries and 3 cannibals on left bank, boat on left side
// 🔹 Output: A sequence of states (boat crossings) leading to the goal state where everyone is safely on the right bank
// ⚙️ Functions Explained

// solve(): The main function that initiates the DFS algorithm to find a path from the initial state to the goal state.
// dfs(State current, Set<State> visited): The recursive depth-first search function that explores the state space by trying different moves from the current state. It returns the goal state if found or null otherwise.
// generateNextStates(State s): Generates all possible next states from the current state based on valid moves. It considers all possible combinations of missionaries and cannibals (1M, 2M, 1C, 2C, or 1M+1C) that can cross the river in the boat.
// isValid(): Checks if a state is valid by ensuring:

// No negative counts or counts exceeding 3
// Missionaries are not outnumbered by cannibals on either bank (unless there are 0 missionaries on that bank)


// isGoal(): Checks if the current state is the goal state (0 missionaries and 0 cannibals on the left bank, and the boat on the right bank).
// printPath(): Reconstructs and prints the solution path from the initial state to the goal state.

// 📊 Variables Used

// State class fields

//  :

// missionariesLeft: Number of missionaries on the left bank
// cannibalsLeft: Number of cannibals on the left bank
// boat: Position of the boat (0 = left bank, 1 = right bank)
// parent: Reference to the parent state (previous state in the solution path)


// Local variables in solve():

// initialState: The starting state (3,3,0)
// visited: Set to keep track of visited states to avoid cycles


// Local variables in generateNextStates():

// successors: List to store all valid successor states
// moves: 2D array representing all possible combinations of missionaries and cannibals that can cross the river



// 🔁 Flow of Execution

// The program starts by creating an initial state with 3 missionaries, 3 cannibals, and the boat on the left bank.
// It initializes a visited set to keep track of explored states.
// The DFS algorithm is invoked with the initial state.
// For each state explored, the algorithm:

// Checks if it's the goal state (if yes, returns the solution)
// Marks the state as visited
// Generates all possible next states
// For each valid unvisited next state, it:

// Sets the parent reference to the current state
// Recursively calls DFS on this next state


// If a recursive call returns a solution, it propagates back up the recursion stack
// If no solution is found in this branch, it backtracks


// If the goal state is found, the solution path is reconstructed and printed.
// If no solution is found after exploring all possibilities, the program reports that no solution exists.

// Short State Space Tree
// Initial State: [Left: M=3, C=3, Boat=L]
// ├── [Left: M=2, C=2, Boat=R]
// │   ├── [Left: M=3, C=2, Boat=L] → Invalid (3>2, missionaries eat cannibals)
// │   └── [Left: M=2, C=3, Boat=L] → Invalid (2<3, cannibals eat missionaries)
// ├── [Left: M=3, C=1, Boat=R]
// │   ├── [Left: M=3, C=3, Boat=L] → Cycle detected
// │   └── [Left: M=3, C=2, Boat=L] → Invalid (3>2, missionaries eat cannibals)
// └── [Left: M=1, C=3, Boat=R] → Invalid (1<3, cannibals eat missionaries)
    
// ... (many more branches explored) ...

// Solution Path:
// [Left: M=3, C=3, Boat=L]
// [Left: M=2, C=2, Boat=R]
// [Left: M=3, C=2, Boat=L]
// [Left: M=3, C=0, Boat=R]
// [Left: M=3, C=1, Boat=L]
// [Left: M=1, C=1, Boat=R]
// [Left: M=2, C=2, Boat=L]
// [Left: M=0, C=2, Boat=R]
// [Left: M=0, C=3, Boat=L]
// [Left: M=0, C=1, Boat=R]
// [Left: M=0, C=2, Boat=L]
// [Left: M=0, C=0, Boat=R] (Goal State)
// Explanation of General Approach
// The solution uses a depth-first search algorithm to explore the state space systematically. The state space is represented as a tree where each node is a state (M,C,B) that represents the number of missionaries and cannibals on the left bank and the position of the boat.
// The DFS algorithm uses backtracking to explore different paths through the state space. It maintains a set of visited states to avoid cycles and prevent revisiting states. The algorithm recursively explores each possible move from the current state until either the goal state is found or all possibilities are exhausted.
// When generating successor states, the algorithm considers all valid moves: one or two missionaries crossing, one or two cannibals crossing, or one of each crossing. After generating these potential next states, it checks each one for validity before exploring it further.
// The key insight is using depth-first search, which allows the algorithm to explore deeply into the state space quickly while using minimal memory. The backtracking nature of DFS allows it to explore alternative paths if the current path doesn't lead to a solution.
