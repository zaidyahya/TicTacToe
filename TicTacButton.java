import java.awt.Color;
import java.util.InputMismatchException;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class TicTacButton extends JButton {
	
	private char value;

	public TicTacButton() {
		value = ' ';
	}

	public TicTacButton(String text) {
		super(text);
		value = ' ';
	}
	
	public char getValue() {
		return this.value;
	}

	public void userMove() throws InputMismatchException
	{
		if(this.value == ' ') {
			value = 'X';
			super.setText("X");
			super.setForeground(Color.RED);
		}
		else {
			throw new InputMismatchException();
		}
	}
	
	public void AIMove() throws IllegalArgumentException
	{
		if(this.value == ' ') {
			value = 'O';
			super.setText("O");
			super.setForeground(Color.BLUE);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public void reset() {
		this.value = ' ';
		super.setText("");
	}
}
