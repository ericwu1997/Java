package main.java.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

public class AnimatableButtonListener extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		AnimatableButton button = (AnimatableButton) e.getSource();
		if (!button.checkRunningStatus() && button.activated) {
			button.setRunningStatus(true);
			Timer timer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(button.loopOnce()) {
					} else {
						((Timer) e.getSource()).stop();
						button.action();
						button.nextSpriteSheet();
						button.setRunningStatus(false);
					}
				}
			});
			timer.setRepeats(true);
			timer.setDelay(60);
			timer.start();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseExited(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseEntered(e);
	}
}