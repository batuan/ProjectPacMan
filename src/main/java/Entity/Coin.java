package Entity;

import Render.GameBoard;

import javax.swing.*;
import java.awt.*;

public class Coin extends Entity {
	public Coin(int x, int y) {
		this.setPosition(new Point(x, y));
		this.setPixelPosition(new Point(x * 28, y * 28));
		this.setPath(this.getURLPath("Image/Coin/Coin-0.png").getPath());
		Image image = new ImageIcon(this.getPath()).getImage();
		this.setImage(image);
	}
}