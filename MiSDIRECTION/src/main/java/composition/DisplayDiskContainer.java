package main.java.composition;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import main.java.component.DisplayDisk;

public class DisplayDiskContainer extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color active;
	DisplayDisk disk_list[];

	public DisplayDiskContainer(DisplayDisk... displayDisks) {
		setOpaque(false);
		setLayout(new GridLayout(displayDisks.length, 1));
		disk_list = new DisplayDisk[displayDisks.length];
		int i = 0;
		for (DisplayDisk disk : displayDisks) {
			disk_list[i] = disk;
			add(disk);
			i++;
		}
	}

	public void init() {
		disk_list = new DisplayDisk[4];
	}

	public DisplayDisk[] getDisplayDiskList() {
		return disk_list;
	}

	public DisplayDisk getDisplayDisk(Color color) {
		for (DisplayDisk disk : disk_list) {
			if (disk.color == color) {
				return disk;
			}
		}
		return disk_list[0];
	}

	public void activateDisplayDiskAt(int index) {
		disk_list[index - 1].activate();
	}

	public void deactivateDisplayDiskAt(int index) {
		disk_list[index - 1].deactivate();
	}

	private void add(DisplayDisk displayDisk) {
		add(displayDisk.getPanel());
	}
	
	public void flipDiskAt(int index) {
		disk_list[index - 1].flip();
	}
}
