package main.java.composition;

import java.awt.Color;

import main.java.component.DisplayToken;
import main.java.property.Properties;

public class DisplayPlayers {
	DisplayToken[] players;

	public DisplayPlayers() {
		players = new DisplayToken[Properties.TOKEN_NUMBER];
		players[0] = new DisplayToken(1, 1, Color.RED);
		players[1] = new DisplayToken(Properties.GRID_DIMENSION, 1, Color.GREEN);
		players[2] = new DisplayToken(Properties.GRID_DIMENSION, Properties.GRID_DIMENSION, Color.BLUE);
		players[3] = new DisplayToken(1, Properties.GRID_DIMENSION, Color.YELLOW);
	}

	public DisplayToken[] getPlayersArray() {
		return players;
	}

	public DisplayToken getToken(int i) {
		return players[i];
	}
}
