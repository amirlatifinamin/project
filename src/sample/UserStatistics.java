package sample;

public class UserStatistics {
    private int numOfKilledPieces;
    private int numOfWins;
    private int numOfFailures;
    private int numOfPiecesInPile;
    private int sumOfDicesValue;
    private PieceType type;

    public UserStatistics (PieceType t){
        type = t;
        numOfFailures = 0;
        numOfKilledPieces = 0;
        numOfWins = 0;
        numOfPiecesInPile = 0;
        sumOfDicesValue = 0;
    }

    public void newRound(boolean lastRound){
        if (lastRound)
            numOfWins += 1;
        else
            numOfFailures += 1;
        numOfPiecesInPile = 0;
        numOfKilledPieces = 0;
        sumOfDicesValue = 0;
    }

    public void updateStats (int diceVal, int PiecesInPile, int killedPeises){
        sumOfDicesValue += diceVal;
        numOfPiecesInPile = PiecesInPile;
        numOfKilledPieces = killedPeises;
    }

    public int getNumOfFailures() {
        return numOfFailures;
    }

    public int getNumOfPiecesInPile() {
        return numOfPiecesInPile;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public int getNumOfKilledPieces() {
        return numOfKilledPieces;
    }

    public int getSumOfDicesValue() {
        return sumOfDicesValue;
    }

    public void reset(){
        numOfFailures = 0;
        numOfKilledPieces = 0;
        numOfWins = 0;
        numOfPiecesInPile = 0;
        sumOfDicesValue = 0;
    }
}
