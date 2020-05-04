package main.java.game.tiles;

public class ReverseTile extends Tile {
    
    private int playerNumber;
    
    public ReverseTile(int direction) {
        super(direction);
        // TODO Auto-generated constructor stub
    }
    
    public ReverseTile(int direction, int playerNum) {
        super(direction);
        this.playerNumber = playerNum;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.revealed) {
            return "[r]";
        }
        return "[?]";
    }
    
    public String getTypeString() {
        return "Reverse";
    }

}
