import java.util.*;

class StateDFS {
    int a, b;

    public StateDFS(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
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