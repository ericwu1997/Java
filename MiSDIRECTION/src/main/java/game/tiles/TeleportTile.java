package main.java.game.tiles;

public class TeleportTile extends Tile {
    
    private int playerNumber;
    
    public TeleportTile(int direction) {
        super(direction);
        // TODO Auto-generated constructor stub
    }
    
    public TeleportTile(int direction, int playerNum) {
        super(direction);
        this.playerNumber = playerNum;
    }
    
    /**
     * @return the playerNumber
     */
    public int getPlayerNumber() {
        return playerNumber;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.revealed) {
            return "[T]";
        }
        return "[?]";
    }

    @Override
    public String getTypeString() {
        return "Teleport";
    }
}
