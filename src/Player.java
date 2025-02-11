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
    private int score;
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

        if(current_cell.getLeft() != null){
            go_left = true;


        }
        if(current_cell.getRight() != null){
            go_right = true;


        }
        if(current_cell.getTop() != null){
            go_up = true;

        }
        if(current_cell.getBottom() != null){
            go_down = true;
        }



    }

    public void move_player_left(){
        if(go_left){
            current_cell.setVisited();
            col -=1;
            current_cell = maze[row][col];
            if (!current_cell.get_visited()) score += current_cell.get_points();
            System.out.println("POINTS GAINED"+current_cell.get_points());
            check_moves();
        }
    }
    public void move_player_right() {
        if (go_right) {
            current_cell.setVisited();
            col += 1;
            current_cell = maze[row][col];
            if (!current_cell.get_visited()) score += current_cell.get_points();
            System.out.println("POINTS GAINED"+current_cell.get_points());
            check_moves();
        }
    }
    public void move_player_up(){
        if(go_up){
            current_cell.setVisited();
            row -=1;
            current_cell = maze[row][col];
            if (!current_cell.get_visited()) score += current_cell.get_points();

            check_moves();
        }
    }
    public void move_player_down(){
        if(go_down){
            current_cell.setVisited();
            row +=1;
            current_cell = maze[row][col];
            if (!current_cell.get_visited()) score += current_cell.get_points();

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

