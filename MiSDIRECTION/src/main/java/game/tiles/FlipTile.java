package main.java.game.tiles;

public class FlipTile extends Tile {

    public FlipTile(int direction) {
        super(direction);
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.revealed) {
            return "[F]";
        }
        return "[?]";
    }
    
    public String getTypeString() {
        return "Flip";
    }

}
