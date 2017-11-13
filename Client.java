package TicTacToe;

import java.awt.EventQueue;

public class Client {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe game = new TicTacToe();
					game.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
