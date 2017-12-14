import java.awt.*;
import java.util.ArrayList;

public class FindMaxWay {
    Point finish;
    int sum;
    static Maze maze;
    public ArrayList<Point> BestWay;

    public ArrayList<ArrayList<Point>> Ways = new ArrayList<>();

    public FindMaxWay(Maze maze) {
        this.maze = maze;
    }

    public void Run(Point start, Point finish) {
        this.finish = finish;
        checkWay(start.y, start.x, new ArrayList<>());
        FindBestWay();
    }

    private void checkWay(int x, int y, ArrayList<Point> way) {

        ArrayList<Point> Way = new ArrayList<>(way);
        Way.add(new Point(x, y));

        if (maze.cells[y][x].paths.right && !isPassed(new Point(x + 1, y), Way)) {
            checkWay(x + 1, y, Way);
        }
        if (maze.cells[y][x].paths.left && !isPassed(new Point(x - 1, y), Way)) {
            checkWay(x - 1, y, Way);
        }
        if (maze.cells[y][x].paths.down && !isPassed(new Point(x, y + 1), Way)) {
            checkWay(x, y + 1, Way);
        }
        if (maze.cells[y][x].paths.up && !isPassed(new Point(x, y - 1), Way)) {
            checkWay(x, y - 1, Way);
        }
        if (x == finish.x && y == finish.y) {
            Ways.add(Way);
        }
    }

    private boolean isPassed(Point a, ArrayList<Point> Way) {
        for (Point i : Way) {
            if (i.x == a.x && i.y == a.y)
                return true;
        }
        return false;
    }

    private void FindBestWay() {
        ArrayList<Point> bestWay = null;
        int maxSum = 0;
        for (ArrayList<Point> Way : Ways) {
            int tmp = countSum(Way);
            if (tmp >= maxSum) {
                bestWay = Way;
                maxSum = tmp;
            }
        }
        BestWay = bestWay;
        sum = maxSum;
    }

    public int countSum(ArrayList<Point> r) {
        int res = 0;
        for (Point p : r) {
            if (!maze.cells[p.y][p.x].value.equals("s") && !maze.cells[p.y][p.x].value.equals("f")) {
                res += Integer.parseInt(maze.cells[p.y][p.x].value);
            }
        }
        return res;
    }
}
