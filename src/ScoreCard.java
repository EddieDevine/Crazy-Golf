public class ScoreCard {
    private static int score;

    public static void updateScore(int inScore){
        score += inScore;
    }

    public static int getScore(){
        return score;
    }
}
