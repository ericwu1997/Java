package main.java.control;

import main.java.game.*;
import main.java.game.tiles.*;
import main.java.property.Properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.java.component.DisplayToken;
import main.java.composition.DisplayBoard;
import main.java.frame.GameFrame;

public class GameController {

	private Random rand;
	private LinkedList<Player> players;
	private Player activePlayer;
	private Board board;
	private State gameState;
	private boolean rotated;
	private int firstDieRoll;
	private GameFrame frame;
	private int step_token_delay = 0;

	private Map<Player, StartTile> startTiles;
	private Map<Player, Token> tokens;
	private Map<Player, List<TeleportTile>> revealedTeleports;

	/**
	 * default constructor
	 */
	public GameController() {
		this.rand = new Random(System.nanoTime());

		this.players = new LinkedList<Player>();
		this.board = new Board();
		this.frame = new GameFrame();

		this.startTiles = new HashMap<Player, StartTile>();
		this.tokens = new HashMap<Player, Token>();
		this.revealedTeleports = new HashMap<Player, List<TeleportTile>>();

		initPlayers();
		initStartTiles();
		initTokens();
		initTeleports();

		this.firstDieRoll = determineStartingPlayer();
		setGameState(State.TURN_START);
		setRotated(false);

		setDisplayTile();

		this.activePlayer = players.peek();
	}

	private void setDisplayTile() {
		DisplayBoard board = frame.getBoardFrame().getBoard();
		for (int row = 1; row <= Properties.GRID_DIMENSION; row++) {
			for (int col = 1; col <= Properties.GRID_DIMENSION; col++) {
				Tile tile = this.board.getTileAt(row, col);
				board.setTile(tile);
			}
		}
		board.renderBoard();
	}

	/**
	 * 
	 */
	public void initPlayers() {
		for (int n = 1; n <= Board.NUM_PLAYERS; n++) {
			this.players.add(new Player(n));
		}
	}

	/**
	 * partially simulates the process of rolling for first turn and using the
	 * lowest result by internally decoupling the randomness of those events; the
	 * roll is still the lowest roll among four rolls of a die, but it assigns that
	 * roll to a random player to take on their first turn, and designates that
	 * player as the game's first active player
	 * 
	 * @return the first die roll to use for movement in the game
	 */
	public int determineStartingPlayer() {
		// determine first die roll to use based on lowest number rolled on four dice
		int startingRoll = rollDie();
		for (int n = 0; n < this.players.size(); n++) {
			startingRoll = Math.min(startingRoll, rollDie());
		}

		// cycle the active player a random number of times to determine starting player
		for (int i = 0; i < this.rand.nextInt(this.players.size()); i++) {
			cycleActivePlayer();
		}

		return startingRoll;
	}

	/**
	 * 
	 */
	public void initStartTiles() {
		StartTile startTile = null;
		for (Player p : this.players) {
			switch (p.getPlayerNumber()) {
			case 1:
				startTile = this.board.getPlayer1Start();
				break;
			case 2:
				startTile = this.board.getPlayer2Start();
				break;
			case 3:
				startTile = this.board.getPlayer3Start();
				break;
			case 4:
				startTile = this.board.getPlayer4Start();
				break;
			default:
				startTile = null;
				break;
			}
			if (startTile != null) {
				this.startTiles.put(p, startTile);
				System.out.println("adding start tile for player " + p.getPlayerNumber() + " direction: "
						+ startTile.getDirection() + " " + startTile.getCurrentDirection());
			}
		}
	}

	/**
	 * initializes player tokens, associates them with appropriate players, and
	 * places them on start tiles
	 */
	public void initTokens() {
		Token t;
		for (Player p : this.players) {
			int pNum = p.getPlayerNumber();
			t = new Token(pNum);
			tokens.put(p, t);
			switch (p.getPlayerNumber()) {
			case 1:
				placeToken(t, Board.MIN_ROW, Board.MIN_COL);
				break;
			case 2:
				placeToken(t, Board.MIN_ROW, Board.MAX_COL);
				break;
			case 3:
				placeToken(t, Board.MAX_ROW, Board.MAX_COL);
				break;
			case 4:
				placeToken(t, Board.MAX_ROW, Board.MIN_COL);
				break;
			default:
				System.out.println("error in player token initialization; player number found: " + p.getPlayerNumber());
				;
				break;
			}
			System.out.println("player " + p.getPlayerNumber() + " gets a token, placed at row " + t.getRow()
					+ ", column " + t.getCol());
		}
	}

