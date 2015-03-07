import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quit extends JButton implements ActionListener {

	private final int WIDTH = 60;
	private final int HEIGHT = 26;

	private Image image;
	private Board board;

	public Quit(Board board) {
		image = (new ImageIcon("images/Quit.png")).getImage();
		addActionListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		this.board = board;
	}

	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, this);
	}

	public void actionPerformed(ActionEvent e) {
		board.quitGame();
	}
}
