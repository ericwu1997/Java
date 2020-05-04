package main.java.game.tiles;

import main.java.game.Token;
import main.java.property.Properties;

public class Tile {
    
    public static final int DIR_MIN = 1;
    public static final int DIR_MAX = 8;
    
    /*
     * I thought of using an enum for these but I think it's probably
     * more trouble than it's worth at the moment
     */
    public static final int UP = 1;
    public static final int UP_RIGHT = 2;
    public static final int RIGHT = 3;
    public static final int DOWN_RIGHT = 4;
    public static final int DOWN = 5;
    public static final int DOWN_LEFT = 6;
    public static final int LEFT = 7;
    public static final int UP_LEFT = 8;
    
    private static final int ROTATION_VALUE = 2;
    private static final int ROTATION_MIN = 0;
    private static final int ROTATION_MAX = 6;
    
    protected boolean revealed;
    protected int direction;
    protected int rotation;
    protected Token presentToken;
    protected int row;
    protected int col;
    
    /**
     * "Default" constructor
     * @param direction
     */
    public Tile(int direction) {
        this.revealed = false;
        this.direction = direction;
        this.rotation = 0;
        this.presentToken = null;
    }
    
    /**
     * Fully parameterized constructor, minus position
     * @param revealed
     * @param direction
     * @param rotation
     * @param presentToken
     */
    public Tile(boolean revealed, int direction, int rotation, Token presentToken) {
        this.revealed = revealed;
        this.direction = direction;
        this.rotation = rotation;
        this.presentToken = presentToken;
    }

    /**
     * @return the revealed
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * @param revealed the revealed to set
     */
    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the rotation
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * @return the presentToken
     */
    public Token getPresentToken() {
        return presentToken;
    }

    /**
     * @param presentToken the presentToken to set
     */
    public void setPresentToken(Token presentToken) {
        this.presentToken = presentToken;
    }
    
    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Default way of setting revealed status to true
     */
    public void reveal() {
        this.revealed = true;
    }
    
    /**
     * This should be the only method invoked to rotate a tile, since it
     * contains the logic to keep the rotation value between 0 and 8
     * @param clockwise true if rotating clockwise; false if counterclockwise
     */
    public void rotate(boolean clockwise) {
        // debug print statements can be commented out later obviously
        System.out.println("debug: entering rotate method");
        this.rotation = (clockwise) ? this.rotation + ROTATION_VALUE : 
                                      this.rotation - ROTATION_VALUE;
        // prevent negative rotation value
        if(this.rotation < ROTATION_MIN) {
            System.out.println("debug: rotation below " + ROTATION_MIN);
            this.rotation += ROTATION_MAX; 
        }
        
        // prevent rotation values above maximum
        if(this.rotation > ROTATION_MAX) {
            System.out.println("debug: rotation above " + ROTATION_MAX);
            this.rotation -= ROTATION_MAX;
        }
        
        System.out.println("debug: new rotation value - " + this.rotation);
    }
    
    /**
     * Calculates the direction that the tile is currently pointing,
     * given its rotation value
     * @return the effective game direction of the tile
     */
    public int getCurrentDirection() {
        int effectiveDir = this.direction + this.rotation;
        
        if (effectiveDir > DIR_MAX) {
            effectiveDir = effectiveDir % DIR_MAX;
        }
        return effectiveDir;
    }
    
    /**
     * shortcut method
     * @param row
     * @param col
     */
    public void setRowCol(int row, int col) {
        this.setRow(row);
        this.setCol(col);
    }
    
    public int getPlayerNumber() {
        return 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if(this.revealed) {
            return "[" + this.direction + "]";
        }
        return "[?]";
    }
        
    public String getTypeString() {
        return "Normal";
    }
}
