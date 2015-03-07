import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Random;

public class Board extends JComponent {

	Random random = new Random();
	private Image background = (new ImageIcon("images/Board.png")).getImage();

	private Card[][] cards = new Card[5][5]; //[row][col]
	private SumCard[][] sumCards = new SumCard[2][5]; //[row, column][up/down, left/right]
	private ScoreBoard scoreBoard;
	private LevelButton levelButton;
	private TextBox textBox;

	private int cardsLeft, cardsFlipped;
	private boolean waiting = false;

	public Board(ScoreBoard scoreBoard, LevelButton levelButton, TextBox textBox) {
		this.scoreBoard = scoreBoard;
		this.levelButton = levelButton;
		this.textBox = textBox;

		setPreferredSize(new Dimension(384, 384));

		//initialize arrays
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {
				cards[i][j] = new Card();
			}
		}
		for (int i = 0; i < sumCards.length; i++) {
			for (int j = 0; j < sumCards[i].length; j++) {
				sumCards[i][j] = new SumCard();
			}
		}

		levelButton.setLevel(1);
		newGame();
		addMouseListener(new FlipAdapter());
	}

	private void newGame() {
		waiting = false;
		cardsFlipped = 0;
		scoreBoard.setScore(1);

		resetCards();
		resetSumCards();
		initCards();
		initSumCards();

		textBox.setMessage("New game!");
	}

	public void quitGame() {
		if (cardsFlipped < levelButton.getLevel()) {
			if (cardsFlipped == 0) {
				levelButton.setLevel(1);
			} else {
				levelButton.setLevel(cardsFlipped);
			}
			textBox.setMessage("You quit. Dropped to level " + levelButton.getLevel() + ".");
		} else {
			textBox.setMessage("You quit.");
		}
		scoreBoard.submitScore();
		revealCards();
		waiting = true;
	}

	private int[][] possibleSums(int n) {
		//calculates all the possible ways n can be a sum of 3s and 2s
		//returns an array where each element is of the form [2s, 3s]

		int start = (int) Math.ceil( ((double) n) / -2 );
		int end = (int) Math.floor( ((double) n) / -3 );

		int length = Math.abs(start - end) + 1; //+1 because we include floor(-n/3) in the for loop

		int[][] sums = new int[length][2];
		int j = 0;

		for (int i = start; i <= end; i++) {
			sums[j][0] = -n - 3*i; //diophantine equations
			sums[j][1] = n + 2*i;
			j++;
		}
		
		return sums;
	}

	private void initCards() {
		//determines number of 2s, 3s, and voltorbs, then places them randomly on the board
		int[] multiplierCards = new int[3];
		int level = levelButton.getLevel();

		multiplierCards[0] = (level*3)/2 + 5; //voltorbs
		if (multiplierCards[0] > 13) {
			multiplierCards[0] = 13;
		}

		int[][] sums = possibleSums(level*2 + 8);
		int r = random.nextInt(sums.length);
		multiplierCards[1] = sums[r][0]; //twos
		multiplierCards[2] = sums[r][1]; //threes
		cardsLeft = multiplierCards[1] + multiplierCards[2];
		
		int a, b, j;
		for (int i = 0; i < multiplierCards.length; i++) {
			j = 0;
			while (j < multiplierCards[i]) {
				a = random.nextInt(5);
				b = random.nextInt(5);

				if (cards[a][b].getNumber() == 1) { //i.e. not already a mult. card
					if (i == 0) {
						cards[a][b].setNumber(i);
					} else {
						cards[a][b].setNumber(i + 1);
					}
					j++;
				}
			}
		}
	}
	
	private void initSumCards() {
		Card[] column = new Card[5];
		for (int i = 0; i < cards.length; i++) {
			sumCards[0][i].setTotals(cards[i]);
			for (int j = 0; j < cards[i].length; j++) {
				column[j] = cards[j][i];
			}
			sumCards[1][i].setTotals(column);
		}
	}

	private void resetCards() {
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {
				cards[i][j].reset();
			}
		}
	}

	private void resetSumCards() {
		for (int i = 0; i < sumCards.length; i++) {
			for (int j = 0; j < sumCards[i].length; j++) {
				sumCards[i][j].reset();
			}
		}
	}	

	private void revealCards() {
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {
				cards[i][j].setClicked(true);
			}
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, 384, 384, this);

		drawCards(g);
		drawSumCards(g);
	}

	private void drawCards(Graphics g) {
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {
				if (cards[i][j].isClicked()) {
					g.drawImage(cards[i][j].getImage(), j*64, i*64, 64, 64, this);
				}
			}
		}
	}

	private void drawSumCards(Graphics g) {
		for (int i = 0; i < sumCards[0].length; i++) {
			g.drawImage(sumCards[0][i].getSumImage()[0], 346, i*64 + 8, 16, 16, this);
			g.drawImage(sumCards[0][i].getSumImage()[1], 359, i*64 + 8, 16, 16, this);
			g.drawImage(sumCards[0][i].getVoltImage(), 359, i*64 + 34, 16, 16, this);

			g.drawImage(sumCards[1][i].getSumImage()[0], i*64 + 26, 328, 16, 16, this);
			g.drawImage(sumCards[1][i].getSumImage()[1], i*64 + 39, 328, 16, 16, this);
			g.drawImage(sumCards[1][i].getVoltImage(), i*64 + 39, 354, 16, 16, this);
		}
	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public LevelButton getLevelButton() {
		return levelButton;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	class FlipAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (waiting) {
				newGame();
			} else {
				int x = e.getX();
				int y = e.getY();
				int row = y/64;
				int col = x/64;

				if (x <= 312 && y <= 312 &&
					(x - 10) % 64 <= 46 &&
					(y - 10) % 64 <= 46) {
					cards[row][col].setClicked(true);

					int n = cards[row][col].getNumber();
					scoreBoard.multScore(n);
					textBox.setMessage("x" + n + "!");
					if (n > 1) {
						cardsLeft--;
						cardsFlipped++;
						if (cardsLeft == 0) {
							levelButton.levelUp();
							scoreBoard.submitScore();
							textBox.setMessage("You win! Advanced to level " + levelButton.getLevel() + "!");
							revealCards();
							waiting = true;
						}
					} else if (n == 0) {
						if (cardsFlipped < levelButton.getLevel()) {
							if (cardsFlipped == 0) {
								levelButton.setLevel(1);
							} else {
								levelButton.setLevel(cardsFlipped);
							}
							textBox.setMessage("BOOM!! Dropped to level " + levelButton.getLevel() + ".");
						} else {
							textBox.setMessage("BOOM!! Careful!");
						}
						revealCards();
						waiting = true;
					}
				}
			}
			repaint();
		}
	}
}
