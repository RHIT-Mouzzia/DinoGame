package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameViewer {
	public static final int DELAY=10;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        JFrame frame = new JFrame("Among Raptors");
        GameComponent gameComponent = new GameComponent();
        frame.add(gameComponent, BorderLayout.CENTER);
        GameAdvanceListener advanceListener = new GameAdvanceListener(gameComponent);
        
        Timer timer = new Timer(DELAY, advanceListener);
		timer.start();
        
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

	}

}
