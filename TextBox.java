import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;

import java.awt.GraphicsEnvironment;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class TextBox extends JLabel {

	private final int WIDTH = 125;
	private final int HEIGHT = 50;

	private ImageIcon box = new ImageIcon("images/TextBox.png");

	public TextBox() {
		loadFont();
		setFont(new Font("Pokemon DPPT", Font.PLAIN, 16));
		setIcon(box);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setIconTextGap(8-WIDTH); //8-pixel left margin
	}

	public void loadFont() {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Pokemon.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void setMessage(String text) {
		if (text.length() <= 20) {
			setText("<html>" + text + "<br><br></html>");
		} else if (text.length() <= 40) {
			setText("<html>" + text + "</html>");
		} else {
			setText("<html>Text could not be displayed.</html>");
		}
	}
}
