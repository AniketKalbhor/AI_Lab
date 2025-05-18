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

        if (missionariesLeft < 0 || cannibalsLeft < 0 || missionariesLeft > 3 || cannibalsLeft > 3)
            return false;

        if ((missionariesLeft > 0 && missionariesLeft < cannibalsLeft) ||
                (mRight > 0 && mRight < cRight))
            return false;

        return true;
    }

    public boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0 && boat == 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) return false;
        State s = (State) obj;
        return missionariesLeft == s.missionariesLeft &&
                cannibalsLeft == s.cannibalsLeft &&
                boat == s.boat;
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
                if (result != null) return result;
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
