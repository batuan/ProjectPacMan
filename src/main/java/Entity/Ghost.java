package Entity;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Render.GameBoard;

public class Ghost extends Entity{
	private int speed;
	// color = 1 Blinky, Color = 2 PINK, Color = 3 Clyde.
	private int color;
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	Ghost(int x, int y, int color){
		this.setPosition(new Point(x, y));
		this.setPixelPosition(new Point(28*x, 28*y));
		this.color = color;
		switch (color){
			case 1:{
				Image image = new ImageIcon(this.getURLPath("Image/Ghost/Blinky/Blinky-0.png")).getImage();
				this.setImage(image);
				break;
			}
			case 2: {
				Image image = new ImageIcon(this.getURLPath("Image/Ghost/Pinky/Pinky-0.png")).getImage();
				this.setImage(image);
				break;
			}
			case 3: {
				Image image = new ImageIcon(this.getURLPath(  "Image/Ghost/Clyde/Clyde-0.png")).getImage();
				this.setImage(image);
				break;
			}
			default: {
				Image image = new ImageIcon(this.getURLPath( "Image/Ghost/Inky/Inky-0.png")).getImage();
				this.setImage(image);
				break;
			}
		}

	}

	public Ghost(Point position, GameBoard board){
		this.setPosition(position);
		this.setPixelPosition(new Point(position.x*28, position.y*28));
		this.setGameBoard(board);
		try {
			String path = System.getProperty("user.dir");
			Image image = ImageIO.read(new File(path+ "/resources/Ghost/7.png"));
			this.setImage(image);
			//this.image = ImageIO.read(this.getClass().getResource("resources/Ghost/7.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
