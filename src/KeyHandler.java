import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {

	public boolean up, down, left, right;


	@Override
	public void keyPressed(KeyEvent ke) {
		int keyCode = ke.getKeyCode();

		if (keyCode == KeyEvent.VK_W) {
			up = true;
		}
		if (keyCode == KeyEvent.VK_S) {
			down = true;
		}
		if (keyCode == KeyEvent.VK_A) {
			left = true;
		}
		if (keyCode == KeyEvent.VK_D) {
			right = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		int keyCode = ke.getKeyCode();

		if (keyCode == KeyEvent.VK_W) {
			up = false;
		}
		if (keyCode == KeyEvent.VK_S) {
			down = false;
		}
		if (keyCode == KeyEvent.VK_A) {
			left = false;
		}
		if (keyCode == KeyEvent.VK_D) {
			right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}