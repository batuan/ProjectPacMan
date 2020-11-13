package Entity;

import java.awt.*;

public class Wall extends Entity{
    public Wall(Point position){
        this.setPosition(position);
        this.setPixelPosition(new Point(position.x*getSize()+10, position.y*getSize()+10));
    }
}
