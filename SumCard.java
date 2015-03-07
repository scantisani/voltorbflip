import java.awt.Image;
import javax.swing.ImageIcon;

public class SumCard {

	int voltorbs, sum;
	Image[] sumImage = new Image[2];
	Image voltImage;

	public SumCard() {
		this.voltorbs = 0;
		this.sum = 0;
	}

	public void setTotals(Card[] cards) {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].getNumber() == 0) {
				this.voltorbs++;
			}
			this.sum += cards[i].getNumber();
		}
		setImages();
	}

	public void reset() {
		this.voltorbs = 0;
		this.sum = 0;
	}

	public int getVoltorbs() {
		return this.voltorbs;
	}

	public int getSum() {
		return this.sum;
	}

	private void setImages() {
		if (this.sum < 10) {
			this.sumImage[0] = (new ImageIcon("images/Number0.png")).getImage();
			this.sumImage[1] = (new ImageIcon("images/Number" + this.sum + ".png")).getImage();

		} else {
			this.sumImage[0] = (new ImageIcon("images/Number1.png")).getImage();
			this.sumImage[1] = (new ImageIcon("images/Number" + (this.sum - 10) + ".png")).getImage();
		}

		this.voltImage = (new ImageIcon("images/Number" + this.voltorbs + ".png")).getImage();
	}

	public Image getVoltImage() {
		return this.voltImage;
	}

	public Image[] getSumImage() {
		return this.sumImage;
	}
}
