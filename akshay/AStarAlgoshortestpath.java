//AStarAlgoshortestpath

package akshay;

import java.util.*;

class Node implements Comparable<Node> {
    int id;
    int cost;
    int heuristic;
    Node parent;

    public Node(int id, int cost, int heuristic, Node parent) {
        this.id = id;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(cost + heuristic, other.cost + other.heuristic);
    }
}

public class AStarAlgoshortestpath {
    public static List<Integer> findShortestPath(int[][] graph, int start, int goal, int[] heuristic) {
        int numNodes = graph.length;
        boolean[] visited = new boolean[numNodes];
        int[] costs = new int[numNodes];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[start] = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start, 0, heuristic[start], null));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentNode = current.id;

            if (currentNode == goal) {
                List<Integer> path = new ArrayList<>();
                while (current != null) {
                    path.add(current.id);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            if (visited[currentNode]) {
                continue;
            }

            visited[currentNode] = true;

            for (int neighbor = 0; neighbor < numNodes; neighbor++) {
                if (graph[currentNode][neighbor] != 0 && !visited[neighbor]) {
                    int newCost = costs[currentNode] + graph[currentNode][neighbor];
                    if (newCost < costs[neighbor]) {
                        costs[neighbor] = newCost;
                        Node neighborNode = new Node(neighbor, newCost, heuristic[neighbor], current);
                        queue.add(neighborNode);
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        int[][] graph = {
                { 0, 2, 0, 1, 0 },
                { 2, 0, 3, 0, 0 },
                { 0, 3, 0, 0, 4 },
                { 1, 0, 0, 0, 2 },
                { 0, 0, 4, 2, 0 }
        };
        int start = 0;
        int goal = 4;
        int[] heuristic = { 6, 3, 2, 4, 0 };

        List<Integer> path = findShortestPath(graph, start, goal, heuristic);

        if (path != null) {
            System.out.println("Shortest path: " + path);
        } else {
            System.out.println("No path found");
        }
    }
}
