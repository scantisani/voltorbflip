// Voltorb Flip
// Clone of the Voltorb Flip mini-game from Pokemon HeartGold/SoulSilver
// Scott Cantisani (mintdiscount)

// To-do:
// Level selector?
// Right click to set memos?

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.Dimension;

import java.awt.Color;

import java.awt.Image;
import javax.swing.ImageIcon;

public class VoltorbFlip extends JFrame {

	public VoltorbFlip() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Voltorb Flip");
		setIconImage((new ImageIcon("images/Icon.png")).getImage());

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		Board board = new Board(new ScoreBoard(), new LevelButton(), new TextBox());
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.gridheight = 12;
		add(board, c);

		ScoreBoard scoreBoard = board.getScoreBoard();
		c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 4;
		c.gridheight = 3;
		add(scoreBoard, c);

		TextBox textBox = board.getTextBox();
		c.gridx = 4;
		c.gridy = 12;
		c.gridwidth = 2;
		c.gridheight = 2;
		add(textBox, c);

		LevelButton levelButton = board.getLevelButton();
		c.gridx = 4;
		c.gridy = 14;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(levelButton, c);

		Quit quit = new Quit(board);
		c.gridx = 5;
		c.gridy = 14;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(quit, c);

		getContentPane().setBackground(new Color(40, 160, 104));

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame ex = new VoltorbFlip();
				ex.setVisible(true);
			}
		});
	}
}
