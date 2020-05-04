package main.java.widget;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ImageButtonGroup extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageButton[] group;
	int last_selected_index = -1;

	public ImageButtonGroup(ImageButton... buttonsList) {
		setOpaque(false);
		setLayout(new GridLayout(buttonsList.length, 1));
		group = new ImageButton[buttonsList.length];
		int index = 0;
		for (ImageButton button : buttonsList) {
			group[index] = button;
			add(button);
			index++;
		}
		for (int i = 0; i < group.length; i++) {
			int selected_index = i;
			group[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (last_selected_index != -1) {
						group[last_selected_index].enable();
					}
					group[selected_index].disable();
					last_selected_index = selected_index;
				}
			});
		}
	}

	public void enableAll() {
		last_selected_index = -1;
		for(int i = 0; i < group.length; i++) {
			group[i].enable();
		}
	}
}
