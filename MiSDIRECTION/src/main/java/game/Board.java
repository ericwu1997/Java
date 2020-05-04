package main.java.game;

import java.util.LinkedList;
import java.util.Random;

import main.java.game.tiles.*;

public class Board {

    /* the following values represent the number of each type of tile
     * PER DIRECTION that an arrow can point, i.e. multiplied by 8
     */
    private static final int STD_TILES = 17;
    private static final int FLIP_TILES = 1;
    private static final int REVERSE_DIRECTION_PER_COLOUR = 1;
    private static final int ROLL_AGAIN_TILES = 1;
    private static final int TELEPORT_TILES_PER_COLOUR = 1;

    /* this last value is NOT multiplied by 8, there are only 4 skip tiles
     * pointing one in each diagonal direction
     */
    private static final int SKIP_TURN_TILES_PER_COLOUR = 1;

    public static final int NUM_PLAYERS = 4;
    private static final int PLAYER_1_DIRECTION = 4; // down-right
    private static final int PLAYER_2_DIRECTION = 6; // down-left
    private static final int PLAYER_3_DIRECTION = 8; // up-left
    private static final int PLAYER_4_DIRECTION = 2; // up-right

    private static final int MIN_INDEX = 0;
    private static final int CENTER_INDEX = 7;
    private static final int MAX_INDEX = 14;
    
    private static final int INDEX_OFFSET = 1;
    private static final int HOP = 1;
    
    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 15;
    public static final int MIN_COL = 1;
    public static final int MAX_COL = 15;
    public static final int CENTER_ROW = 8;
    public static final int CENTER_COL = 8;
    public static final int REV_DIR = 4;

    private LinkedList<Tile> tileSet;
    private Tile[][] board; // outer array is rows, inner array is columns
    
    private StartTile p1Start;
    private StartTile p2Start;
    private StartTile p3Start;
    private StartTile p4Start;

    /**
     * Default constructor which should handle creating the board and 
     * initializing the tileset through submethods
     */
    public Board() {
        initBoard();
    }

    private void initBoard() {
        this.tileSet = new LinkedList<Tile>();
        this.board = new Tile[15][15];
        initStartTiles();
        initGoalTile();
        initStdTiles();
        initFlipTiles();
        initReverseTiles();
        initSkipTiles();
        initRollAgainTiles();
        initTeleportTiles();
        System.out.println("number of tiles in tileset: " + tileSet.size());
        shuffleTiles();
        System.out.println("number of tiles in tileset: " + tileSet.size());
        placeTiles();
        System.out.println("number of tiles in tileset: " + tileSet.size());
    }

    /**
     * creates start tile for each player, places them on the board,
     * but DOES NOT add them to the tileset for randomization
     */
    private void initStartTiles() {
        StartTile p1Start = new StartTile(PLAYER_1_DIRECTION, 1);
        p1Start.reveal(); // probably unnecessary but doesn't hurt
        p1Start.setRowCol(MIN_ROW, MIN_ROW);        
        this.board[MIN_INDEX][MIN_INDEX] = p1Start;
        this.p1Start = p1Start;

        StartTile p2Start = new StartTile(PLAYER_2_DIRECTION, 2);
        p2Start.reveal();
        p2Start.setRowCol(MIN_ROW, MAX_COL);
        this.board[MIN_INDEX][MAX_INDEX] = p2Start;
        this.p2Start = p2Start;

        StartTile p3Start = new StartTile(PLAYER_3_DIRECTION, 3);
        p3Start.reveal();
        p3Start.setRowCol(MAX_ROW, MAX_COL);
        this.board[MAX_INDEX][MAX_INDEX] = p3Start;
        this.p3Start = p3Start;

        StartTile p4Start = new StartTile(PLAYER_4_DIRECTION, 4);
        p4Start.reveal();
        p4Start.setRowCol(MAX_ROW, MIN_COL);
        this.board[MAX_INDEX][MIN_INDEX] = p4Start;
        this.p4Start = p4Start;
    }

    /**
     * creates the goal tile and places it in the center of the board,
     * but DOES NOT add it to the tileset for randomization
     */
    private void initGoalTile() {
        Tile goal = new GoalTile();
        goal.setRow(CENTER_ROW);
        goal.setCol(CENTER_ROW);
        this.board[CENTER_INDEX][CENTER_INDEX] = goal;
    }

