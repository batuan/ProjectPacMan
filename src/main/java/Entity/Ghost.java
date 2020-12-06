package Entity;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import AI.AiInterface;
import AI.RandomMovement;
import App.CoreKernel;
import PhysicsEngine.Movement.Movement;
import PhysicsEngine.Movement.MovementType;
import Render.GameBoard;

public class Ghost extends MovableEntity {
	private int speed = 1;
	// color = 1 Blinky, Color = 2 PINK, Color = 3 Clyde.
	public MovementType nextDirection;
	private int color;
	public AiInterface AI;
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
	
	public Ghost(int x, int y, int color){
		this.setPosition(new Point(x, y));
		this.setPixelPosition(new Point(10+getSize()*x, 10+getSize()*y));
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
		this.setDirection(MovementType.STOP);
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

	public void nextMoveCalculateByAI(CoreKernel coreKernel){
		MovementType movementType = this.AI.getMovement(this, coreKernel);
		//System.out.println("movement: " + movementType);
		this.setDirection(movementType);
	}

	@Override
	public Boolean canMove() {
		return true;
	}

	@Override
	public void move() {
		System.out.println("in ghost move: " + this.getDirection());
		switch (this.getDirection()) {
			case UP:
				this.getPixelPosition().y -= speed;
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			case DOWN:
				this.getPixelPosition().y += speed;
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			case LEFT:
				this.getPixelPosition().x -= speed;
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			case RIGHT:
				this.getPixelPosition().x += speed;
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			default: break;
		}
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public void goUp() {
		this.nextDirection = MovementType.UP;
	}

	@Override
	public void goDown() {
		this.nextDirection = MovementType.DOWN;
	}

	@Override
	public void goLeft() {
		this.nextDirection = MovementType.LEFT;
	}

	@Override
	public void goRight() {
		this.nextDirection = MovementType.RIGHT;
	}

	@Override
	public void stop() {
		this.nextDirection = MovementType.STOP;
	}
}
