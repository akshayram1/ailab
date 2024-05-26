import java.util.*;

class PuzzleState {
    int[][] board;
    int x, y; // Coordinates of the blank space (0)
    PuzzleState parent; // To track the path to the solution

    public PuzzleState(int[][] board, int x, int y, PuzzleState parent) {
        this.board = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = board[i].clone();
        }
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    // Check if this state is the goal state
    public boolean isGoal() {
        int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        return Arrays.deepEquals(this.board, goal);
    }

    // Generate successors of the current state
    public List<PuzzleState> generateSuccessors() {
        List<PuzzleState> successors = new ArrayList<>();
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (isInBounds(newX, newY)) {
                int[][] newBoard = copyBoard(this.board);
                swap(newBoard, x, y, newX, newY);
                successors.add(new PuzzleState(newBoard, newX, newY, this));
            }
        }

        return successors;
    }

    // Check if coordinates are within bounds of the board
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board.length;
    }

    // Swap the values in the board
    private void swap(int[][] board, int x1, int y1, int x2, int y2) {
        int temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    // Create a copy of the board
    private int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            newBoard[i] = board[i].clone();
        }
        return newBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleState that = (PuzzleState) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}

public class EightPuzzleBFS {
    public static void main(String[] args) {
        int[][] initialBoard = {
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6}
        };

        PuzzleState initialState = new PuzzleState(initialBoard, 1, 1, null);
        if (bfs(initialState)) {
            System.out.println("Solution found.");
        } else {
            System.out.println("No solution exists.");
        }
    }

    public static boolean bfs(PuzzleState initialState) {
        Queue<PuzzleState> frontier = new LinkedList<>();
        Set<PuzzleState> explored = new HashSet<>();
        int nodesVisited = 0;
        int branches = 0;

        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            PuzzleState current = frontier.poll();
            explored.add(current);
            nodesVisited++;

            if (current.isGoal()) {
                System.out.println("Total nodes visited: " + nodesVisited);
                System.out.println("Total cost (depth of solution): " + getDepth(current));
                System.out.println("Number of branches: " + branches);
                printSolutionPath(current);
                return true;
            }

            List<PuzzleState> successors = current.generateSuccessors();
            branches += successors.size();
            for (PuzzleState successor : successors) {
                if (!explored.contains(successor) && !frontier.contains(successor)) {
                    frontier.add(successor);
                }
            }

            System.out.println("Frontier size: " + frontier.size());
        }

        return false;
    }

    // Get the depth of the solution by counting the number of parents
    private static int getDepth(PuzzleState state) {
        int depth = 0;
        while (state.parent != null) {
            depth++;
            state = state.parent;
        }
        return depth;
    }

    // Print the path from the initial state to the goal state
    private static void printSolutionPath(PuzzleState state) {
        List<PuzzleState> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = state.parent;
        }
        Collections.reverse(path);
        for (PuzzleState step : path) {
            printBoard(step.board);
            System.out.println();
        }
    }

    // Print the board
    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}