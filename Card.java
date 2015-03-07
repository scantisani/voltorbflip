import java.awt.Image;
import javax.swing.ImageIcon;

public class Card {

	Image image;
	int number;
	boolean clicked;

	public Card() {
		number = 1;
		clicked = false;
		setImage();
	}

	public Image getImage() {
		return image;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		setImage();
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	private void setImage() {
		image = (new ImageIcon("images/Card" + number + ".png")).getImage();
	}

	public void reset() {
		this.number = 1;
		this.clicked = false;
		setImage();
	}
}
