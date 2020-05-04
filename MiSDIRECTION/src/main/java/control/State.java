package main.java.control;

public enum State {
	TURN_START, // allowed GUI actions: rotate, roll die, teleport
	MOVING, // allowed GUI actions: none
	ROTATING, // allowed GUI actions: clockwise, counterclockwise, cancel
	TELEPORTING, // allowed GUI actions: select teleport destination, cancel
	TURN_END // allowed GUI actions: rotate, end turn
}
