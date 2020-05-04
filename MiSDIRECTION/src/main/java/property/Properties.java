package main.java.property;

import java.io.File;

public class Properties {
	public static final int TILE_SRC_WIDTH = 140;
	public static final int TILE_SRC_HEIGHT = 140;
	public static final double TILE_SCALE_RATIO = 0.5;
	public static final int TILE_WIDTH = 50;
	public static final int TILE_HEIGHT = 50;
	public static final int TILE_FLIP_GAP = 10;
	public static final int TILE_FLIP_RATE = 40;

	public static final int TOKEN_SRC_WIDTH = 100;
	public static final int TOKEN_SRC_HEIGHT = 140;
	public static final double TOKEN_SCALE_RATIO = 0.75;
	public static final int TOKEN_WIDTH = 70;
	public static final int TOKEN_HEIGHT = 70;
	public static final int TOKEN_NUMBER = 4;
	public static final int TOKEN_FRAME_NUM = 7;

	public static final int GRID_DIMENSION = 15;
	public static final int BOARD_WIDTH = TILE_WIDTH * GRID_DIMENSION + 2 * TILE_FLIP_GAP;
	public static final int BOARD_HEIGHT = TILE_HEIGHT * GRID_DIMENSION + 2 * TILE_FLIP_GAP;

	public static final int BOARD_FRAME_PADDING = 40;

	public static final int SIDEBAR_WIDTH = 150;
	public static final int SIDEBAR_HEIGHT = BOARD_HEIGHT + 2 * BOARD_FRAME_PADDING;

	public static final int GAME_FRAME_WIDTH = BOARD_WIDTH + 2 * BOARD_FRAME_PADDING + SIDEBAR_WIDTH;
	public static final int GAME_FRAME_HEIGHT = BOARD_HEIGHT + 2 * BOARD_FRAME_PADDING + 20;

	public static final int MAX_JUMP_HEIGHT = 30;
	public static final int DEFAULT_JUMP_SPEED = 5;
	
	public static final String IMAGE_PATH = new File("").getAbsolutePath().concat("\\src\\Resource\\");
}
