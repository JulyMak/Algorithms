import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Point;

public class GUI extends Application {

    final int cellSize = 100;
    final int gapSize = 5;
    final int offset = gapSize;
    final int pathSize = 50;
    final int wallSize = (cellSize + 2 * gapSize - pathSize) / 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Maze maze = new Maze();
        maze = maze.loadMazeFromFile("maze5.txt");
        if (maze == null) {
            System.exit(0);
        }
        FindMaxWay res = new FindMaxWay(maze);
        res.Run(new Point(maze.start.y, maze.start.x), new Point(maze.finish.x, maze.finish.y));

        if (res.BestWay == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("found no paths");
            alert.showAndWait();
            System.exit(0);
        }

        Group root = new Group();
        Scene scene = new Scene(root, maze.DimX * (cellSize + gapSize) + gapSize,
                maze.DimY * (cellSize + gapSize) + gapSize, Color.WHITE);

        Rectangle[][] rectangles = new Rectangle[maze.DimY][];
        for (int i = 0; i < maze.DimY; i++) {
            rectangles[i] = new Rectangle[maze.DimX];
            for (int j = 0; j < maze.DimX; j++) {
                rectangles[i][j] = new Rectangle();
            }
        }

        for (int i = 0; i < res.maze.DimY; i++) {
            for (int j = 0; j < res.maze.DimX; j++) {

                rectangles[i][j].setX(offset + j * (cellSize + gapSize));
                rectangles[i][j].setY(offset + i * (cellSize + gapSize));
                rectangles[i][j].setWidth(cellSize);
                rectangles[i][j].setHeight(cellSize);

                rectangles[i][j].setFill(Color.CORAL);

                Text text = new Text(res.maze.cells[i][j].value);
                text.setX(offset + j * (cellSize + gapSize) + cellSize / 2 - 5);
                text.setY(offset + i * (cellSize + gapSize) + cellSize / 2 + 5);
                text.setFill(Color.BLACK);
                text.setFont(Font.font("Calibri", 20));

                root.getChildren().addAll(rectangles[i][j], text);

                Rectangle wallUp = new Rectangle();
                wallUp.setX(offset + j * cellSize + (j - 1) * gapSize);
                wallUp.setY(offset + i * cellSize + (i - 1) * gapSize);
                wallUp.setHeight(gapSize);
                wallUp.setWidth(cellSize + 2 * gapSize);
                wallUp.setFill(Color.BLACK);
                root.getChildren().add(wallUp);
                if (res.maze.cells[i][j].paths.up) {
                    Rectangle p = new Rectangle();
                    p.setX(offset + j * cellSize + (j - 1) * gapSize + wallSize);
                    p.setY(offset + i * cellSize + (i - 1) * gapSize);
                    p.setWidth(pathSize);
                    p.setHeight(gapSize);
                    p.setFill(Color.CORAL);
                    root.getChildren().add(p);
                }

                Rectangle wallDown = new Rectangle();
                wallDown.setX(offset + j * cellSize + (j - 1) * gapSize);
                wallDown.setY(offset + (i + 1) * cellSize + i * gapSize);
                wallDown.setHeight(gapSize);
                wallDown.setWidth(cellSize + 2 * gapSize);
                wallDown.setFill(Color.BLACK);
                root.getChildren().add(wallDown);
                if (res.maze.cells[i][j].paths.down) {
                    Rectangle p = new Rectangle();
                    p.setX(offset + j * cellSize + (j - 1) * gapSize + wallSize);
                    p.setY(offset + (i + 1) * cellSize + i * gapSize);
                    p.setWidth(pathSize);
                    p.setHeight(gapSize);
                    p.setFill(Color.CORAL);
                    root.getChildren().add(p);
                }

                Rectangle wallRight = new Rectangle();
                wallRight.setX(offset + (j + 1) * cellSize + j * gapSize);
                wallRight.setY(offset + i * cellSize + (i - 1) * gapSize);
                wallRight.setHeight(cellSize + 2 * gapSize);
                wallRight.setWidth(gapSize);
                wallRight.setFill(Color.BLACK);
                root.getChildren().add(wallRight);
                if (res.maze.cells[i][j].paths.right) {
                    Rectangle p = new Rectangle();
                    p.setX(offset + (j + 1) * cellSize + j * gapSize);
                    p.setY(offset + i * cellSize + (i - 1) * gapSize + wallSize);
                    p.setWidth(gapSize);
                    p.setHeight(pathSize);
                    p.setFill(Color.CORAL);
                    root.getChildren().add(p);
                }

                Rectangle wallLeft = new Rectangle();
                wallLeft.setX(offset + j * cellSize + (j - 1) * gapSize);
                wallLeft.setY(offset + i * cellSize + (i - 1) * gapSize);
                wallLeft.setHeight(cellSize + 2 * gapSize);
                wallLeft.setWidth(gapSize);
                wallLeft.setFill(Color.BLACK);
                root.getChildren().add(wallLeft);
                if (res.maze.cells[i][j].paths.left) {
                    Rectangle p = new Rectangle();
                    p.setX(offset + j * cellSize + (j - 1) * gapSize);
                    p.setY(offset + i * cellSize + (i - 1) * gapSize + wallSize);
                    p.setWidth(gapSize);
                    p.setHeight(pathSize);
                    p.setFill(Color.CORAL);
                    root.getChildren().add(p);
                }
            }
        }

        primaryStage.setResizable(false);
        primaryStage.setTitle("Mazesolver");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        new Thread(() -> {
            for (Point p : res.BestWay) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> rectangles[p.y][p.x].setFill(Color.YELLOW));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Success");
                String s = "Max sum is " + res.sum;
                alert.setContentText(s);
                alert.show();
            });
        }).start();
    }
}
