import java.sql.SQLOutput;

public class Player {

    MazeCell[][] maze;

    private int col;
    private int row;
    private boolean go_left;
    private boolean go_right;
    private boolean go_up;
    private boolean go_down;
    private boolean finished;
    private boolean needs_scoring;

    private float size;
    private int score;
    private MazeCell current_cell;
    private double total_time;
    private Scores score_keeper;

    private double start_time;
    public Player(float size,MazeCell[][] maze,double start_time,Scores score_keeper){
        col = 0;
        row = 0;
        go_left = false;
        go_right = false;
        go_up = false;
        go_down = false;
        finished = false;
        needs_scoring = true;

        this.score_keeper=score_keeper;

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
        if(go_left && !finished){
            current_cell.set_playerVisited();
            col -=1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
            if (current_cell.get_isGoal()) finished = true;
            check_moves();
        }
    }
    public void move_player_right() {
        if (go_right && !finished) {
            current_cell.set_playerVisited();
            col += 1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
            if (current_cell.get_isGoal()) finished = true;
            check_moves();
        }
    }
    public void move_player_up(){
        if(go_up && !finished){
            current_cell.set_playerVisited();
            row -=1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
            if (current_cell.get_isGoal()) finished = true;
            check_moves();
        }
    }
    public void move_player_down(){
        if(go_down && !finished){
            current_cell.set_playerVisited();
            row +=1;
            current_cell = maze[row][col];
            if (!current_cell.get_playerVisited()) score += current_cell.get_points();
            if (current_cell.get_isGoal()) finished = true;
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
    public void update_player(double elapsed_time){
        if(!finished) total_time += elapsed_time;
        else if (finished && needs_scoring){
            needs_scoring = false;
            double rounded_time = Math.round(total_time * 10.0) / 10.0;
            score_keeper.check_score(((maze.length)/5)-1,score,rounded_time);

        }
    }
    public double getTime(){

        return total_time;
    }
}