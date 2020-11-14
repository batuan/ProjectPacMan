package App;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Graphics extends JFrame {

    public Graphics(){

        JFrame game = new JFrame("Pacman");
        game.setLocationRelativeTo(null);
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        game.getContentPane().setBackground(Color.black);

        JPanel map = new JPanel();
        map.setLayout(new BorderLayout());
        map.setBackground(Color.black);
        game.add(map, BorderLayout.CENTER);

        JPanel score = new JPanel();
        score.setLayout(new BorderLayout());
        score.setBackground(Color.black);
        game.add(score, BorderLayout.SOUTH);

        JTextField getscore = new JTextField(10);
        getscore.setEditable(false);
        getscore.setBackground(Color.black);
        getscore.setForeground(Color.yellow);
        getscore.setBorder(null);
        getscore.removeFocusListener(null);
        score.add(getscore);
        game.pack();
        game.setVisible(true);
    }
}