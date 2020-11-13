package Entity;

import Render.GameBoard;

import javax.swing.*;
import java.awt.*;

public class Coin extends Entity {
	public Coin(int x, int y) {
		this.setPosition(new Point(x, y));
		this.setPixelPosition(new Point(10+x * 28, 10+y * 28));
		Image image = new ImageIcon(this.getPath()).getImage();
		this.setImage(image);
	}
}