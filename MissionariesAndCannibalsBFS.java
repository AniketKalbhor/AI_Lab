//import java.util.*;
//
//class State {
//    int missionariesLeft, cannibalsLeft, boat; // boat: 0 = left, 1 = right
//    State parent;
//
//    public State(int m, int c, int b) {
//        this.missionariesLeft = m;
//        this.cannibalsLeft = c;
//        this.boat = b;
//    }
//
//    public boolean isValid() {
//        int mRight = 3 - missionariesLeft;
//        int cRight = 3 - cannibalsLeft;
//
//        // Check bounds
//        if (missionariesLeft < 0 || cannibalsLeft < 0 || missionariesLeft > 3 || cannibalsLeft > 3)
//            return false;
//
//        // Missionaries should not be outnumbered on either bank
//        if ((missionariesLeft > 0 && missionariesLeft < cannibalsLeft) ||
//                (mRight > 0 && mRight < cRight))
//            return false;
//
//        return true;
//    }
//
//    public boolean isGoal() {
//        return missionariesLeft == 0 && cannibalsLeft == 0 && boat == 1;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof State)) return false;
//        State s = (State) obj;
//        return missionariesLeft == s.missionariesLeft &&
//                cannibalsLeft == s.cannibalsLeft &&
//                boat == s.boat;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(missionariesLeft, cannibalsLeft, boat);
//    }
//
//    @Override
//    public String toString() {
//        return "[Left -> M: " + missionariesLeft + ", C: " + cannibalsLeft + ", Boat: " + (boat == 0 ? "L" : "R") + "]";
//    }
//}
//
//public class MissionariesAndCannibalsBFS {
//    public static void main(String[] args) {
//        solve();
//    }
//
//    public static void solve() {
//        State initialState = new State(3, 3, 0);
//        Queue<State> queue = new LinkedList<>();
//        Set<State> visited = new HashSet<>();
//
//        queue.offer(initialState);
//        visited.add(initialState);
//
//        while (!queue.isEmpty()) {
//            State current = queue.poll();
//
//            if (current.isGoal()) {
//                printPath(current);
//                return;
//            }
//
//            for (State next : generateNextStates(current)) {
//                if (next.isValid() && !visited.contains(next)) {
//                    next.parent = current;
//                    visited.add(next);
//                    queue.offer(next);
//                }
//            }
//        }
//
//        System.out.println("No solution found.");
//    }
//
//    public static List<State> generateNextStates(State s) {
//        List<State> successors = new ArrayList<>();
//
//        // Possible moves: (m, c)
//        int[][] moves = {
//                {1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}
//        };
//
//        for (int[] move : moves) {
//            int m = move[0];
//            int c = move[1];
//
//            State newState;
//            if (s.boat == 0) { // Boat on the left side
//                newState = new State(s.missionariesLeft - m, s.cannibalsLeft - c, 1);
//            } else { // Boat on the right side
//                newState = new State(s.missionariesLeft + m, s.cannibalsLeft + c, 0);
//            }
//
//            successors.add(newState);
//        }
//
//        return successors;
//    }
//
//    public static void printPath(State goal) {
//        List<State> path = new ArrayList<>();
//        State current = goal;
//
//        while (current != null) {
//            path.add(current);
//            current = current.parent;
//        }
//
//        Collections.reverse(path);
//        System.out.println("Solution found! Steps to achieve the goal:");
//        for (State s : path) {
//            System.out.println(s);
//        }
//    }
//}
