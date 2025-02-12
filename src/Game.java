import edu.usu.graphics.*;
import org.joml.primitives.Circled;
import org.joml.primitives.Circlef;
import org.w3c.dom.Text;

import static org.lwjgl.glfw.GLFW.*;

public class Game {
    private final Graphics2D graphics;

    private Maze maze;
    private Player player;
    private long time;
    private float cell_size;
    private boolean keypress;
    public Game(Graphics2D graphics) {
        this.graphics = graphics;
    }

    private boolean show_shortest_path;
    private boolean show_hint;
    private boolean show_crumbs;
    private boolean show_credits;
    private boolean show_highscores;

    private Texture dino;
    private Texture leaf;
    private Texture egg;
    Texture background;
    Font font;

    public void initialize() {
        keypress = false;
        show_shortest_path = false;
        show_hint = false;
        show_crumbs = false;
        dino = new Texture("resources/images/Pixel_Dino.png");
        leaf = new Texture("resources/images/leaf.png");
        egg = new Texture("resources/images/dino_egg.png");
        background = new Texture("resources/images/Dino_background.2.jpg");
        font = new Font("Arial", java.awt.Font.PLAIN, 42, true);
    }

    public void shutdown() {
    }

    public void run() {
        // Grab the first time
        double previousTime = glfwGetTime();

        while (!graphics.shouldClose()) {
            double currentTime = glfwGetTime();
            double elapsedTime = currentTime - previousTime;    // elapsed time is in seconds
            previousTime = currentTime;

            processInput(elapsedTime);
            update(elapsedTime);
            render(elapsedTime);

        }
    }

