public class MazeCell {

    private MazeCell left_cell;
    private MazeCell right_cell;
    private MazeCell up_cell;
    private MazeCell down_cell;

    private final int row;
    private final int col;

    private boolean visited;
    private boolean inMaze;

    public MazeCell(int row, int col){
        this.row = row;
        this.col = col;
        inMaze = false;

    }

    //setters
    public void connect_left_cell(MazeCell cell) {
        this.left_cell = cell;
        cell.right_cell = this;
    }

    public void connect_right_cell(MazeCell cell){
        this.right_cell = cell;
        cell.left_cell = this;
    }

    public void connect_upper_cell(MazeCell cell){
        this.up_cell = cell;
        cell.down_cell = this;
    }
    public void connect_bottom_cell(MazeCell cell){
        this.down_cell = cell;
        cell.up_cell = this;
    }
    public void setVisited(){
        this.visited = true;
    }
    public void set_inMaze(){
        this.inMaze = true;
    }

    //getters
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }

    public boolean get_inMaze(){
        return inMaze;
    }

    public MazeCell getLeft(){
        return left_cell;
    }
    public MazeCell getRight(){
        return right_cell;
    }
    public MazeCell getTop(){
        return up_cell;
    }
    public MazeCell getBottom(){
        return down_cell;
    }
    public boolean get_visited(){
        return visited;
    }

}
