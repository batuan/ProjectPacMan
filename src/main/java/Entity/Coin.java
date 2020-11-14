package Entity;

import Render.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin extends Entity {
	public boolean canEat = true;
	public Coin(int x, int y) {
		this.setPosition(new Point(x, y));
		this.setPixelPosition(new Point(10+x * 28, 10+y * 28));
		Image image = new ImageIcon(this.getPath()).getImage();
		this.setImage(image);
	}

	public void setEaten() {
		Image image = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
		image.getGraphics().setColor(Color.black);
		this.setImage(image);
	}
}