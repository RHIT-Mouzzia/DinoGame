import javax.swing.JFrame;

public class GameViewer{

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Among Raptors");

        GamePanel panel = new GamePanel();
        window.add(panel);
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

