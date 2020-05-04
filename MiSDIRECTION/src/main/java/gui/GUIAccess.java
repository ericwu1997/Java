package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import main.java.component.DisplayTile;
import main.java.composition.DisplayBoard;
import main.java.control.GameController;
import main.java.control.State;
import main.java.frame.GameFrame;
import main.java.frame.SideBarFrame;
import main.java.game.Token;
import main.java.game.tiles.Tile;
import main.java.property.Properties;
import main.java.widget.ImageButton;

public class GUIAccess {

	private GameController gc;
	private GameFrame frame;
	private Tile selected;

	public GUIAccess(GameController gc, GameFrame gf) {
		this.gc = gc;
		this.frame = gf;
		this.selected = null;

		frame.getSideBarFrame().getDisplayDiskContainer().activateDisplayDiskAt(gc.getActivePlayer().getPlayerNumber());

		attachRollListener();
		attachEndTurnListener();
		attachTileClickListener();
		attachRotateLeftListener();
		attachRotateRightListener();
		attachTeleportListener();
	}

	private void attachRotateLeftListener() {
		JButton button = frame.getSideBarFrame().getRotateLeftButton();
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Rotate Left Called");
				rotationDirectionClicked(false);
			}
		});
	}

	private void attachRotateRightListener() {
		JButton button = frame.getSideBarFrame().getRotateRightButton();
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Rotate Right Called");
				rotationDirectionClicked(true);
			}
		});
	}

	private void attachTeleportListener() {
		JButton button = frame.getSideBarFrame().getTeleportButton();
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Teleport Called");
				teleportButtonClicked();
			}
		});
	}

	private void attachTileClickListener() {
		DisplayBoard board = frame.getBoardFrame().getBoard();
		DisplayTile[][] tileArray = board.getTileArray();
		for (int i = 0; i < Properties.GRID_DIMENSION; i++) {
			for (int j = 0; j < Properties.GRID_DIMENSION; j++) {
				DisplayTile tile = tileArray[i][j];
				int row = i + 1;
				int col = j + 1;
				tile.getPanel().addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mousePressed(e);

						Tile selected_tile = gc.getBoard().getTileAt(col, row);
						setSelected(selected_tile);
						board.deselectLastTile();
						board.setLastSelectedTile(tile);

						if (gc.getGameState() == State.TELEPORTING) {
							teleportDestinationClicked(selected_tile);
						}

					}
				});
			}
		}
	}

	private void attachRollListener() {
		ImageButton btn = this.frame.getSideBarFrame().getRollButton();
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rollButtonClicked();
			}
		};
		btn.attachListener(action);
	}

	private void attachEndTurnListener() {
		DisplayBoard board = frame.getBoardFrame().getBoard();
		ImageButton end_btn = this.frame.getSideBarFrame().getEndTurnButton();
		ImageButton roll_btn = this.frame.getSideBarFrame().getRollButton();
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				endButtonClicked();
				roll_btn.enable();
				board.deselectLastTile();
			}
		};
		end_btn.attachListener(action);
	}

	/**
	 * call this method when the roll button is clicked
	 */
	public void rollButtonClicked() {
		if (gc.getGameState() == State.TURN_START) {
			if (!gc.getActiveToken().rotateAtStart())
				frame.getSideBarFrame().getRollButton().disable();
			gc.rollButtonClicked();
		}
		// do gui stuff related to showing the die roll
		// movement should already be handled from within the controller
	}

	/**
	 * call this method when the player clicks a tile to rotate
	 * 
	 * @param clicked
	 */
	public void tileClickedToRotate(Tile clicked) {
		State current = gc.getGameState();

		if ((current == State.TURN_START || current == State.TURN_END) && !gc.rotated()) {
			setSelected(clicked);
			gc.setGameState(State.ROTATING);
		}

		// do gui stuff related to highlighting the tile and showing rotation buttons
	}

	/**
	 * call this method when the player clicks/chooses the desired direction to
	 * rotate the selected tile
	 * 
	 * @param clockwise
	 *            true if the tile needs to rotate clockwise, false if
	 *            counterclockwise
	 */
	public void rotationDirectionClicked(boolean clockwise) {
		Token active = gc.getActiveToken();
		State current = gc.getGameState();
		SideBarFrame sidebarFrame = frame.getSideBarFrame();
		ImageButton button = (clockwise ? sidebarFrame.getRotateRightButton() : sidebarFrame.getRotateLeftButton());

		if (this.selected != null && !gc.rotated() && ((current == State.TURN_START && active.rotateAtStart())
				|| current == State.TURN_END && active.rotateAtEnd())) {
			gc.setGameState(State.ROTATING);
			frame.getSideBarFrame().getDisplayDiskContainer().flipDiskAt(gc.getActivePlayer().getPlayerNumber());
			gc.tileClickedToRotate(getSelected(), clockwise);
			DisplayTile displayTile = frame.getBoardFrame().getBoard().getTile(this.selected.getRow(),
					this.selected.getCol());
			displayTile.rotate(selected, clockwise);
			if (current == State.TURN_START)
				button.disable();
			if (current == State.TURN_END)
				frame.getSideBarFrame().getRollButton().enable();
		}
	}

	public void tileClickedToTeleport(Tile clicked) {
		if (gc.getGameState() == State.TURN_START) {
			setSelected(clicked);
			gc.setGameState(State.TELEPORTING);
		}

		// do gui stuff to highlight the origin tile and any eligible destination tiles
	}

	public void teleportButtonClicked() {
		if (gc.getGameState() == State.TURN_START) {
			gc.setGameState(State.TELEPORTING);
		}
	}

	public void teleportDestinationClicked(Tile dest) {
		System.out.println("Teleport invoked");
		if (gc.getGameState() == State.TELEPORTING && dest != null) {
			System.out.println("Attempting to teleport");
			Token t = gc.getActiveToken();
			gc.teleportToken(t, getSelected(), dest);
			frame.getToken(t.getColour()).teleport(dest.getRow(), dest.getCol());
		}

		// teleport the token on the gui
	}

	public void endButtonClicked() {
		if (gc.getGameState() == State.TURN_END) {
			frame.getSideBarFrame().enableAllButton();
			gc.endButtonClicked();
		}

		// do anything gui related to switching the active player
	}

	/**
	 * @return the selected
	 */
	public Tile getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Tile selected) {
		this.selected = selected;
	}

}
