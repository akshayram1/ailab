import java.util.*;

public class IDSWaterJug {

    public static int total = 0;

    static class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static Stack<Node> result = new Stack<Node>();
    static boolean[][] m;
    static int found = 0;

    static boolean solveDLS(int curj1, int curj2, int jug1, int jug2, int tx, int ty, int depth, int limit) {

        // Got final case
        if (curj1 == tx && curj2 == ty) {
            result.push(new Node(curj1, curj2));
            return true;
        }

        total++;

        // If the depth limit is reached
        if (depth > limit) {
            return false;
        }

        // If the node is already visited
        if (m[curj1][curj2]) {
            return false;
        }
        // Marks the node as a visited
        m[curj1][curj2] = true;

        // Fill the 1st jug completely
        if (curj1 < jug1 && solveDLS(jug1, curj2, jug1, jug2, tx, ty, depth + 1, limit)) {
            result.push(new Node(curj1, curj2));
            return true;
        }

        // Fill the 2nd jug completely
        if (curj2 < jug2 && solveDLS(curj1, jug2, jug1, jug2, tx, ty, depth + 1, limit)) {
            result.push(new Node(curj1, curj2));
            return true;
        }

        // Empty the first jug
        if (curj1 > 0 && solveDLS(0, curj2, jug1, jug2, tx, ty, depth + 1, limit)) {
            result.push(new Node(curj1, curj2));
            return true;
        }

        // Empty the second jug
        if (curj2 > 0 && solveDLS(curj1, 0, jug1, jug2, tx, ty, depth + 1, limit)) {
            result.push(new Node(curj1, curj2));
            return true;
        }

        if (curj1 > 0 && curj2 < jug2) {
            boolean temp;
            if (curj2 + curj1 <= jug2) {
                // Empty first jug into second jug
                temp = solveDLS(0, curj1 + curj2, jug1, jug2, tx, ty, depth + 1, limit);
            } else {
                // Pour the water from 1st jug to 2nd jug
                temp = solveDLS(curj1 - (jug2 - curj2), jug2, jug1, jug2, tx, ty, depth + 1, limit);
            }
            if (temp) {
                result.push(new Node(curj1, curj2));
                return true;
            }
        }

        if (curj2 > 0 && curj1 < jug1) {
            boolean temp;
            if (curj2 + curj1 <= jug1) {
                // Empty the second jug into first jug
                temp = solveDLS(curj1 + curj2, 0, jug1, jug2, tx, ty, depth + 1, limit);
            } else {
                // Pour the water from second jug to first jug
                temp = solveDLS(jug1, curj2 - (jug1 - curj2), jug1, jug2, tx, ty, depth + 1, limit);
            }
            if (temp) {
                result.push(new Node(curj1, curj2));
                return true;
            }
        }

        m[curj1][curj2] = false;
        return false;
    }

    // Iterative Deepening Search
    static boolean iterativeDeepeningSearch(int jug1, int jug2, int tx, int ty) {
        int depth = 0;
        while (true) {
            // Reset visited array for each depth level
            m = new boolean[5][5];
            if (solveDLS(0, 0, jug1, jug2, tx, ty, 0, depth)) {
                return true;
            }
            depth++;
        }
    }

    // function to print the stack in reverse order
    public static void printReverseStack(Stack<Node> stack) {
        // base case
        if (stack.isEmpty())
            return;

        // create temporary stack
        Stack<Node> temporaryStack = new Stack<>();

        // copy all the elements from given stack to temporary stack
        while (stack.size() > 0) {
            temporaryStack.push(stack.pop());
        }

        for (Node n : temporaryStack) {
            System.out.println("(" + n.x + "," + n.y + ")");
        }
    }

    public static void main(String[] args) {
        IDSWaterJug obj = new IDSWaterJug();
        boolean res = iterativeDeepeningSearch(4, 3, 2, 0);
        if (res) {
            printReverseStack(result);
        } else {
            System.out.println("Solution not found.");
        }
        System.out.println("Total Nodes: " + total);
    }
}
