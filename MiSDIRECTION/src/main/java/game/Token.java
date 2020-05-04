package main.java.game;

public class Token {

    /* row and col represent the x, y coordinates of the token's current position on the board
     * and may or may not actually be used
     */
    private int row;
    private int col;
    
    /* when rotateAtStart is true, it represents the disk being LIGHT SIDE UP
     * false represents LIGHT SIDE DOWN / DARK SIDE UP
     */
    private boolean rotateAtStart;
    
    /*
     * colour is used to correspond to player numbers elsewhere, but internally can be used 
     * to determine draw colour and thus has a place inside the DisplayToken class
     */
    private int colour;
      
    public Token() {
        this.row = 0;
        this.col = 0;
        this.rotateAtStart = false;
    }
    
    public Token(int colour) {
        this.row = 0;
        this.col = 0;
        this.colour = colour;
        this.rotateAtStart = false;
    }
    
    public Token(int row, int col, int colour) {
        this.row = row;
        this.col = col;
        this.colour = colour;
        this.rotateAtStart = false;
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
     * shortcut method
     * @param row
     * @param col
     */
    public void setRowCol(int row, int col) {
        setRow(row);
        setCol(col);
    }

    /**
     * @return the colour
     */
    public int getColour() {
        return colour;
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(int colour) {
        this.colour = colour;
    }

    public boolean rotateAtStart() {
        return this.rotateAtStart;
    }

    public boolean rotateAtEnd() {
        return !this.rotateAtStart;
    }
    
    /**
     * flips the disc that determines when the player can rotate a tile
     */
    public void flipDisc() {
        this.rotateAtStart = !this.rotateAtStart;
    }
    
}
