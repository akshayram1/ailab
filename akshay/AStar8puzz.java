/* Informed strategies O(b^d). f(n)=g(n)+h(n) */

package akshay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AStar8puzz {
    static ArrayList<State> output = new ArrayList<>();

    static class StateComparator implements Comparator<State> {
        public int compare(State a, State b) {
            return Integer.compare(a.f, b.f);
        }
    }

    static class State {
        public int[][] board = new int[3][3];
        public int g, f, h;
        public State came_from;

        public State() {
            g = 0;
            f = 0;
            h = 0;
            came_from = null;
        }

        public static int heuristic(State from, State to) {
            int ret = 0;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (from.board[i][j] != to.board[i][j])
                        ret++;
            return ret;
        }

        public boolean equals(State a) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (this.board[i][j] != a.board[i][j])
                        return false;
            return true;
        }

        public void print() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    System.out.print(board[i][j] + " ");
                System.out.println();
            }
            System.out.println("g = " + g + " | h = " + h + " | f = " + f);
        }
    }

    static boolean isinset(State a, ArrayList<State> b) {
        for (int i = 0; i < b.size(); i++)
            if (a.equals(b.get(i)))
                return true;
        return false;
    }

    static void addNeighbor(State current, State goal, int newi, int newj, int posi, int posj,
            ArrayList<State> openset, ArrayList<State> closedset) {
        State newstate = new State();
        for (int i = 0; i < 3; i++)
            newstate.board[i] = current.board[i].clone();

        // Swap operation replacement
        int temp = newstate.board[newi][newj];
        newstate.board[newi][newj] = newstate.board[posi][posj];
        newstate.board[posi][posj] = temp;

        if (!isinset(newstate, closedset) && !isinset(newstate, openset)) {
            newstate.g = current.g + 1;
            newstate.h = State.heuristic(newstate, goal);
            newstate.f = newstate.g + newstate.h;
            newstate.came_from = current;
            openset.add(newstate);
        }
    }

    static void neighbors(State current, State goal, ArrayList<State> openset, ArrayList<State> closedset) {
        int i = 0, j = 0, posi = 0, posj = 0;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                if (current.board[i][j] == 0) {
                    posi = i;
                    posj = j;
                    break;
                }

        i = posi;
        j = posj;
        if (i - 1 >= 0)
            addNeighbor(current, goal, i - 1, j, posi, posj, openset, closedset);
        if (i + 1 < 3)
            addNeighbor(current, goal, i + 1, j, posi, posj, openset, closedset);
        if (j + 1 < 3)
            addNeighbor(current, goal, i, j + 1, posi, posj, openset, closedset);
        if (j - 1 >= 0)
            addNeighbor(current, goal, i, j - 1, posi, posj, openset, closedset);
    }

    static boolean reconstruct_path(State current, ArrayList<State> came_from) {
        State temp = current;
        while (temp != null) {
            came_from.add(temp);
            temp = temp.came_from;
        }
        return true;
    }

    static boolean astar(State start, State goal) {
        ArrayList<State> openset = new ArrayList<>();
        ArrayList<State> closedset = new ArrayList<>();
        State current = new State();
        start.g = 0;
        start.h = State.heuristic(start, goal);
        start.f = start.g + start.h;
        openset.add(start);
        while (!openset.isEmpty()) {
            openset.sort(new StateComparator());
            current = openset.get(0);
            if (current.equals(goal))
                return reconstruct_path(current, output);
            openset.remove(0);
            closedset.add(current);
            neighbors(current, goal, openset, closedset);
        }
        return false;
    }

    public static void main(String[] args) {
        State start = new State();
        State goal = new State();

        // Initial state
        start.board[0][0] = 2;
        start.board[0][1] = 8;
        start.board[0][2] = 3;
        start.board[1][0] = 1;
        start.board[1][1] = 6;
        start.board[1][2] = 4;
        start.board[2][0] = 7;
        start.board[2][1] = 0;
        start.board[2][2] = 5;

        // Goal state
        goal.board[0][0] = 1;
        goal.board[0][1] = 2;
        goal.board[0][2] = 3;
        goal.board[1][0] = 8;
        goal.board[1][1] = 0;
        goal.board[1][2] = 4;
        goal.board[2][0] = 7;
        goal.board[2][1] = 6;
        goal.board[2][2] = 5;

        if (astar(start, goal)) {
            for (int i = output.size() - 1; i >= 0; i--)
                output.get(i).print();
            System.out.println("SUCCESS!! GOAL STATE REACHED.");
        } else {
            System.out.println("FAIL");
        }
    }
}
