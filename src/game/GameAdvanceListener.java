package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdvanceListener implements ActionListener {
	private GameComponent gameComponent;
	
	public GameAdvanceListener(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		advanceOneTick();
	}

	private void advanceOneTick() {
		// TODO Auto-generated method stub
		this.gameComponent.drawScreen();
		this.gameComponent.upDateState();
	
	}
	
	
}