    /**
     * creates the standard tiles and places them in the tileset; which
     * will be randomized later
     */
    private void initStdTiles() {
        // outer loop to run once for every direction
        for(int dir = Tile.DIR_MIN; dir <= Tile.DIR_MAX; dir++) {

            // inner loop generates correct number tiles for each dir
            for(int n = 0; n < STD_TILES; n++) {
                Tile tile = new Tile(dir);
                this.tileSet.add(tile);
            }

        }
    }

    private void initFlipTiles() {
        // outer loop to run once for every direction
        for(int dir = Tile.DIR_MIN; dir <= Tile.DIR_MAX; dir++) {
            Tile tile = new FlipTile(dir);
            this.tileSet.add(tile);
        }
    }

    private void initReverseTiles() {
        // outer loop to run once for every direction
        for(int dir = Tile.DIR_MIN; dir <= Tile.DIR_MAX; dir++) {
            for (int colour = 0; colour < NUM_PLAYERS; colour++) {
                int playerNum = colour + INDEX_OFFSET;
                Tile tile = new ReverseTile(dir, playerNum);
                this.tileSet.add(tile);
            }
            
        }
    }

    /**
     * magic numbers should be removed at some point but I'm not
     * sure how the player colour/tile direction here is correlated yet
     */
    private void initSkipTiles() {
        for(int dir = 2; dir <= 8; dir += 2) {
            int playerNum = dir / 2;
            Tile tile = new SkipTile(dir, playerNum);
            this.tileSet.add(tile);
        }
    }

    private void initRollAgainTiles() {
        // outer loop to run once for every direction
        for(int dir = Tile.DIR_MIN; dir <= Tile.DIR_MAX; dir++) {
            Tile tile = new RollAgainTile(dir);
            this.tileSet.add(tile);
        }
    }

    private void initTeleportTiles() {
        // outer loop to run once for every direction
        for(int dir = Tile.DIR_MIN; dir <= Tile.DIR_MAX; dir++) {
            for (int colour = 0; colour < NUM_PLAYERS; colour++) {
                int playerNum = colour + INDEX_OFFSET;
                Tile tile = new TeleportTile(dir, playerNum);
                this.tileSet.add(tile);
            }
        }
    }

    private void shuffleTiles() {
        Random rand = new Random(System.nanoTime());
        LinkedList<Tile> shuffled = new LinkedList<Tile>();
        int index = 0;
        int listLength = this.tileSet.size();
        Tile transfer = null;
        
        while(listLength > 0) {
            index = rand.nextInt(listLength);
            transfer = this.tileSet.remove(index);
            if(transfer != null) {
                shuffled.add(transfer);
            }
            listLength = this.tileSet.size();
        }
        this.tileSet = shuffled;
    }
    
    private void placeTiles() {
        // outer loop handles rows
        for(int row = 0; row < board[0].length; row++) {
            // inner loop handles columns
            for(int col = 0; col < board[1].length; col++) {
                // control block to prevent tiles with static placement from being overwritten
                if(!(row == MIN_INDEX && col == MIN_INDEX) && // not top-left corner
                        !(row == MIN_INDEX && col == MAX_INDEX) && // not top-right corner
                        !(row == MAX_INDEX && col == MIN_INDEX) && // not bottom-left corner
                        !(row == MAX_INDEX && col == MAX_INDEX) && // not bottom-right corner
                        !(row == CENTER_INDEX && col == CENTER_INDEX)) { // not center
                    Tile t = this.tileSet.poll(); // removes an element from the tileset and places it on the board
                    t.setRowCol(row + INDEX_OFFSET, col + INDEX_OFFSET);                   
                    this.board[row][col] = t;
                }              
            }     
        }  
    }
    
    /**
     * simple accessor for tiles on the board since the 2D array is private, using 
     * intuitive numbering i.e. 1-15 rather than array indices i.e. 0-14
     * CURRENTLY DOES NOT CHECK FOR ARRAY BOUNDS
     * @param row of the tile to return
     * @param col of the tile to return
     * @return the tile at the indicated coordinates in the 2D array
     */
    public Tile getTileAt(int row, int col) {
        return this.board[row-INDEX_OFFSET][col-INDEX_OFFSET];
    }
    
