import java.util.*;

class State {
    int monkeyPosition;
    int chairPosition;
    int bananaPosition;

    State(int monkeyPosition, int chairPosition, int bananaPosition) {
        this.monkeyPosition = monkeyPosition;
        this.chairPosition = chairPosition;
        this.bananaPosition = bananaPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        State state = (State) obj;
        return monkeyPosition == state.monkeyPosition &&
                chairPosition == state.chairPosition &&
                bananaPosition == state.bananaPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(monkeyPosition, chairPosition, bananaPosition);
    }
}

public class MonkeyBananaProblem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter initial position for Monkey (0 or 1 or 2):");
        int initialMonkeyPosition = scanner.nextInt();

        System.out.println("Enter initial position for Chair (0 or 1):");
        int initialChairPosition = scanner.nextInt();

        System.out.println("Enter initial position for Banana (0 or 1 or 2):");
        int initialBananaPosition = scanner.nextInt();

        State initialState = new State(initialMonkeyPosition, initialChairPosition, initialBananaPosition);

        System.out.println("Choose algorithm: (1) BFS, (2) DFS");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("BFS Solution:");
                bfs(initialState);
                break;
            case 2:
                System.out.println("DFS Solution:");
                dfs(initialState, new HashSet<>());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void bfs(State initialState) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (isGoalState(currentState)) {
                printSolution(currentState);
                return;
            }

            List<State> nextStates = generateNextStates(currentState);
            for (State nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static void dfs(State currentState, Set<State> visited) {
        if (isGoalState(currentState)) {
            printSolution(currentState);
            return;
        }

        visited.add(currentState);
        List<State> nextStates = generateNextStates(currentState);

        for (State nextState : nextStates) {
            if (!visited.contains(nextState)) {
                dfs(nextState, visited);
            }
        }
    }

    public static boolean isGoalState(State state) {
        return state.monkeyPosition == state.bananaPosition;
    }

    public static List<State> generateNextStates(State currentState) {
        List<State> nextStates = new ArrayList<>();

        // Monkey moves
        if (currentState.monkeyPosition < 2) {
            nextStates.add(new State(currentState.monkeyPosition + 1, currentState.chairPosition,
                    currentState.bananaPosition));
        }

        if (currentState.monkeyPosition > 0) {
            nextStates.add(new State(currentState.monkeyPosition - 1, currentState.chairPosition,
                    currentState.bananaPosition));
        }

        // Chair moves
        nextStates.add(
                new State(currentState.monkeyPosition, 1 - currentState.chairPosition, currentState.bananaPosition));

        // Monkey climbs the chair
        if (currentState.monkeyPosition == currentState.chairPosition
                && currentState.monkeyPosition == currentState.bananaPosition) {
            nextStates
                    .add(new State(currentState.chairPosition, currentState.chairPosition, currentState.chairPosition));
        }

        return nextStates;
    }

    public static void printSolution(State state) {
        System.out.println("Monkey reached the banana successfully!");
        System.out.println("Monkey Position: " + state.monkeyPosition);
        System.out.println("Chair Position: " + state.chairPosition);
        System.out.println("Banana Position: " + state.bananaPosition);
    }
}
