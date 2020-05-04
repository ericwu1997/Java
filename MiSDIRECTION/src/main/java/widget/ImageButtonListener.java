package main.java.widget;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ImageButtonListener extends MouseAdapter {
	ImageButton button;

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
		button = (ImageButton) e.getSource();
		if(!button.isDisable())
			button.active();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
		button = (ImageButton) e.getSource();
		if(!button.isDisable())
			button.deactive();
	}
}
