import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import java.util.LinkedList;
import java.util.Queue;


public class Maze {

    private Random rand;
    private int size;
    private MazeCell[][] maze;

    public Maze(int size) {

        this.size = size;
        maze = new MazeCell[size][size];
        initializeMaze(size);

        //maze[1][1].connect_upper_cell(maze[0][1]);
        //maze[1][1].connect_bottom_cell(maze[2][1]);
        //maze[1][1].connect_left_cell(maze[1][0]);
        //maze[1][1].connect_right_cell(maze[1][2]);

        maze[size - 1][size - 1].set_isGoal();

        generateMaze();
        shortest_path();
        //isAllConnected();
    }

    private void initializeMaze(int size) {

        rand = new Random();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                maze[row][col] = new MazeCell(row, col);
            }
        }
    }

    private void generateMaze() {

        MazeCell start = maze[0][0];

        start.set_inMaze();
        ArrayList<MazeCell> start_neighbor_cells = get_valid_cells(start);
        List<MazeCell> frontier = new ArrayList<>(start_neighbor_cells);


        while (!frontier.isEmpty()) {
            System.out.println(frontier.size());
            MazeCell current = frontier.get(rand.nextInt(frontier.size()));

            current.set_inMaze();

            ArrayList<MazeCell> valid_cells = get_valid_cells(current);
            ArrayList<MazeCell> neighbors = new ArrayList<>();

            for (MazeCell cell : valid_cells) {

                if (!cell.get_inMaze() && !frontier.contains(cell)) {
                    frontier.add(cell);
                } else if (cell.get_inMaze()) {
                    neighbors.add(cell);
                }
            }


            //randomly pick frontier neighbor cell
            if (!neighbors.isEmpty()) {

                int randomIndex = rand.nextInt(neighbors.size());
                MazeCell cell_to_connect = neighbors.get(randomIndex);

                //Locate cell to properly connect them

                //Connect above
                if (cell_to_connect.getRow() < current.getRow()) {
                    current.connect_upper_cell(cell_to_connect);
                }
                //Connect below
                else if (cell_to_connect.getRow() > current.getRow())
                    current.connect_bottom_cell(cell_to_connect);
                    //Connect right
                else if (cell_to_connect.getCol() > current.getCol())
                    current.connect_right_cell(cell_to_connect);
                    //Connect left
                else if (cell_to_connect.getCol() < current.getCol())
                    current.connect_left_cell(cell_to_connect);

            }
            frontier.remove(current);
        }


    }

    private void shortest_path() {
        Queue<MazeCell> q = new LinkedList<>();
        Map<MazeCell, MazeCell> predecessors = new HashMap<>(); // Track predecessors
        List<MazeCell> visited = new LinkedList<>();

        MazeCell start = maze[0][0];
        q.add(start);
        visited.add(start);
        predecessors.put(start, null); // Start has no predecessor

        while (!q.isEmpty()) {
            MazeCell curr = q.poll();
            curr.setVisited();

            if (curr.get_isGoal()) {
                System.out.println("FOUND GOAL");
                System.out.println(curr.getCol());
                System.out.println(curr.getRow());

                // Backtrack and mark the shortest path
                markShortestPath(predecessors, curr);
                break;
            }

            // Explore neighbors
            checkAndEnqueue(curr, curr.getTop(), q, visited, predecessors);
            checkAndEnqueue(curr, curr.getBottom(), q, visited, predecessors);
            checkAndEnqueue(curr, curr.getRight(), q, visited, predecessors);
            checkAndEnqueue(curr, curr.getLeft(), q, visited, predecessors);
        }
    }

    // Helper function to enqueue cells and track predecessors
    private void checkAndEnqueue(MazeCell curr, MazeCell neighbor, Queue<MazeCell> q, List<MazeCell> visited, Map<MazeCell, MazeCell> predecessors) {
        if (neighbor != null && !visited.contains(neighbor)) {
            visited.add(neighbor);
            q.add(neighbor);
            predecessors.put(neighbor, curr); // Track where the cell came from
        }
    }

    // Backtrack from goal to start and mark the shortest path
    private void markShortestPath(Map<MazeCell, MazeCell> predecessors, MazeCell goal) {
        MazeCell curr = goal;
        while (curr != null) {
            curr.set_shortestPath(); // Assuming a method exists to visually mark the shortest path
            curr = predecessors.get(curr);
        }
    }


    private ArrayList<MazeCell> get_valid_cells(MazeCell current){

        //create a list of valid cells that are inside maze
        int left_cell  = current.getCol() - 1;
        int right_cell = current.getCol() + 1;
        int upper_cell = current.getRow() - 1;
        int lower_cell = current.getRow() + 1;

        ArrayList<MazeCell> valid_cells = new ArrayList<>();
        if (left_cell >= 0) valid_cells.add(maze[current.getRow()][left_cell]);
        if (right_cell < size) valid_cells.add(maze[current.getRow()][right_cell]);
        if (upper_cell >= 0) valid_cells.add(maze[upper_cell][current.getCol()]);
        if (lower_cell < size) valid_cells.add(maze[lower_cell][current.getCol()]);

        return valid_cells;

    }

    public MazeCell[][] getMaze(){

        return maze;
    }
}
