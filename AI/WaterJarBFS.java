//import java.util.*;
//
//class State {
//    int a, b;
//
//    public State(int a, int b) {
//        this.a = a;
//        this.b = b;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        State state = (State) obj;
//        return a == state.a && b == state.b;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(a, b);
//    }
//
//    @Override
//    public String toString() {
//        return "(" + a + ", " + b + ")";
//    }
//}
//
//public class WaterJarBFS {
//    static final int JAR_A_CAPACITY = 4;
//    static final int JAR_B_CAPACITY = 3;
//    static final int TARGET = 2;
//
//    public static void main(String[] args) {
//        solveWaterJarProblem();
//    }
//
//    public static void solveWaterJarProblem() {
//        Queue<State> queue = new LinkedList<>();
//        Set<State> visited = new HashSet<>();
//        Map<State, State> parent = new HashMap<>(); // To trace the path
//
//        // Initial state: both jars are empty
//        State initialState = new State(0, 0);
//        queue.offer(initialState);
//        visited.add(initialState);
//
//        while (!queue.isEmpty()) {
//            State current = queue.poll();
//
//            // Check if we have reached the goal
//            if (current.a == TARGET) {
//                printSolution(current, parent);
//                return;
//            }
//
//            // Generate all possible next states
//            List<State> nextStates = generateNextStates(current);
//
//            for (State next : nextStates) {
//                if (!visited.contains(next)) {
//                    queue.offer(next);
//                    visited.add(next);
//                    parent.put(next, current); // Keep track of the state that led here
//                }
//            }
//        }
//
//        System.out.println("No solution found.");
//    }
//
//    public static List<State> generateNextStates(State current) {
//        int a = current.a, b = current.b;
//        List<State> nextStates = new ArrayList<>();
//
//        // Fill Jar A
//        nextStates.add(new State(JAR_A_CAPACITY, b));
//
//        // Fill Jar B
//        nextStates.add(new State(a, JAR_B_CAPACITY));
//
//        // Empty Jar A
//        nextStates.add(new State(0, b));
//
//        // Empty Jar B
//        nextStates.add(new State(a, 0));
//
//        // Pour from Jar A to Jar B
//        int pourToB = Math.min(a, JAR_B_CAPACITY - b);
//        nextStates.add(new State(a - pourToB, b + pourToB));
//
//        // Pour from Jar B to Jar A
//        int pourToA = Math.min(b, JAR_A_CAPACITY - a);
//        nextStates.add(new State(a + pourToA, b - pourToA));
//
//        return nextStates;
//    }
//
//    public static void printSolution(State goal, Map<State, State> parent) {
//        List<State> path = new ArrayList<>();
//        State current = goal;
//
//        while (current != null) {
//            path.add(current);
//            current = parent.get(current);
//        }
//
//        Collections.reverse(path);
//        System.out.println("Solution found! Steps to achieve the goal:");
//        for (State state : path) {
//            System.out.println(state);
//        }
//    }
//}
