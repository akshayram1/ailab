import java.util.*;

class NQueensAlgo {
    static final int N = 4;

    static class State {
        int[] board;
        int level;
        List<State> successors;  
        boolean isSolved;       
                                 
        State(int[] board, int level) {
            this.board = board.clone();
            this.level = level;
            this.successors = new ArrayList<>();
            this.isSolved = false;
        }

        List<State> generateSuccessors() {
            List<State> successors = new ArrayList<>();
            if (level < N) {
                for (int col = 0; col < N; col++) {
                    if (isSafe(board, level, col)) {
                        board[level] = col;
                        successors.add(new State(board, level + 1));
                    }
                }
            }
            return successors;
        }

        boolean isGoal() {
            return level == N;
        }

        boolean isSafe(int[] board, int row, int col) {
            for (int i = 0; i < row; i++) {
                if (board[i] == col || Math.abs(board[i] - col) == row - i) {
                    return false;
                }
            }
            return true;
        }

        void printBoard() {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i] == j) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        NQueensAlgo solver = new NQueensAlgo();
        int[] initialBoard = new int[N];
        Arrays.fill(initialBoard, -1);
        State initial = new State(initialBoard, 0);
        solver.aoStar(initial);
    }

    void aoStar(State initialState) {
        PriorityQueue<State> open = new PriorityQueue<>(Comparator.comparingInt(this::heuristic));
        Set<State> closed = new HashSet<>();
        open.add(initialState);

        while (!open.isEmpty()) {
            State current = open.poll();
            if (current.isGoal()) {
                System.out.println("Solution found:");
                current.printBoard();
                return;
            }

            closed.add(current);
            List<State> successors = current.generateSuccessors();
            current.successors = successors;  // Link successors

            for (State successor : successors) {
                if (!closed.contains(successor) && !open.contains(successor)) {
                    open.add(successor);
                    System.out.println("Intermediate step:");
                    successor.printBoard();
                }
            }

            // Handle "AND" conditions
            if (current.successors.stream().allMatch(s -> s.isSolved)) {
                current.isSolved = true;
            }
        }

        System.out.println("No solution found.");
    }

    int heuristic(State state) {
        return N - state.level;
    }
}