	/**
	 * initializes lists of revealed teleport tiles for each player
	 */
	public void initTeleports() {
		for (Player p : this.players) {
			LinkedList<TeleportTile> revealed = new LinkedList<TeleportTile>();
			this.revealedTeleports.put(p, revealed);
		}
	}

	/**
	 * method to be called when the GUI roll button is clicked; will advance game
	 * state either to the end of the turn or the next player's turn based on status
	 * of that player's rotation disk
	 */
	public void rollButtonClicked() {
		this.setGameState(State.MOVING);

		Player active = this.getActivePlayer();
		Token token = this.tokens.get(active);
		int dieRoll = rollDie();

		if (this.firstDieRoll != 0) {
			dieRoll = firstDieRoll;
			this.firstDieRoll = 0;
		}

		moveToken(token, dieRoll);
		if (token.rotateAtEnd()) {
			this.setGameState(State.TURN_END);
		} else {
			nextPlayer();
		}
	}

	/**
	 * method to be called when a tile is clicked for rotation in the GUI
	 * 
	 * @param clicked
	 * @param clockwise
	 *            the direction to rotate the tile, true if CW, false for CCW
	 */
	public void tileClickedToRotate(Tile clicked, boolean clockwise) {
		this.setGameState(State.ROTATING);
		Token activeToken = this.tokens.get(this.activePlayer);

		rotateTile(clicked, clockwise);

		activeToken.flipDisc(); // this line INVERTS THE FOLLOWING LOGIC; but is required for AI implementation
								// to go smoothly

		if (!activeToken.rotateAtEnd()) { // token WAS dark side up prior to the flip
			nextPlayer();
		} else {
			this.setGameState(State.TURN_START);
		}
	}

	/**
	 * method to "manually" advance the turn, in case player can rotate a tile after
	 * moving but chooses not to
	 */
	public void endButtonClicked() {
		nextPlayer();
	}

	/**
	 * 
	 * @return a random number between 1 and 6
	 */
	public int rollDie() {
		return (rand.nextInt(5) + 1);
	}

	/**
	 * puts the current player at the end of the playerlist, making the new first
	 * player the current active player
	 */
	public void cycleActivePlayer() {
		Player last = this.players.pollFirst();
		this.players.offerLast(last);

		// assign first player as active player
		this.activePlayer = this.players.peek();
	}

	/**
	 * method to be called whenever player turn changes to allow for AI to take
	 * control if applicable
	 */
	public void nextPlayer() {
		frame.getSideBarFrame().getDisplayDiskContainer().deactivateDisplayDiskAt(getActivePlayer().getPlayerNumber());
		cycleActivePlayer();
		this.rotated = false;
		if (this.activePlayer.skipNext()) {
			this.activePlayer.setSkipNext(false);
			cycleActivePlayer();
		}
		frame.getSideBarFrame().getDisplayDiskContainer().activateDisplayDiskAt(getActivePlayer().getPlayerNumber());
		this.setGameState(State.TURN_START);
		System.out.println("Next player called");
		if (this.activePlayer.isAI()) {
			// run AI stuff
		}
	}

	/**
	 * pass-through method to call the tile's rotate method
	 * 
	 * @param row
	 *            of the tile to rotate
	 * @param col
	 *            of the tile to rotate
	 * @param clockwise
	 *            true if rotating clockwise, false if counter-clockwise
	 */
	public void rotateTile(int row, int col, boolean clockwise) {
		Tile toRotate = this.board.getTileAt(row, col);
		toRotate.rotate(clockwise);
		setRotated(true);
	}

	/**
	 * much simpler method than above, previous method may be deprecate-worthy
	 * 
	 * @param toRotate
	 * @param clockwise
	 */
	public void rotateTile(Tile toRotate, boolean clockwise) {
		toRotate.rotate(clockwise);
		setRotated(true);
	}

