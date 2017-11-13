package TicTacToe;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TicTacToe {

	private JFrame frmTicTacToe;
	private int dimension;
	private int turnKeeper;
	private int movesPlayed;
	private JLabel instructor, userLabel, AILabel, userScore, AIScore;
	private int userGames, AIGames;
	private TicTacButton[][] board;

	
	public TicTacToe() {
		userGames = 0;
		AIGames = 0;
	}
	
	public void createBoard(int n, String userName)
	{
		//Set the frame
		frmTicTacToe = new JFrame();
		frmTicTacToe.setTitle("Tic Tac Toe");
		frmTicTacToe.setBounds(500, 300, dimension*50, (dimension*50)+100);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTicTacToe.getContentPane().setLayout(null);	
		frmTicTacToe.setVisible(true);
		
		//Instruction place holder
		JPanel instructionPH = new JPanel();
		instructionPH.setBackground(Color.LIGHT_GRAY);
		System.out.println("WHAT THE FUCK");
		instructionPH.setToolTipText("");
		instructionPH.setBounds(0, (dimension*50)+45, dimension*50, 25);
		frmTicTacToe.getContentPane().add(instructionPH);
		instructionPH.setLayout(null);
		
		//Scores place holder
		JPanel scoresPH = new JPanel();
		scoresPH.setBackground(Color.green);
		scoresPH.setBounds(0, 0, dimension*50, 40);
		frmTicTacToe.getContentPane().add(scoresPH);
		scoresPH.setLayout(null);
		
		//Printing of instructions
		instructor = new JLabel("");
		instructor.setHorizontalAlignment(SwingConstants.CENTER);
		instructor.setBounds(0, 3, dimension*50, 20);
		float fontSize = 3.5f*dimension;
		instructor.setFont(instructor.getFont().deriveFont(fontSize));
		instructionPH.add(instructor);
		
		userLabel = new JLabel(userName);
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		userLabel.setBounds(0, 0, (dimension*50)/2, 10);
		scoresPH.add(userLabel);
		
		AILabel = new JLabel("AI");
		AILabel.setHorizontalAlignment(SwingConstants.CENTER);
		AILabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		AILabel.setBounds((dimension*50)/2, 0, (dimension*50)/2, 10);
		scoresPH.add(AILabel);
		
		userScore = new JLabel("0");
		userScore.setHorizontalAlignment(SwingConstants.CENTER);
		userScore.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		userScore.setBounds(0, 15, (dimension*50)/2, 25);
		scoresPH.add(userScore);
		
		AIScore = new JLabel("0");
		AIScore.setHorizontalAlignment(SwingConstants.CENTER);
		AIScore.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		AIScore.setBounds((dimension*50)/2, 15, (dimension*50)/2, 25);
		scoresPH.add(AIScore);
		
		
		//Make the board
		board = new TicTacButton[dimension][dimension];
		for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++) {
				board[i][j] = new TicTacButton("");
				board[i][j].setBounds(i*50, (j*50)+40, 50, 50);
				board[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
								((TicTacButton)e.getSource()).userMove();
								movesPlayed++;
								if(checkForWinner() == 'X') {
									if(endGame('X') == 1) {
										restartGame();
									}
									else {
										System.exit(0);
									}
								}
								else if(checkForWinner() == ' ' && movesPlayed == dimension*dimension) {
									if(endGame(' ') == 1) {
										restartGame();
									}
									else {
										System.exit(0);
									}
								}
								else if(checkForWinner() == 'O') {
									if(endGame('O') == 1) {
										restartGame();
									}
									else {
										System.exit(0);
									}
								}
								else {
									if(!checkForObviousMove()) {
										getAIMove();
									}
									movesPlayed++;
									changeMessage();
									if(checkForWinner() == 'X') {
										if(endGame('X') == 1) {
											restartGame();
										}
										else {
											System.exit(0);
										}
									}
									else if(checkForWinner() == ' ' && movesPlayed == dimension*dimension) {
										if(endGame(' ') == 1) {
											restartGame();
										}
										else {
											System.exit(0);
										}
									}
									else if(checkForWinner() == 'O') {
										if(endGame('O') == 1) {
											restartGame();
										}
										else {
											System.exit(0);
										}
									}
								}
							}
						catch(InputMismatchException f) {
							JOptionPane.showMessageDialog(frmTicTacToe, "This spot is already taken!", "Error!", JOptionPane.ERROR_MESSAGE);
						}					
					}
				});
				frmTicTacToe.getContentPane().add(board[i][j]);
			}
		}
	}
	
	public void coinToss() {
		Random coin = new Random();
		turnKeeper = coin.nextInt(2);
		JOptionPane.showMessageDialog(frmTicTacToe, "The result of the coin toss is :- " + turnKeeper, "Coin Toss", JOptionPane.INFORMATION_MESSAGE);
		if(turnKeeper == 1) {
			JOptionPane.showMessageDialog(frmTicTacToe, "The AI has the first move!", "Outcome", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(frmTicTacToe, "Your move first! ", "Outcome", JOptionPane.INFORMATION_MESSAGE);
		}
		if(turnKeeper == 1) {
			getAIMove();
			movesPlayed++;
			instructor.setText("The AI made it's move");
			changeMessage();
			turnKeeper = 0;
		};
	}
	
	public boolean checkForObviousMove()
	{
		
		//Check horizontally
		int counter = 0;
		int checker = -1;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				if(board[i][j].getValue() == 'O') {
					counter++;
				}
				else if(board [i][j].getValue() == ' ') {
					checker = j;
				}
			}
			if(counter == board.length - 1 && checker != -1) {
				board[i][checker].AIMove();
				instructor.setText("The AI made it's move");
				return true;
			}
			counter = 0;
			checker = -1;
		}
		
		//Check vertically
		checker = -1;
		counter = 0;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				if(board[j][i].getValue() == 'O') {
					counter++;
				}
				else if(board [j][i].getValue() == ' ') {
					checker = j;
				}
			}
			if(counter == board.length - 1 && checker != -1) {
				board[checker][i].AIMove();
				instructor.setText("The AI made it's move");
				return true;
			}
			counter = 0;
			checker = -1;
		}
		
		//Check diagonals
		checker = -1;
		counter = 0;
		for(int i=0;i<board.length;i++) {
			if(board[i][i].getValue() == 'O') {
				counter++;
			}
			else if(board[i][i].getValue() == ' ') {
				checker = i;
			}
		}
		if(counter == board.length - 1 && checker != -1) {
			System.out.println(checker);
			board[checker][checker].AIMove();
			instructor.setText("The AI made it's move");
			return true;
		}
		
		//Check other diagonal
		checker = -1;
		counter = 0;
		for(int i= 0;i<board.length;i++) {
			if(board[i][board.length-1-i].getValue() == 'O') {
				counter++;
			}
			else if(board[i][board.length-1-i].getValue() == ' ') {
				checker = i;
			}
		}
		if(counter == board.length - 1 && checker != -1) {
			board[checker][board.length-1-checker].AIMove();
			instructor.setText("The AI made it's move");
			return true;
		}
		
		//Now check to stop the user from winning
		counter = 0;
		checker = -1;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				if(board[i][j].getValue() == 'X') {
					counter++;
				}
				else if(board [i][j].getValue() == ' ') {
					checker = j;
				}
			}
			if(counter == board.length - 1 && checker != -1) {
				board[i][checker].AIMove();
				instructor.setText("The AI made it's move");
				return true;
			}
			counter = 0;
			checker = -1;
		}
		
		//Check vertically
		checker = -1;
		counter = 0;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				if(board[j][i].getValue() == 'X') {
					counter++;
				}
				else if(board [j][i].getValue() == ' ') {
					checker = j;
				}
			}
			if(counter == board.length - 1 && checker != -1) {
				board[checker][i].AIMove();
				instructor.setText("The AI made it's move");
				return true;
			}
			counter = 0;
			checker = -1;
		}
		
		//Check diagonals
		checker = -1;
		counter = 0;
		for(int i=0;i<board.length;i++) {
			if(board[i][i].getValue() == 'x') {
				counter++;
			}
			else if(board[i][i].getValue() == ' ') {
				checker = i;
			}
		}
		if(counter == board.length - 1 && checker != -1) {
			board[checker][checker].AIMove();
			instructor.setText("The AI made it's move");
			return true;
		}
		
		//Check other diagonal
		checker = -1;
		counter = 0;
		for(int i= 0;i<board.length;i++) {
			if(board[i][board.length-1-i].getValue() == 'X') {
				counter++;
			}
			else if(board[i][board.length-1-i].getValue() == ' ') {
				checker = i;
			}
		}
		if(counter == board.length - 1 && checker != -1) {
			board[checker][board.length-1-checker].AIMove();
			instructor.setText("The AI made it's move");
			return true;
		}	
		return false;
	}
	
	public void getAIMove()
	{
		//If there is no obvious, keep asking for input until a valid one is found
		if(!checkForObviousMove()) {
			Random generateRandomNumber = new Random();
			int x = generateRandomNumber.nextInt(board.length);
			int y = generateRandomNumber.nextInt(board.length);
			boolean success = false;
			while(!success) {
				try {
					board[x][y].AIMove();
					instructor.setText("The AI made it's move");
					success = true;
				}
				catch(IllegalArgumentException e) {
					success = false;
					x = generateRandomNumber.nextInt(board.length);
					y = generateRandomNumber.nextInt(board.length);
				}
			}
		}
	}
	
	public char checkForWinner()
	{
		//Horizontal
		int counterForUser = 0;
		int counterForAI = 0;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				if(board[i][j].getValue() == 'O') {
					counterForAI++;
				}
				else if(board[i][j].getValue() == 'X') {
					counterForUser++;
				}
			}
			if(counterForUser == board.length) {
				return 'X';
			}
			else if(counterForAI == board.length) {
				return 'O';
			}
			counterForUser = 0;
			counterForAI = 0;
		}
		
		//Vertical
		counterForUser = 0;
		counterForAI = 0;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				if(board[j][i].getValue() == 'O') {
					counterForAI++;
				}
				else if(board [j][i].getValue() == 'X') {
					counterForUser++;
				}
			}
			if(counterForUser == board.length) {
				return 'X';
			}
			else if(counterForAI == board.length) {
				return 'O';
			}
			counterForUser = 0;
			counterForAI = 0;
		}
		
		//Diagonal (L to R)
		counterForUser = 0;
		counterForAI = 0;
		for(int i=0;i<board.length;i++) {
			if(board[i][i].getValue() == 'X') {
				counterForUser++;
			}
			else if(board[i][i].getValue() == 'O') {
				counterForAI++;
			}
		}
		if(counterForUser == board.length) {
			return 'X';
		}
		else if(counterForAI == board.length) {
			return 'O';
		}
		
		//Diagonal (R to L)
		counterForUser = 0;
		counterForAI = 0;
		for(int i= 0;i<board.length;i++) {
			if(board[i][board.length-1-i].getValue() == 'X') {
				counterForUser++;
			}
			else if(board[i][board.length-1-i].getValue() == 'O') {
				counterForAI++;
			}
		}
		if(counterForUser == board.length) {
			return 'X';
		}
		else if(counterForAI == board.length) {
			return 'O';
		}
		
		return ' ';
	}
	
	public int endGame(char symbol) {
		Object[] options = {"Cancel", "Restart"};
		if(symbol == 'X') {
			userGames++;
			userScore.setText(String.valueOf(userGames));
			return JOptionPane.showOptionDialog(frmTicTacToe, "Congratulations! You won", "Result",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		}
		else if(symbol == 'O') {
			AIGames++;
			AIScore.setText(String.valueOf(AIGames));
			return JOptionPane.showOptionDialog(frmTicTacToe, "Sorry! You lost", "Result",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		}
		else {
			return JOptionPane.showOptionDialog(frmTicTacToe, "The game was a tie! Try again", "Result",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		}
	}
	
	public void restartGame() {
		for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++) {
				board[i][j].reset();
			}
		}
		movesPlayed = 0;
		coinToss();
	}
	
	public void changeMessage()
	{
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
					instructor.setText("Your move");
			}
		};
		timer.schedule(task, 1500);
	}
	
	public void play()
	{
		String playerName = JOptionPane.showInputDialog(frmTicTacToe, "Please enter your name!", "Welcome", JOptionPane.PLAIN_MESSAGE);
		if(playerName == null) {
			System.exit(0);
		}
		String boardDimension = JOptionPane.showInputDialog(frmTicTacToe, "Welcome " +playerName+"! Enter the dimensions of your board ");
		if(boardDimension == null) {
			System.exit(0);
		}
		dimension = Integer.parseInt(boardDimension);		
		createBoard(dimension, playerName);
		movesPlayed = 0;
		coinToss();
	}
}


