import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Point {
    int x, y, n;

    Point(int x, int y, int n) {
        this.x = x;
        this.y = y;
        this.n = n;
    }

    int manhattan(Point other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    List<Point> generateCentre() {
        List<Point> centres = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            int newX = this.x + d[0];
            int newY = this.y + d[1];
            if (newX >= 0 && newX <= n && newY >= 0 && newY <= n) {
                centres.add(new Point(newX, newY, n));
            }
        }
        return centres;
    }

    @Override
    public String toString() {
        return "(x, y) = (" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}

public class hillclimbing 
 {
    static Set<Point> visited = new HashSet<>();

    public static void main(String[] args) {
        int n = 9;  // Grid dimension

        // Hardcoded coordinates of points
        int[][] coordinates = {
            {1, 1},
            {9, 4},
            {4, 6},
            {2, 5},
            {7, 7}
        };

        List<Point> pointsList = new ArrayList<>();
        for (int[] coordinate : coordinates) {
            pointsList.add(new Point(coordinate[0], coordinate[1], n));
        }

        Point initialCentre = new Point(0, 0, n);
        findCentre(pointsList, n, initialCentre);
    }

    static void findCentre(List<Point> pointsList, int n, Point centre) {
        visited.add(centre);
        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Current centre: " + centre);
        System.out.println("Sum of manhattan distances (centre) :" + sum(pointsList, centre));

        List<Point> newCentres = centre.generateCentre();
        newCentres.removeAll(visited);  // Remove already visited centres

        System.out.println("\nNew centres: ");
        for (Point pt : newCentres) {
            System.out.println(pt);
        }

        List<Integer> distances = new ArrayList<>();
        for (Point pt : newCentres) {
            distances.add(sum(pointsList, pt));
        }

        System.out.println("Sum of manhattan distances from new centres: " + distances);

        int currentSum = sum(pointsList, centre);
        if (!distances.isEmpty()) {
            int minDistance = distances.stream().min(Integer::compareTo).orElse(currentSum);
            if (minDistance < currentSum) {
                Point newCentre = newCentres.get(distances.indexOf(minDistance));
                findCentre(pointsList, n, newCentre);
            } else {
                System.out.println("Finalized Minimal centre: " + centre);
                System.out.println("Cost: " + currentSum);
            }
        } else {
            System.out.println("Finalized Minimal centre: " + centre);
            System.out.println("Cost: " + currentSum);
        }
    }

    static int sum(List<Point> pointsList, Point centre) {
        return pointsList.stream().mapToInt(pt -> centre.manhattan(pt)).sum();
    }
}