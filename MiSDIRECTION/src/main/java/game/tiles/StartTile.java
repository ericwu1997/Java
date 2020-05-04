package main.java.game.tiles;

public class StartTile extends Tile{

    private static int PLAYER_1_DIRECTION = 4; // down-right
    private static int PLAYER_2_DIRECTION = 6; // down-left
    private static int PLAYER_3_DIRECTION = 8; // up-left
    private static int PLAYER_4_DIRECTION = 2; // up-right
    
    private int playerNumber;
    
    public StartTile(int direction) {
        super(direction);
        this.revealed = true;
        // TODO Auto-generated constructor stub
    }
    
    public StartTile(int direction, int playerNum) {
        super(direction);
        this.playerNumber = playerNum;
        this.revealed = true;
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
        if(this.presentToken != null) {
            return "[X]";
        }
        return "[S]";
    }

    @Override
    public String getTypeString() {
        return "Start";
    }
}
