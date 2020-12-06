package Entity;

import Render.GameBoard;

import java.awt.*;
import java.net.URL;

public class Entity {
    private Point position;
    private Point pixelPosition;
    private static int size = 28;
    private Image image;
    private String path;
    private String type;
    private String systemPath = System.getProperty("user.dir");
    private GameBoard gameBoard;

    public Point getPosition() {
        return position;
    }

    public Point getCurrentPosition(){
        System.out.println(getPixelPosition());
        if((this.pixelPosition.x-10)%size!=0||(this.pixelPosition.y-10)%size!=0) return null;
        else return new Point((this.pixelPosition.x-10)/size, (this.pixelPosition.y-10)/size);
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPixelPosition() {
        return pixelPosition;
    }

    public void setPixelPosition(Point pixelPosition) {
        this.pixelPosition = pixelPosition;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(String systemPath) {
        this.systemPath = systemPath;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public URL getURLPath(String subpath) {
        URL url = this.getClass().getClassLoader().getResource(subpath);
        return url;
    }

    public Rectangle getRectangle() {
        int x = this.getPixelPosition().x;
        int y = this.getPixelPosition().y;
        return new Rectangle(x, y, getSize(), getSize());
    }
}
