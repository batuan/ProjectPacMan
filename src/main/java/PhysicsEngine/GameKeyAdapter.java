package PhysicsEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameKeyAdapter extends KeyAdapter {
    boolean inGame = false;
    int x = 10;
    int y = 10;
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            System.out.println("left");
            x = - 1;
            y = 0;
        } else if (key == KeyEvent.VK_RIGHT) {
            System.out.println("right");
            x = 1;
            y = 0;
        } else if (key == KeyEvent.VK_UP) {
            System.out.println("up");
            x = 0;
            y = -1;
        } else if (key == KeyEvent.VK_DOWN) {
            System.out.println("down");
            x = 0;
            y = 1;
        }
    }
}
