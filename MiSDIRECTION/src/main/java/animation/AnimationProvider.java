package main.java.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class AnimationProvider {

	/**
	 * This method runs the animation base on the specify rate
	 * 
	 * @param animation
	 *            animation object to run
	 * @param rate
	 *            frame rate
	 */
	public static void anime(Animation animation, double rate) {
		// TODO Auto-generated method stub
		Timer t = new Timer(0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (animation.loopFrame()) {
				} else {
					((Timer) e.getSource()).stop();
				}
			}
		});
		t.setRepeats(true);
		t.setDelay((int) (rate));
		t.start();
	}

	/**
	 * This method runs the animation base on the specify rate
	 * 
	 * @param animation
	 *            animation object to run
	 * @param rate
	 *            frame rate
	 */
	public static void anime(Animation animation, double rate, boolean loop) {
		if (loop) {
			anime(animation, rate);
		} else {
			Timer t = new Timer(0, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (animation.loopOnce()) {
					} else {
						((Timer) e.getSource()).stop();
					}
				}
			});
			t.setRepeats(true);
			t.setDelay((int) (1000 / rate));
			t.start();
		}
	}
}
