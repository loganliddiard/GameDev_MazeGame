import java.sql.SQLOutput;

public class Player {

    MazeCell[][] maze;

    private int col;
    private int row;

    private boolean go_left;
    private boolean go_right;
    private boolean go_up;
    private boolean go_down;
    private float size;
    private MazeCell current_cell;
    public Player(float size,MazeCell[][] maze){
        col = 1;
        row = 1;
        go_left = false;
        go_right = false;
        go_up = false;
        go_down = false;

        this.maze = maze;
        this.size = size;

        current_cell = this.maze[row][col];
        check_moves();
    }

    //check where we can move when moving to a new cell
    public void check_moves(){

        System.out.println("CURRENT LOCATION:");
        System.out.println("Row:"+row);
        System.out.println("Col:"+col);
        go_left = false;
        go_right = false;
        go_up = false;
        go_down = false;

        System.out.println();

        if(current_cell.getLeft() != null){
            go_left = true;
            System.out.println("CAN GO LEFT <");

        }
        if(current_cell.getRight() != null){
            go_right = true;
            System.out.println("CAN GO RIGHT >");

        }
        if(current_cell.getTop() != null){
            go_up = true;
            System.out.println("CAN GO UP ^");
        }
        if(current_cell.getBottom() != null){
            go_down = true;
            System.out.println("CAN GO DOWN v");
        }



    }

    public void move_player_left(){
        if(go_left){
            col -=1;
            current_cell = maze[row][col];
            check_moves();
        }
    }
    public void move_player_right() {
        if (go_right) {
            col += 1;
            current_cell = maze[row][col];
            check_moves();
        }
    }
    public void move_player_up(){
        if(go_up){
            row -=1;
            current_cell = maze[row][col];
            check_moves();
        }
    }
    public void move_player_down(){
        if(go_down){
            row +=1;
            current_cell = maze[row][col];
            check_moves();
        }
    }
    public int getCol(){
        return col;
    }
    public int getRow(){
        return row;
    }
    public float getSize(){
        return size;
    }
}

