package main.java.control;

public class Player {

    private int playerNumber; // this is a stand-in for colour right now, not sure exactly what we're doing for that
    private boolean dirReversed;
    private boolean skipNext;
    private boolean isAI;
    
    public Player(int playerNum) {
        this.playerNumber = playerNum;
        this.dirReversed = false;
        this.skipNext = false;
        this.isAI = false;
    }

    /**
     * @return the playerNumber
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @param playerNumber the playerNumber to set
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * @return the dirReversed
     */
    public boolean dirReversed() {
        return dirReversed;
    }

    /**
     * @param dirReversed the dirReversed to set
     */
    public void setDirReversed(boolean dirReversed) {
        this.dirReversed = dirReversed;
    }

    /**
     * @return the skipNext
     */
    public boolean skipNext() {
        return skipNext;
    }

    /**
     * @param skipNext the skipNext to set
     */
    public void setSkipNext(boolean skipNext) {
        this.skipNext = skipNext;
    }

    /**
     * @return the isAI
     */
    public boolean isAI() {
        return isAI;
    }

    /**
     * @param isAI the isAI to set
     */
    public void setAI(boolean isAI) {
        this.isAI = isAI;
    }
    
}
