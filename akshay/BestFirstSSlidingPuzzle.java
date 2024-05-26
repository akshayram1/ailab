/* Informed strategies O(b^d).  f(n)=h(n) */
package akshay;
import java.util.*;

public class BestFirstSSlidingPuzzle {
    static void findZero(int[][] board, int[] pos) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                    return;
                }
            }
        }
    }

    static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void solve(int[][] initial, int[][] goal) {
        int[] dx = { 0, 0, -1, 1 };
        int[] dy = { 1, -1, 0, 0 };

        PriorityQueue<Map.Entry<int[][], Integer>> pq = new PriorityQueue<>(
                Comparator.comparingInt(entry -> entry.getValue()));

        Set<String> visited = new HashSet<>();
        pq.offer(new AbstractMap.SimpleEntry<>(initial, heuristic(initial, goal)));

        while (!pq.isEmpty()) {
            int[][] curr = pq.peek().getKey();
            int cost = pq.peek().getValue();
            pq.poll();

            String hash = Arrays.deepToString(curr);
            if (visited.contains(hash)) {
                continue;
            }

            visited.add(hash);

            printBoard(curr);

            if (Arrays.deepEquals(curr, goal)) {
                System.out.println("Goal State Reached");
                return;
            }

            int[] pos = new int[2];
            findZero(curr, pos);
            int x = pos[0];
            int y = pos[1];

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (newX >= 0 && newX < curr.length && newY >= 0 && newY < curr[0].length) {
                    int[][] newCurr = new int[curr.length][curr[0].length];
                    for (int r = 0; r < curr.length; r++) {
                        newCurr[r] = Arrays.copyOf(curr[r], curr[r].length);
                    }

                    int temp = newCurr[x][y];
                    newCurr[x][y] = newCurr[newX][newY];
                    newCurr[newX][newY] = temp;

                    int newCost = heuristic(newCurr, goal);
                    pq.offer(new AbstractMap.SimpleEntry<>(newCurr, newCost));
                }
            }
        }
    }

    static int heuristic(int[][] state, int[][] goal) {
        int distance = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] != goal[i][j]) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public static void main(String[] args) {
        int[][] initial = {
                { 2, 8, 3 },
                { 1, 6, 4 },
                { 7, 0, 5 } };

        int[][] goal = {
                { 1, 2, 3 },
                { 8, 0, 4 },
                { 7, 6, 5 } };

        solve(initial, goal);
    }
}
