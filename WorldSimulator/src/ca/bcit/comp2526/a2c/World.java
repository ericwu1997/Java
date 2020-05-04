package ca.bcit.comp2526.a2c;

import java.io.Serializable;

import ca.bcit.comp2526.a2b.Exception.CouldNotAddException;
import ca.bcit.comp2526.a2b.LinkList.DoubleLinkList;

/**
 * World.java.
 * 
 * @author Eric
 * @version 1.0
 */
public class World implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rows;
	private int columns;
	private Cell[][] cellList;

	/**
	 * constructor.
	 * 
	 * @param rows
	 *            number of row
	 * @param columns
	 *            number of columns
	 */
	public World(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		init();
	}

	/**
	 * initialize.
	 */
	public void init() {
		cellList = new Cell[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cellList[i][j] = new Cell(this, i, j);
			}
		}
	}

	/**
	 * get cell location.
	 * 
	 * @param row
	 *            x coordinate
	 * @param column
	 *            y coordinate
	 * @return Cell cell
	 */
	public Cell getCellAt(int row, int column) {
		try {
			return cellList[row][column];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * take turn.
	 */
	public void takeTurn() {
		DoubleLinkList<Thread> actionList = new DoubleLinkList<Thread>();
		Thread currentCell;
		for (Cell[] row : cellList) {
			for (Cell cell : row) {
				cell.updateMoveStatus(false);
				if (!(cell.getSpecies() instanceof Empty)) {
					currentCell = new Thread(new Runnable() {
						public void run() {
							cell.getSpecies()
									.takeAction(cell.getAdjacentCells());
							return;
						}
					});
					try {
						actionList.addToFront(currentCell);
					} catch (CouldNotAddException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		actionList.forEach(x -> x.start());
		actionList.forEach(x -> {
			try {
				x.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * get number of rows.
	 * 
	 * @return int number of rows.
	 */
	public int getRowCount() {
		return rows;
	}

	/**
	 * get number of columns.
	 * 
	 * @return int number of columns
	 */
	public int getColumnCount() {
		return columns;
	}
	
}
