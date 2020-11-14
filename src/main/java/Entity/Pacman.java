package Entity;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;

import PhysicsEngine.CollisionEngine.CollisionInMap;
import PhysicsEngine.Movement.MovementType;
import Render.GameBoard;

public class Pacman extends MovableEntity {
	private Image normalImage, upImage, downImage, leftImage, rightImage;
	private Timer timer;
	private int speed = 1;
	private Point startPoint;
	public MovementType nextDirection;

	private int life = 3;

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Pacman() {

	}

	public Point getStartPoint() {
		return startPoint;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Image getNormalImage() {
		return normalImage;
	}

	public void setNormalImage(Image normalImage) {
		this.normalImage = normalImage;
	}

	public Image getUpImage() {
		return upImage;
	}

	public void setUpImage(Image upImage) {
		this.upImage = upImage;
	}

	public Image getDownImage() {
		return downImage;
	}

	public void setDownImage(Image downImage) {
		this.downImage = downImage;
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(Image leftImage) {
		this.leftImage = leftImage;
	}

	public Image getRightImage() {
		return rightImage;
	}

	public void setRightImage(Image rightImage) {
		this.rightImage = rightImage;
	}

	public Pacman(Point position, GameBoard board) {
		nextDirection = MovementType.STOP;
		this.startPoint = position;
		this.setPosition(position);
		this.setPixelPosition(new Point(10+position.x*28, 10+position.y*28));
		this.setGameBoard(board);
		this.setPath(this.getSystemPath() + "/resources/Image/Pacman/");
		this.normalImage = new ImageIcon(getURLPath("Image/Pacman/pac_normal.png")).getImage();
		this.upImage = new ImageIcon(getURLPath("Image/Pacman/up/up.gif")).getImage();
		this.downImage = new ImageIcon(getURLPath("Image/Pacman/down/down.gif")).getImage();
		this.rightImage = new ImageIcon(getURLPath("Image/Pacman/right/right.gif")).getImage();
		this.leftImage = new ImageIcon(getURLPath("Image/Pacman/left/left.gif")).getImage();
		this.setImage(normalImage);
	}

	public void verifyNextDirection(ArrayList<Wall> walls){
		if(nextDirection==MovementType.STOP) return;
		Pacman newPacman = new Pacman();
		newPacman.setDirection(this.nextDirection);
		newPacman.setPixelPosition(this.getPixelPosition());
		CollisionInMap collision = new CollisionInMap();
		for (Wall wall : walls) {
			collision.collisionWithWall(newPacman, wall);
		}
		if (newPacman.getDirection() != MovementType.STOP) calculateNextDirection();
	}

	private void calculateNextDirection() {
		this.setDirection(nextDirection);
		nextDirection = MovementType.STOP;
	}

	public void setNewPosition(Point position) {
		super.setPosition(position);
		this.getPixelPosition().setLocation(10 + position.x * 28, 10+position.y*28);
	}

	@Override
	public Boolean canMove() {
		return true;
	}

	@Override
	public void move() {
//		Point position = pacman.getPosition();
//		position.x = position.x + speed;
//		pacman.setNewPosition(position);
//		this.getGraphics().drawImage(pacman.getNormalImage(), position.y, position.x, null);
//		this.repaint();
		switch (this.getDirection()) {
			case UP:
				this.getPixelPosition().y -= speed;
				this.setImage(upImage);
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			case DOWN:
				this.getPixelPosition().y += speed;
				this.setImage(downImage);
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			case LEFT:
				this.getPixelPosition().x -= speed;
				this.setImage(leftImage);
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			case RIGHT:
				this.getPixelPosition().x += speed;
				this.setImage(rightImage);
				this.getGameBoard().getGraphics().drawImage(this.getImage(), getPixelPosition().x, getPixelPosition().y, null);
				this.getGameBoard().repaint();
				break;
			default: break;
		}
	}

	@Override
	public String name() {
		return "Pacman_Movement";
	}

	/**
	 * We have a next direction to get next move of pacman. If next Direction is Stop, Pacman will stop
	 * else Pacman will continue the direction
	 */

	@Override
	public void goUp() {
		this.nextDirection = MovementType.UP;
		//this.setDirection(MovementType.UP);
	}

	@Override
	public void goDown() {
		this.nextDirection = MovementType.DOWN;
		//this.setDirection(MovementType.DOWN);
	}

	@Override
	public void goLeft() {
		this.nextDirection = MovementType.LEFT;
		//this.setDirection(MovementType.LEFT);
	}

	@Override
	public void goRight() {
		this.nextDirection = MovementType.RIGHT;
		//this.setDirection(MovementType.RIGHT);
	}

	@Override
	public void stop() {
		this.setDirection(MovementType.STOP);
	}
}
