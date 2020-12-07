package Render;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

public class MazeWindow extends JFrame {
	public MazeWindow() {
		setTitle("GL - Projet -  Pacman"); 
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		setLayout(new BorderLayout()); 
		getContentPane().setBackground(Color.black); setSize(776,870);
		setLocationRelativeTo(null); 
		JLabel scoreboard = new JLabel("  Score : 0"); 
		scoreboard.setForeground(new Color(255, 243, 36));
		setVisible(true);
		Map map = new Map();
		map.getMapFromResource("mapdata/map.txt");
		//map.adjustMap();
		map.adjustNewMap();
		GameBoard pb = new GameBoard(scoreboard, map, this);
        pb.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new LineBorder(Color.BLUE)));
        pb.setFocusable(true);
        pb.requestFocusInWindow();
        this.getContentPane().add(scoreboard,BorderLayout.SOUTH);
        this.getContentPane().add(pb);
        setVisible(true);
        this.addKeyListener(pb);
	}
}
