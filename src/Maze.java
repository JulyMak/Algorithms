import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

class Maze {
    int DimX;
    int DimY;

    static class Start {
        int x, y;

        Start() {
            x = -1;
            y = -1;
        }
    }

    static class Finish {
        int x, y;

        Finish() {
            x = -1;
            y = -1;
        }
    }

    Start start = new Start();
    Finish finish = new Finish();

    static class Cell {
        String value;

        static class Paths {
            boolean right, up, left, down;
        }

        Paths paths;
    }

    Cell[][] cells;

    Maze loadMazeFromFile(String filename) {

        Maze maze = new Maze();

        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            String sCurrentLine;
            sCurrentLine = br.readLine();

            maze.DimY = Integer.parseInt(sCurrentLine.trim().split(" +")[0]); //  size of maze
            maze.DimX = Integer.parseInt(sCurrentLine.trim().split(" +")[1]);
            maze.cells = new Cell[maze.DimY][];
            for (int i = 0; i < maze.DimY; i++) {
                maze.cells[i] = new Cell[maze.DimX]; // fill cells
                for (int j = 0; j < maze.DimX; j++) {
                    maze.cells[i][j] = new Cell();
                    maze.cells[i][j].paths = new Cell.Paths();
                }
            }
            maze.start = new Start();
            maze.finish = new Finish();

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.equals("")) {
                    continue;   // do nothing if line is empty
                }
                String[] line = sCurrentLine.trim().split(" +");
                if (line.length != 4) { // if we don't have enough or more than need data
                    //System.out.println("loadMazeFromFile(String filename) error: invalid string " + sCurrentLine +
                    //      " format is: <row col val ?ruld>");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("invalid string");
                    alert.showAndWait();
                    return null;
                }
                int row = Integer.parseInt(line[0]);
                int col = Integer.parseInt(line[1]);


                if (line[2].equals("s")) { // start
                    if (maze.start.x == -1 && maze.start.y == -1) {
                        maze.start.y = row;
                        maze.start.x = col;
                    } else {   // if coordinates were changed we have more than 1 start
                        // System.out.println("bad maze: found more than 1 start cell");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("found more than 1 start cell");
                        alert.showAndWait();
                        return null;
                    }
                }
                if (line[2].equals("f")) { // finish
                    if (maze.finish.x == -1 && maze.finish.y == -1) {
                        maze.finish.y = row;
                        maze.finish.x = col;
                    } else {  // if coordinates were changed we have more than 1 finish
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("found more than 1 finish cell");
                        alert.showAndWait();
                        //System.out.println("bad maze: found more than 1 finish cell");
                        return null;
                    }
                }

                maze.cells[row][col].value = line[2];

                char[] paths = line[3].toCharArray();

                for (int i = 0; i < paths.length; i++) { // make pathes
                    switch (paths[i]) {
                        case 'r': {
                            maze.cells[row][col].paths.right = true;
                            break;
                        }
                        case 'u': {
                            maze.cells[row][col].paths.up = true;
                            break;
                        }
                        case 'l': {
                            maze.cells[row][col].paths.left = true;
                            break;
                        }
                        case 'd': {
                            maze.cells[row][col].paths.down = true;
                            break;
                        }
                    }
                }
            }
            if (maze.start.x == -1) { // if coordinates did not changed no start
                //System.out.println("bad maze: found no start cell");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("found no start cell");
                alert.showAndWait();
                return null;
            }
            if (maze.finish.x == -1) {
                //System.out.println("bad maze: found no finish cell");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("found no finish cell");
                alert.showAndWait();
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Just numbers!");
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return maze;
    }
}


