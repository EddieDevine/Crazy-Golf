public class ScoreCard {
    private static int score = 0;

    //increase score
    public static void updateScore(int inScore){
        score += inScore;
    }

    //reset score
    public static void resetScore(){
        score = 0;
    }

    //get score
    public static int getScore(){
        return score;
    }
}
