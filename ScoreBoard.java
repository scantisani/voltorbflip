import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;

public class ScoreBoard extends JComponent {

	private final int WIDTH = 256;
	private final int HEIGHT = 80;
	
	private int score, highScore;
	private Image background;

	public ScoreBoard() {
		score = 0;
		highScore = 0;
		background = (new ImageIcon("images/ScoreBoard.png")).getImage();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void multScore(int multiplier) {
		score = score * multiplier;
		repaint();
	}

	public void submitScore() {
		if (score > highScore) {
			highScore = score;
		}
		score = 0;
		repaint();
	}

	public void setScore(int score) {
		this.score = score;
		repaint();
	}

	public void paint(Graphics g) {
		Image number;
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, this);

		String scoreStr = Integer.toString(score);
		String highScoreStr = Integer.toString(highScore);

		for(int i = 0; i < 9; i++) {
			if (i >= 9 - highScoreStr.length()) {
				number = (new ImageIcon("images/Number" + highScoreStr.charAt(i - (9 -highScoreStr.length())) + ".png")).getImage();
			} else {
				number = (new ImageIcon("images/Number0.png")).getImage();
			}
			g.drawImage(number, 120 + i*13, 12, 16, 16, this);

			if (i >= 9 - scoreStr.length()) {
				number = (new ImageIcon("images/Number" + scoreStr.charAt(i - (9 - scoreStr.length())) + ".png")).getImage();
			} else {
				number = (new ImageIcon("images/Number0.png")).getImage();
			}
			g.drawImage(number, 120 + i*13, 52, 16, 16, this);
		}
	}
}
