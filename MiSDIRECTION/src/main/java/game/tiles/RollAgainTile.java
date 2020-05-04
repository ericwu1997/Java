package main.java.game.tiles;

public class RollAgainTile extends Tile {

    public RollAgainTile(int direction) {
        super(direction);
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.revealed) {
            return "[R]";
        }
        return "[?]";
    }

    
    public String getTypeString() {
        return "RollAgain";
    }
}
