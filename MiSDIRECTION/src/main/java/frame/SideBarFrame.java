package main.java.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.component.DisplayDisk;
import main.java.composition.DisplayDiskContainer;
import main.java.manager.ResourceManager;
import main.java.property.Properties;
import main.java.widget.ImageButton;
import main.java.widget.ImageButtonGroup;

public class SideBarFrame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel actionButtonContainer;
	JPanel turnButtonContainer;

	ImageButtonGroup button_group;
	ImageButton rotate_left;
	ImageButton rotate_right;
	ImageButton teleport;

	DisplayDiskContainer diskContainer;
	DisplayDisk player1;
	DisplayDisk player2;
	DisplayDisk player3;
	DisplayDisk player4;

	ImageButton roll;
	ImageButton endTurn;

	BufferedImage background;

	SideBarFrame() {
		setBackground(new Color(18, 116, 0));
		setPreferredSize(new Dimension(Properties.SIDEBAR_WIDTH, Properties.SIDEBAR_HEIGHT));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		background = ResourceManager.getInstance().grabBufferedImg("SideBarBackground.png");

		actionButtonContainer = new JPanel();
		turnButtonContainer = new JPanel();

		actionButtonContainer.setOpaque(false);
		actionButtonContainer.setLayout(new GridLayout(5, 1));

		turnButtonContainer.setOpaque(false);
		turnButtonContainer.setLayout(new GridLayout(2, 1));

		rotate_left = new ImageButton("Rotate_Left.png", "Rotate_Left_Clicked.png");
		rotate_right = new ImageButton("Rotate_Right.png", "Rotate_Right_Clicked.png");
		teleport = new ImageButton("Teleport.png", "Teleport_Clicked.png");

		player1 = new DisplayDisk("Red_Black_White.png", "Red_White_Black.png", 171, 171, 60, 60, 7, Color.RED);
		player2 = new DisplayDisk("Green_Black_White.png", "Green_White_Black.png", 171, 171, 60, 60, 7, Color.GREEN);
		player3 = new DisplayDisk("Blue_Black_White.png", "Blue_White_Black.png", 171, 171, 60, 60, 7, Color.BLUE);
		player4 = new DisplayDisk("Yellow_Black_White.png", "Yellow_White_Black.png", 171, 171, 60, 60, 7,
				Color.YELLOW);

		diskContainer = new DisplayDiskContainer(player1, player2, player3, player4);

		roll = new ImageButton("Roll.png", "Roll_Clicked.png");
		endTurn = new ImageButton("End.png", "End_Clicked.png");

		actionButtonContainer.add(rotate_left);
		actionButtonContainer.add(rotate_right);
		actionButtonContainer.add(teleport);

		turnButtonContainer.add(roll);
		turnButtonContainer.add(endTurn);

		c.anchor = GridBagConstraints.PAGE_END; // BOTTOM OF SPACE
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		add(actionButtonContainer, c);

		c.gridy = 2;
		add(diskContainer, c);

		c.gridy = 3;
		add(turnButtonContainer, c);
	}

	public ImageButton getRollButton() {
		return roll;
	}

	public ImageButton getEndTurnButton() {
		return endTurn;
	}

	public ImageButton getRotateLeftButton() {
		return rotate_left;
	}

	public ImageButton getTeleportButton() {
		return teleport;
	}

	public ImageButton getRotateRightButton() {
		return rotate_right;
	}

	public ImageButtonGroup getButtonGroup() {
		return button_group;
	}

	public DisplayDiskContainer getDisplayDiskContainer() {
		return diskContainer;
	}

	public void enableAllButton() {
		roll.enable();
		rotate_left.enable();
		rotate_right.enable();
		teleport.enable();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
}
