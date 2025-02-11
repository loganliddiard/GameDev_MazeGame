import edu.usu.graphics.*;

import static org.lwjgl.glfw.GLFW.*;

public class Game {
    private final Graphics2D graphics;

    private Maze maze;
    private Player player;
    private float cell_size;
    public Game(Graphics2D graphics) {
        this.graphics = graphics;
    }



    public void initialize() {

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
            player = new Player(1 / cell_size,maze.getMaze());

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_F2) == GLFW_PRESS) {

            maze = new Maze(10);
            cell_size = 10.0f;
            player = new Player(1 / cell_size,maze.getMaze());

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_F3) == GLFW_PRESS) {

            maze = new Maze(15);
            cell_size = 15.0f;
            player = new Player(1 / cell_size,maze.getMaze());

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_F4) == GLFW_PRESS) {

            maze = new Maze(20);

            cell_size = 20.0f;
            player = new Player(1 / cell_size,maze.getMaze());
        }

        // Movement handeler
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_W) == GLFW_PRESS) {
            player.move_player_up();

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_S) == GLFW_PRESS) {
            player.move_player_down();

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_A) == GLFW_PRESS) {
            player.move_player_left();

        }
        if (glfwGetKey(graphics.getWindow(), GLFW_KEY_D) == GLFW_PRESS) {
            player.move_player_right();

        }

    }

    private void update(double elapsedTime) {

    }

    private void render(double elapsedTime) {
        graphics.begin();

        Rectangle myBox = new Rectangle(-0.525f, -0.525f, 1.05f, 1.05f);

        final float MAZE_CORNER_LEFT = -0.5f;
        final float MAZE_CORNER_TOP = -0.5f;
        this.graphics.draw(myBox,Color.PURPLE);

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
            this.graphics.draw(player, Color.BLUE);
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
            this.graphics.draw(r, Color.WHITE);
        }
        if (cell.getBottom() == null) {
            Rectangle r = new Rectangle(left, top + CELL_SIZE, CELL_SIZE, WALL_THICKNESS);
            this.graphics.draw(r, Color.WHITE);
        }
        if (cell.getLeft() == null) {
            Rectangle r = new Rectangle(left, top, WALL_THICKNESS, CELL_SIZE);
            this.graphics.draw(r, Color.WHITE);
        }
        if (cell.getRight() == null) {
            Rectangle r = new Rectangle(left + CELL_SIZE, top, WALL_THICKNESS, CELL_SIZE);
            this.graphics.draw(r, Color.WHITE);
        }
    }
}
