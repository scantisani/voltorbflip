import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelButton extends JButton implements ActionListener {

	private final int WIDTH = 60;
	private final int HEIGHT = 26;

	private Image image;
	private int level;

	public LevelButton() {
		image = (new ImageIcon("images/Level.png")).getImage();
		addActionListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, this);
		Image number = (new ImageIcon("images/Level" + level + ".png")).getImage();
		g.drawImage(number, 34, 7, 11, 11, this);
	}

	public void actionPerformed(ActionEvent e) {
		//make level selector
		System.out.println("Level button clicked");
	}

	public void levelUp() {
		level++;
		repaint();
	}

	public void setLevel(int level) {
		this.level = level;
		repaint();
	}

	public int getLevel() {
		return level;
	}
}
