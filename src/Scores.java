import java.util.ArrayList;

public class Scores {

    int [][] scores;
    double [][] times;

    public Scores(){

        scores = new int[4][5];
        times = new double[4][5];

        int base_score = 25;
        int base_time = 25;

        for(int i = 0; i < scores.length;i++){
            int temp_score = base_score;
            double temp_time = base_time;
            for(int j = 0; j < scores[i].length;j++){

                scores[i][j] = temp_score;
                times[i][j] = temp_time;
                if (i != 0)temp_score-=(5*i);
                else temp_score -=5;
                temp_time += 1.5;


            }
            base_score+= 25;
            base_time+=5;
        }
    }

    public int get_score(int maze, int place){

        return scores[maze][place];
    }
    public double get_time(int maze, int place){

        return times[maze][place];
    }

    public void check_score(int maze, int score, double time) {
        boolean isHigher = false;

        // Check if we need to replace score
        for (int i = 0; i < scores[maze].length; i++) {
            // Find the first place where the score is higher than the current score
            if (score > scores[maze][i]) {
                // Shift the scores and times downwards to make space for the new score
                for (int j = scores[maze].length - 1; j > i; j--) {
                    scores[maze][j] = scores[maze][j - 1];
                    times[maze][j] = times[maze][j - 1];
                }

                // Insert the new score and time in the correct position
                scores[maze][i] = score;
                times[maze][i] = time;
                break;
            }
        }


    }

}
