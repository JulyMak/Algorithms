import org.junit.Test;

import java.awt.Point;

import static org.junit.Assert.*;

public class FindMaxWayTest {
    @Test
    public void run() throws Exception {
        Maze maze = new Maze();
        maze = maze.loadMazeFromFile("maze1.txt");
        FindMaxWay res = new FindMaxWay(maze);
        res.Run(new Point(maze.start.y, maze.start.x), new Point(maze.finish.x, maze.finish.y));
        assertEquals(res.sum, 32);
    }
    @Test
    public void run1() throws Exception {
        Maze maze = new Maze();
        maze = maze.loadMazeFromFile("maze2.txt");
        FindMaxWay res = new FindMaxWay(maze);
        res.Run(new Point(maze.start.y, maze.start.x), new Point(maze.finish.x, maze.finish.y));
        assertEquals(res.sum, 9);
    }
    @Test
    public void run2() throws Exception {
        Maze maze = new Maze();
        maze = maze.loadMazeFromFile("maze3.txt");
        FindMaxWay res = new FindMaxWay(maze);
        res.Run(new Point(maze.start.y, maze.start.x), new Point(maze.finish.x, maze.finish.y));
        assertEquals(res.sum, 30);
    }
}