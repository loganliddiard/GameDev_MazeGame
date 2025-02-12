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
    private double total_time;

    private double start_time;
    public Player(float size,MazeCell[][] maze,double start_time){
        col = 1;
        row = 1;
        go_left = false;
        go_right = false;
        go_up = false;
        go_down = false;

        this.start_time = start_time;
        total_time = 0;
        this.maze = maze;
        this.size = size;

        score = 0;
        current_cell = this.maze[row][col];
        check_moves();
    }

    //check where we can move when moving to a new cell
    public void check_moves(){

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
            current_cell.set_playerVisited();
            col -=1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
            check_moves();
        }
    }
    public void move_player_right() {
        if (go_right) {
            current_cell.set_playerVisited();
            col += 1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
            check_moves();
        }
    }
    public void move_player_up(){
        if(go_up){
            current_cell.set_playerVisited();
            row -=1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
            check_moves();
        }
    }
    public void move_player_down(){
        if(go_down){
            current_cell.set_playerVisited();
            row +=1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
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
    public int getScore(){
        return score;
    }
    public void update_timer(double elapsed_time){
        total_time += elapsed_time;
    }
    public double getTime(){

        return total_time;
    }

}

