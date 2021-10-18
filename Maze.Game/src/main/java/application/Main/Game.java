package application.Main;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Treasure Hunter");
		window.add(new GamePanel());
		window.setResizable(false);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}