	/**
	 * pass-through method to clear a given tile's token reference
	 * 
	 * @param src
	 *            the tile to clear token reference from
	 */
	public void clearTile(Tile src) {
		src.setPresentToken(null);
	}

	/**
	 * overloaded method that automatically calls getTileAt to remove tokens from a
	 * tile given its row/col coordinates
	 * 
	 * @param row
	 *            of the tile to be cleared
	 * @param col
	 *            of the tile to be cleared
	 */
	public void clearTile(int row, int col) {
		Tile src = this.board.getTileAt(row, col);
		clearTile(src);
	}

	/**
	 * 
	 * @param token
	 * @param row
	 * @param column
	 */
	public void placeToken(Token token, int row, int column) {
		// reset previous tile's token reference to null
		/*
		 * DisplayTile src = this.board.getTileAt(token.getRow(), token.getCol());
		 * src.setPresentToken(null);
		 */

		// set token's position via its own parameters
		token.setRow(row);
		token.setCol(column);

		// set tile reference to the token being placed
		Tile dest = this.board.getTileAt(row, column);
		dest.setPresentToken(token);
	}

	/**
	 * overloaded method for ease of use
	 * 
	 * @param token
	 * @param dest
	 */
	public void placeToken(Token token, Tile dest) {
		token.setRowCol(dest.getRow(), dest.getCol());
		dest.setPresentToken(token);
	}

	/**
	 * returns a Player corresponding to a selected token
	 * 
	 * @param token
	 *            the token that a player is desired for
	 * @return the Player object that corresponds to token
	 */
	public Player getPlayerForToken(Token token) {
		Player pReturn = null;

		for (Player p : tokens.keySet()) {
			if (p.getPlayerNumber() == token.getColour()) {
				pReturn = p;
			}
		}

		return pReturn;
	}

	/**
	 * puts the indicated token back on its player's start tile
	 * 
	 * @param token
	 */
	public void returnToStart(Token token) {
		Player p = getPlayerForToken(token);
		Tile pStart = this.startTiles.get(p);
		placeToken(token, pStart);
	}

	/**
	 * 
	 * @param token
	 * @param direction
	 * @param distance
	 */
	public void moveToken(Token token, int distance) {
		// setGameState(State.MOVING);

		Tile src = getCurrentTile(token);
		int currentDirection = src.getCurrentDirection();
		int remDistance = distance;

		Player current = getPlayerForToken(token);
		if (current.dirReversed()) {
			currentDirection = reversedDirection(currentDirection);
			current.setDirReversed(false);
		}

		while (remDistance > 0 && currentDirection != 0) {
			currentDirection = stepToken(token, currentDirection, remDistance);
			remDistance--;
		}

		Tile dest = getCurrentTile(token);
		if (!dest.isRevealed()) {
			dest.reveal();
			frame.getTile(dest.getRow(), dest.getCol()).flip(distance * 400);
			triggerTile(token, dest);
		}
		step_token_delay = 0;
		System.out.println("--------");
		// setGameState(State.TURN_END);
	}