    /**
     * determines whether the current tile is on a board edge relative to
     * the current direction of travel; functionally designed for rebound/
     * redirection mechanics
     * @param tile the tile being evaluated
     * @param direction the intended direction of movement
     * @return true if the direction of movement must be interrupted, false otherwise
     */
    public boolean boardEdge(Tile tile, int direction) {
        boolean edge = false;
        if(tile.getRow() == Board.MIN_ROW && (
                (direction == Tile.UP_LEFT) ||
                (direction == Tile.UP) ||
                (direction == Tile.UP_RIGHT))) {
            edge = true;
        }
        
        if(tile.getRow() == Board.MAX_ROW && (
                (direction == Tile.DOWN_LEFT) ||
                (direction == Tile.DOWN) ||
                (direction == Tile.DOWN_RIGHT))) {
            edge = true;
        }
        
        if(tile.getCol() == Board.MIN_COL && (
                (direction == Tile.UP_LEFT) ||
                (direction == Tile.LEFT) ||
                (direction == Tile.DOWN_LEFT))) {
            edge = true;
        }
        
        if(tile.getCol() == Board.MAX_COL && (
                (direction == Tile.UP_RIGHT) ||
                (direction == Tile.RIGHT) ||
                (direction == Tile.DOWN_RIGHT))) {
            edge = true;
        }
        
        return edge;
    }
    
    /**
     * returns the next tile in the intended direction of travel;
     * 
     * THIS METHOD IS RESPONSIBLE FOR CONVERSION OF INTEGER DIRECTION INTO
     * MATRIX TRAVERSAL; ANY CHANGE IN DIRECTIONAL SCHEME MUST BE MATCHED 
     * IN THIS METHOD
     * 
     * @param src the current tile
     * @param direction the direction of travel
     * @return the adjacent tile in the given direction, if any
     */
    public Tile getTileInDirection(Tile src, int direction) {
        if(boardEdge(src, direction)) {
            return null;
        }
        
        Tile next = null;
        
        /*
         * crucially: moving UP or LEFT incurs a NEGATIVE HOP
         *            moving DOWN or RIGHT incurs a POSITIVE HOP
         */
        switch(direction) {
            case 1: next = getTileAt(src.getRow() - HOP, src.getCol()); // up
                    break;
            case 2: next = getTileAt(src.getRow() - HOP, src.getCol() + HOP); // up-right
                    break;
            case 3: next = getTileAt(src.getRow(), src.getCol() + HOP); // right
                    break;
            case 4: next = getTileAt(src.getRow() + HOP, src.getCol() + HOP); //down-right
                    break;
            case 5: next = getTileAt(src.getRow() + HOP, src.getCol()); // down
                    break;
            case 6: next = getTileAt(src.getRow() + HOP, src.getCol() - HOP); // down-left
                    break;
            case 7: next = getTileAt(src.getRow(), src.getCol() - HOP); // left
                    break;
            case 8: next = getTileAt(src.getRow() - HOP, src.getCol() - HOP); // up-left
                    break;
        }
        
        return next;
    }
    
    /**
     * prints a representation of the board's tile arrangement to the console
     */
    public void printBoard() {
        for(int row = 0; row < board[0].length; row++) {
            for(int col = 0; col < board[1].length; col++) {
                System.out.print(board[row][col].toString());
            }
            System.out.println();
        }
    }
    
    /**
     * for debug purposes mainly
     */
    public void revealAll() {
        for(int row = MIN_ROW; row <= board[0].length; row++) {
            for(int col = MIN_COL; col <= board[1].length; col++) {
                //System.out.println("reveal tile at array indices " + row + " " + col);
                this.board[row][col].reveal();
            }
        }
    }
    
    public StartTile getPlayer1Start() {
        return this.p1Start;
    }
    
    public StartTile getPlayer2Start() {
        return this.p2Start;
    }
    
    public StartTile getPlayer3Start() {
        return this.p3Start;
    }
    
    public StartTile getPlayer4Start() {
        return this.p4Start;
    }
}
