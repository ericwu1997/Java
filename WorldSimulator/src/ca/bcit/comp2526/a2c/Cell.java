package ca.bcit.comp2526.a2c;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
/**
 * Cell.java.
 * 
 * @author Eric
 * @version 1.0
 */
public class Cell extends JLabel {

	private static final long serialVersionUID = 1L;
	private static final int CHANCE = 100;
	private Point position;
	private World world;
	private Species type;
	private Cell[] adjacentCell;
	private boolean moveStatus;
	/**
	 * Constructor.
	 * 
	 * @param world
	 *            world
	 * @param row
	 *            row
	 * @param column
	 *            column
	 */
	Cell(World world, int row, int column) {
		this.world = world;
		position = new Point(column, row);
		PerfectTile.addCellBorder(this);
		init();
	}
	/**
	 * initialize cell.
	 */
	public void init() {
		type = SpeciesGenerator.nextSpecies(this,
				RandomGenerator.nextNumber(CHANCE));
	}
	/**
	 * paint component on canvas.
	 * 
	 * @param g
	 *            Grphaics object
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		CellData.setProperties(g, type);
	}
	/**
	 * return cell location.
	 * 
	 * @return Point coordinate
	 */
	public Point getLocation() {
		return position;
	}
	/**
	 * return adjacent cell.
	 * 
	 * @return Cell adjacent cell relative to current cell
	 */
	public Cell[] getAdjacentCells() {
		adjacentCell = new Cell[8];
		int[][] offSet = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1},
				{0, 1}, {1, 1}};

		for (int i = 0; i < offSet.length; i++) {
			if (world.getCellAt(position.y + offSet[i][0],
					position.x + offSet[i][1]) != null) {
				adjacentCell[i] = world.getCellAt(position.y + offSet[i][0],
						position.x + offSet[i][1]);
			}
		}
		return adjacentCell;
	}
	/**
	 * check if cell is located at the right most.
	 * 
	 * @return boolean true or false
	 */
	public boolean frameRightBorder() {
		return position.x == world.getColumnCount() - 1;
	}

	/**
	 * check if cell is located at the left most.
	 * 
	 * @return boolean true or false
	 */
	public boolean frameBottomBorder() {
		return position.y == world.getRowCount() - 1;
	}

	/**
	 * return size of the map - world.
	 * 
	 * @return int size of the map
	 */
	public int worldSize() {
		return world.getColumnCount() * world.getRowCount();
	}

	/**
	 * get species type.
	 * 
	 * @return Species type
	 */
	public Species getSpecies() {
		return type;
	}

	/**
	 * set species type.
	 * 
	 * @param t
	 *            species type
	 */
	public void setSpecies(Species t) {
		type = t;
	}

	/**
	 * check if already moved during current turn.
	 * 
	 * @return boolean true or false
	 */
	public boolean checkMoveStatus() {
		return moveStatus;
	}

	/**
	 * update move status after moving.
	 * 
	 * @param status
	 *            move status
	 */
	public void updateMoveStatus(boolean status) {
		moveStatus = status;
	}

	/**
	 * remove current species data of a cell.
	 */
	public void remove() {
		this.type = new Empty();
	}
}