	/**
	 * this method is for moving an individual step of a token's movement and will
	 * be called multiple times during moveTokens; can also be used to call
	 * animation "hops" if desired
	 * 
	 * @param token
	 * @param direction
	 * @return the continued direction of movement of the token
	 */
	public int stepToken(Token token, int direction, int remDistance) {
		Tile src = getCurrentTile(token);
		Player p = getPlayerForToken(token);
		DisplayToken dt = this.frame.getToken(p.getPlayerNumber());
		clearTile(src);
		Tile dest = null;
		int newDirection = direction;

		if (!this.board.boardEdge(src, direction)) { // next tile is on the board
			dest = this.board.getTileInDirection(src, direction);
		} else { // the token would move off the board

			src.reveal();
			frame.getTile(src.getRow(), src.getCol()).flip(400);

			newDirection = src.getCurrentDirection(); // get new direction (just in case someone was weird and
			// rotated an unrevealed tile)

			if (!this.board.boardEdge(src, newDirection)) { // check if the revealed direction allows the token to be
				// redirected
				dest = this.board.getTileInDirection(src, newDirection);
				System.out.println("dest tile: row " + dest.getRow() + " col:" + dest.getCol());
			} else {
				returnToStart(token); // return token to start if necessary
				newDirection = 0;
				StartTile st = this.startTiles.get(p);
				dt.teleport(st.getRow(), st.getCol());
			}
		}

		// extra null guard, plus checking that the destination is vacant

		if (dest != null) {
			if (dest.getPresentToken() == null) {
				placeToken(token, dest);

				// add extra control to see if the remaining distance is 1, then delay animation

				if (!dest.isRevealed()) {
					if (remDistance > 1 && this.board.boardEdge(dest, newDirection)) {
						step_token_delay += 700;
					} else if (remDistance == 1) {
						step_token_delay += 2000;
					}
				}

				dt.takeStep(step_token_delay, newDirection);
			} else if (remDistance > 1) {
				token.setRowCol(dest.getRow(), dest.getCol());

				if (this.board.boardEdge(dest, newDirection)) {
					step_token_delay = 1000;
				}

				dt.takeStep(step_token_delay, newDirection);

			} else {
				System.out.println("Return to Start");
				returnToStart(token); // return to start if the token would end its move on another token
				newDirection = 0;
				StartTile st = this.startTiles.get(p);
				newDirection = 0;
				dt.teleport(st.getRow(), st.getCol());
			}
		}

		/*
		 * System.out.
		 * println("stepToken failed, either destination does not exist; or is occupued"
		 * );
		 */
		return newDirection;
	}

	/**
	 * @param active
	 *            - the token of the active player
	 * @return the tile presently beneath the active token
	 */
	public Tile getCurrentTile(Token active) {
		return this.board.getTileAt(active.getRow(), active.getCol());
	}

	/**
	 * does anything special that needs to be done for a non-standard tile as it's
	 * revealed
	 * 
	 * @param activeToken
	 *            the currently active token
	 * @param activeTile
	 *            the tile activeToken landed on
	 */
	public void triggerTile(Token activeToken, Tile activeTile) {
		activeTile.reveal(); // should be unnecessary but being safe
		int tileNum = activeTile.getPlayerNumber();

		Player p = getPlayerForToken(activeToken);
		Player affected = getPlayerByNumber(tileNum);

		if (activeTile instanceof GoalTile) {
			// player of ActiveToken wins game/gets pot
			// this is basically 100% placeholder now
		}
		if (activeTile instanceof FlipTile) {
			// active player flips their rotation token
			activeToken.flipDisc();
			frame.getSideBarFrame().getDisplayDiskContainer().flipDiskAt(getActivePlayer().getPlayerNumber());
		}
		if (activeTile instanceof ReverseTile) {
			// indicated player sets reverse flag to true

			affected.setDirReversed(true);
		}
		if (activeTile instanceof SkipTile) {
			// indicated player sets skip flag to true
			affected.setSkipNext(true);
		}
		if (activeTile instanceof RollAgainTile) {
			// active player takes another turn
			this.setGameState(State.TURN_START);
		}
		if (activeTile instanceof TeleportTile) {
			// add the revealed tile to the appropriate player's teleport list
			List<TeleportTile> teleports = this.revealedTeleports.get(affected);
			if (teleports != null) {
				teleports.add((TeleportTile) activeTile);
			}
		}
	}

	/**
	 * this method should have appropriate safeguards to prevent illegal
	 * teleportation; it may be insufficient from a GUI perspective for displaying
	 * teleportation options
	 * 
	 * @param activeToken
	 *            the currently active token
	 * @param currentTile
	 *            the tile the token is present on
	 * @param destination
	 *            the desired end location of the teleport
	 */
	public void teleportToken(Token activeToken, Tile currentTile, Tile destination) {
		Player activePlayer = getPlayerForToken(activeToken);
		TeleportTile origin = null;
		List<TeleportTile> teleports = this.revealedTeleports.get(activePlayer);
		if (currentTile instanceof TeleportTile) {
			origin = (TeleportTile) currentTile;
		}

		if (origin != null && activePlayer.getPlayerNumber() == origin.getPlayerNumber()
				&& teleports.contains(destination) && destination.getPresentToken() == null) {
			clearTile(origin);
			placeToken(activeToken, destination);
		}
	}

