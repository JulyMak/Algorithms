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

            maze.DimY = Integer.parseInt(sCurrentLine.trim().split(" +")[0]);
            maze.DimX = Integer.parseInt(sCurrentLine.trim().split(" +")[1]);
            maze.cells = new Cell[maze.DimY][];
            for (int i = 0; i < maze.DimY; i++) {
                maze.cells[i] = new Cell[maze.DimX];
                for (int j = 0; j < maze.DimX; j++) {
                    maze.cells[i][j] = new Cell();
                    maze.cells[i][j].paths = new Cell.Paths();
                }
            }
            maze.start = new Start();
            maze.finish = new Finish();

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.equals("")) {
                    continue;
                }
                String[] line = sCurrentLine.trim().split(" +");
                if (line.length != 4) {
                    System.out.println("loadMazeFromFile(String filename) error: invalid string " + sCurrentLine +
                            " format is: <row col val ?ruld>");
                    return null;
                }
                int row = Integer.parseInt(line[0]);
                int col = Integer.parseInt(line[1]);


                if (line[2].equals("s")) {
                    if (maze.start.x == -1 && maze.start.y == -1) {
                        maze.start.y = row;
                        maze.start.x = col;
                    } else {
                        System.out.println("bad maze: found more than 1 start cell");
                        return null;
                    }
                }
                if (line[2].equals("f")) {
                    if (maze.finish.x == -1 && maze.finish.y == -1) {
                        maze.finish.y = row;
                        maze.finish.x = col;
                    } else {
                        System.out.println("bad maze: found more than 1 finish cell");
                        return null;
                    }
                }

                maze.cells[row][col].value = line[2];

                char[] paths = line[3].toCharArray();

                for (int i = 0; i < paths.length; i++) {
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
            if (maze.start.x == -1) {
                System.out.println("bad maze: found no start cell");
                return null;
            }
            if (maze.finish.x == -1) {
                System.out.println("bad maze: found no finish cell");
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("error");
        } catch (NumberFormatException e) {
            System.out.println("Just numbers!");
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


