package main.java.game.tiles;

public class GoalTile extends Tile {

    public GoalTile() {
        super(true, 0, 0, null);
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[G]";
    }
    
    @Override
    public String getTypeString() {
        return "Goal";
    }

}