	/**
	 * should return an arraylist of active teleport tiles, using arraylist for
	 * memory efficiency; this method is pretty much entirely for the GUI to
	 * retrieve tiles to make them clickable
	 * 
	 * @return the active player's revealed teleport tiles as an arraylist
	 */
	public ArrayList<TeleportTile> getActiveTeleports() {
		ArrayList<TeleportTile> active = new ArrayList<TeleportTile>(this.revealedTeleports.get(this.activePlayer));

		return active;
	}

	/**
	 * getter for game state
	 * 
	 * @return current game state
	 */
	public State getGameState() {
		return this.gameState;
	}

	public void setGameState(State state) {
		this.gameState = state;
	}

	public boolean rotated() {
		return this.rotated;
	}

	public void setRotated(boolean rotated) {
		this.rotated = rotated;
	}

	/**
	 * returns a player by their identifying number
	 * 
	 * @param playerNum
	 *            the number of the Player object desired
	 * @return the corresponding Player object
	 */
	public Player getPlayerByNumber(int playerNum) {
		Player numbered = null;

		for (Player p : this.players) {
			if (p.getPlayerNumber() == playerNum) {
				numbered = p;
			}
		}

		return numbered;
	}

	public Board getBoard() {
		return this.board;
	}

	public Player getActivePlayer() {
		return this.activePlayer;
	}

	public Token getActiveToken() {
		return this.tokens.get(getActivePlayer());
	}

	public GameFrame getFrame() {
		return this.frame;
	}

	/**
	 * returns the opposite direction for players who's direction has been reversed
	 * 
	 * @param direction
	 * @return
	 */
	public int reversedDirection(int direction) {
		int reverse = direction + Board.REV_DIR;

		if (reverse > Tile.DIR_MAX) {
			reverse %= Tile.DIR_MAX;
		}

		return reverse;
	}

	// BELOW THIS POINT RESERVED FOR AI METHODS

	/**
	 *
	 * @param current
	 */
	private void takeTurn() {
		Player me = this.activePlayer;
		Token myToken = tokens.get(me);
		Tile initialTile = getCurrentTile(myToken);

		int distanceFromGoal = checkDistanceFromGoal(initialTile);
		int possibleDist = Integer.MAX_VALUE;

		if (myToken.rotateAtStart()) {
			// allow tile rotation here
		}

		if (initialTile instanceof TeleportTile) {
			/*
			 * check if tile is correct player colour and if there are other teleport tiles
			 * available, then do teleport stuff
			 */
			ArrayList<TeleportTile> ports = getActiveTeleports();
			Tile best = initialTile;
			int shortest = distanceFromGoal;
			for (Tile t : ports) {
				possibleDist = checkDistanceFromGoal(t);
				if (possibleDist < shortest) {
					best = t;
					shortest = possibleDist;
				}
			}

			if (!best.equals(initialTile)) {
				// teleport to best
			}

		} else { // do regular directional stuff here

		}

		if (myToken.rotateAtEnd() && !this.rotated) { // second part stops double rotation attempts
			// rotate at end an
		}
	}

	private int checkDistanceFromGoal(Tile origin) {
		int rowDistance = Math.abs(origin.getRow() - Board.CENTER_ROW);
		int colDistance = Math.abs(origin.getCol() - Board.CENTER_COL);
		int goalDistance = rowDistance + colDistance;

		return goalDistance;
	}

	private int checkDistanceFromGoal(int row, int col) {
		int rowDistance = Math.abs(row - Board.CENTER_ROW);
		int colDistance = Math.abs(col - Board.CENTER_COL);
		int goalDistance = rowDistance + colDistance;

		return goalDistance;
	}

	private int checkEffectiveDistanceToGoal(Tile origin) {
		int direction = origin.getCurrentDirection();

		int rowDistance = 4;
		int colDistance = 4;

		if (direction == 8 || direction == 1 || direction == 2) {
			rowDistance *= -1;
		}

		if (direction == 7 || direction == 3) {
			rowDistance = 0;
		}

		if (direction == 6 || direction == 7 || direction == 8) {
			colDistance *= -1;
		}

		if (direction == 1 || direction == 5) {
			colDistance = 0;
		}

		int targetRow = origin.getRow() + rowDistance;

		int targetCol = origin.getCol() + colDistance;

		return checkDistanceFromGoal(targetRow, targetCol);
	}
}