    private void processInput(double elapsedTime) {
        // Poll for window events: required in order for window, keyboard, etc events are captured.
        glfwPollEvents();


        // If user presses ESC, then exit the program
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(graphics.getWindow(), true);
        }
        // Maze size handler
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_F1) == GLFW_PRESS) {
            maze = new Maze(5);
            cell_size = 5.0f;
            player = new Player(1 / cell_size,maze.getMaze(),elapsedTime);

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_F2) == GLFW_PRESS) {

            maze = new Maze(10);
            cell_size = 10.0f;
            player = new Player(1 / cell_size,maze.getMaze(),elapsedTime);

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_F3) == GLFW_PRESS) {

            maze = new Maze(15);
            cell_size = 15.0f;
            player = new Player(1 / cell_size,maze.getMaze(),elapsedTime);

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_F4) == GLFW_PRESS) {

            maze = new Maze(20);

            cell_size = 20.0f;
            player = new Player(1 / cell_size,maze.getMaze(),elapsedTime);
        }

        // Movement handeler
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_W) == GLFW_PRESS) {
            if(!keypress){
                player.move_player_up();
                keypress = true;
                maze.update_shortest_path(player.getRow(),player.getCol());

            }

        }
        else if (glfwGetKey(graphics.getWindow(), GLFW_KEY_S) == GLFW_PRESS) {
            if(!keypress){
                player.move_player_down();
                keypress = true;
                maze.update_shortest_path(player.getRow(),player.getCol());

            }

        }
        else if (glfwGetKey(graphics.getWindow(), GLFW_KEY_A) == GLFW_PRESS) {
            if(!keypress){
                player.move_player_left();
                keypress = true;
                maze.update_shortest_path(player.getRow(),player.getCol());
            }
        }
        else if (glfwGetKey(graphics.getWindow(), GLFW_KEY_D) == GLFW_PRESS) {

            if(!keypress){
                player.move_player_right();
                keypress = true;
                maze.update_shortest_path(player.getRow(),player.getCol());
            }
        }
        else if (glfwGetKey(graphics.getWindow(), GLFW_KEY_B) == GLFW_PRESS) {
            if(!keypress){
                show_crumbs = !show_crumbs;
                keypress = true;
            }
        }
        else if (glfwGetKey(graphics.getWindow(), GLFW_KEY_P) == GLFW_PRESS) {
            if (!keypress) {
                show_shortest_path = !show_shortest_path;
                keypress = true;
            }
        }
        else if (glfwGetKey(graphics.getWindow(), GLFW_KEY_H) == GLFW_PRESS) {
            if(!keypress){
                show_hint = !show_hint;
                keypress = true;
            }
        }

        else keypress = false;

    }

    private void update(double elapsedTime) {

        if (player != null) player.update_timer(elapsedTime);

    }

    private void render(double elapsedTime) {
        graphics.begin();
        graphics.drawTextByHeight(font, "F1 - New Game 5x5", -0.95f, -0.50f, 0.04f, Color.WHITE);
        graphics.drawTextByHeight(font, "F2 - New Game 10x10", -0.95f, -0.45f, 0.04f, Color.WHITE);
        graphics.drawTextByHeight(font, "F3 - New Game 15x15", -0.95f, -0.40f, 0.04f, Color.WHITE);
        graphics.drawTextByHeight(font, "F4 - New Game 20x20", -0.95f, -0.35f, 0.04f, Color.WHITE);
        graphics.drawTextByHeight(font, "F5 - Display High Scores", -0.95f, -0.3f, 0.04f, Color.WHITE);
        graphics.drawTextByHeight(font, "F6 - Display Credits", -0.95f, -0.25f, 0.04f, Color.WHITE);

        if (player != null) {
            graphics.drawTextByHeight(font, "SCORE: "+player.getScore(), 0.55f, -0.5f, 0.05f, Color.WHITE);
            graphics.drawTextByHeight(font, "TIME: "+String.format("%.1f", player.getTime()), 0.55f, -0.45f, 0.05f, Color.WHITE);
        }



        Rectangle myBox = new Rectangle(-0.525f, -0.525f, 1.05f, 1.05f,-1);


        final float MAZE_CORNER_LEFT = -0.5f;
        final float MAZE_CORNER_TOP = -0.5f;
        this.graphics.draw(background,myBox,Color.WHITE);
        if(maze != null){
            for (var row : maze.getMaze()) {
                for (var cell : row) {
                    renderCell(cell);
                }
            }
            float left = MAZE_CORNER_LEFT + player.getCol() * player.getSize();
            float top = MAZE_CORNER_TOP + player.getRow() * player.getSize();
            float size = player.getSize();


            Rectangle player = new Rectangle(left, top, size, size);

            this.graphics.draw(dino,player, Color.BLUE);
        }

        graphics.end();
    }

    private void renderCell(MazeCell cell) {
        final float CORNER_LEFT = -0.5f;
        final float CORNER_TOP = -0.5f;
        final float CELL_SIZE = 1 / cell_size;
        final float WALL_THICKNESS = 0.005f;

        float left = CORNER_LEFT + cell.getCol() * CELL_SIZE;
        float top = CORNER_TOP + cell.getRow() * CELL_SIZE;

        if (cell.getTop() == null) {
            Rectangle r = new Rectangle(left, top, CELL_SIZE, WALL_THICKNESS);
            this.graphics.draw(r, Color.BLACK);
        }
        if (cell.getBottom() == null) {
            Rectangle r = new Rectangle(left, top + CELL_SIZE, CELL_SIZE+WALL_THICKNESS, WALL_THICKNESS);
            this.graphics.draw(r, Color.BLACK);
        }
        if (cell.getLeft() == null) {
            Rectangle r = new Rectangle(left, top, WALL_THICKNESS, CELL_SIZE);
            this.graphics.draw(r, Color.BLACK);
        }
        if (cell.getRight() == null) {
            Rectangle r = new Rectangle(left + CELL_SIZE, top, WALL_THICKNESS, CELL_SIZE);
            this.graphics.draw(r, Color.BLACK);
        }


        if (cell.get_playerVisited() && show_crumbs){
            Rectangle r = new Rectangle(left+(CELL_SIZE/3),top+(CELL_SIZE/3),CELL_SIZE/3,CELL_SIZE/3);
            this.graphics.draw(r, Color.BLACK);


        }
        if (cell.get_shortestPath() && show_shortest_path){
            Rectangle r = new Rectangle(left+(CELL_SIZE/3),top+(CELL_SIZE/3),CELL_SIZE/3,CELL_SIZE/3);
            this.graphics.draw(leaf,r, Color.PURPLE);

        } else if (cell.getHint() && show_hint){
            Rectangle r = new Rectangle(left+(CELL_SIZE/3),top+(CELL_SIZE/3),CELL_SIZE/3,CELL_SIZE/3);
            this.graphics.draw(leaf,r, Color.WHITE);

        }

        if (cell.get_isGoal()){
            Rectangle r = new Rectangle(left+(CELL_SIZE/4),top+(CELL_SIZE/4),CELL_SIZE/2,CELL_SIZE/2);
            this.graphics.draw(egg,r, Color.WHITE);

        }
    }
}
