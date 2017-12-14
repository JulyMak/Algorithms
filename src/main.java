import java.awt.*;

public class main {
    public static void main(String[] args) {
        Maze maze = new Maze();

        maze = maze.loadMazeFromFile("maze3.txt");
        if (maze == null) return;
        FindMaxWay res = new FindMaxWay(maze);
        res.Run(new Point(maze.start.y, maze.start.x), new Point(maze.finish.x, maze.finish.y));

        try {
            for (Point p : res.BestWay) {
                System.out.print(String.format("(%d %d) ", p.y, p.x));
            }
        } catch (NullPointerException e) {
            System.out.println("bad maze: found no paths");
        }
    }
}