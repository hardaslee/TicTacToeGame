import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    JButton restartButton;
    JButton changeNamesButton; // New button for changing player names
    
    String player1Name = "X";
    String player2Name = "O";

    public void setPlayerNames(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        updateTurnLabel();
    }
    
    private void updateTurnLabel() {
        if (player1_turn) {
            textfield.setText(player1Name + "'s turn");
        } else {
            textfield.setText(player2Name + "'s turn");
        }
    }
    public void changePlayerNamesDuringGame() {
          String newPlayer1Name = null;
          String newPlayer2Name = null;

          // Keep prompting until a valid name for Player 1 is entered or the user cancels
          while (true) {
              newPlayer1Name = JOptionPane.showInputDialog(frame, "Enter new name for Player X:");
              if (newPlayer1Name == null) {
                  int option = JOptionPane.showConfirmDialog(frame, "Do you want to cancel and keep the current name for Player X?", "Cancel Input", JOptionPane.YES_NO_OPTION);
                  if (option == JOptionPane.YES_OPTION) {
                      return; // Exit the method
                  }
              } else if (newPlayer1Name.isEmpty() || newPlayer1Name.equalsIgnoreCase("O")) {
                  JOptionPane.showMessageDialog(frame, "Invalid name for Player X. Please enter a different name.");
              } else {
                  break; // Exit the loop
              }
          }

          // Keep prompting until a valid name for Player 2 is entered or the user cancels
          while (true) {
              newPlayer2Name = JOptionPane.showInputDialog(frame, "Enter new name for Player O:");
              if (newPlayer2Name == null) {
                  int option = JOptionPane.showConfirmDialog(frame, "Do you want to cancel and keep the current name for Player O?", "Cancel Input", JOptionPane.YES_NO_OPTION);
                  if (option == JOptionPane.YES_OPTION) {
                      return; // Exit the method
                  }
              } else if (newPlayer2Name.isEmpty() || newPlayer2Name.equalsIgnoreCase("X")) {
                  JOptionPane.showMessageDialog(frame, "Invalid name for Player O. Please enter a different name.");
              } else {
                  break; // Exit the loop
              }
          }

          // Update names if the user entered valid names
          if (newPlayer1Name != null && !newPlayer1Name.equals("")) {
              player1Name = newPlayer1Name;
          }
          if (newPlayer2Name != null && !newPlayer2Name.equals("")) {
              player2Name = newPlayer2Name;
          }

          updateTurnLabel();
      }

    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 90, 25));
        textfield.setFont(new Font("Cardana", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("Cardana", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        // Create and configure restart button
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Cardana", Font.BOLD, 20));
        restartButton.setFocusable(false);
        restartButton.addActionListener(this);

        // Create and configure change names button
        changeNamesButton = new JButton("Change Names");
        changeNamesButton.setFont(new Font("Cardana", Font.BOLD, 20));
        changeNamesButton.setFocusable(false);
        changeNamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePlayerNamesDuringGame();
            }
        });

        // Add restart and change names buttons to the title_panel
        title_panel.add(restartButton, BorderLayout.EAST);
        title_panel.add(changeNamesButton, BorderLayout.WEST);

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);

        // Initialize the game
        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            restartGame();
        } else {
            for (int i = 0; i < 9; i++) {
                if (e.getSource() == buttons[i]) {
                    if (player1_turn) {
                        if (buttons[i].getText().equals("")) {
                            buttons[i].setForeground(new Color(255, 0, 0));
                            buttons[i].setText("X");
                            player1_turn = false;
                            textfield.setText(player2Name+"'s turn");
                            check();
                        }
                    } else {
                        if (buttons[i].getText().equals("")) {
                            buttons[i].setForeground(new Color(0, 0, 255));
                            buttons[i].setText("O");
                            player1_turn = true;
                            textfield.setText(player1Name+"'s turn");
                            check();
                        }
                    }
                }
            }
        }
    }

    public void restartGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(new Color(120, 159, 150)); // Set the initial background color
            buttons[i].setEnabled(true);
        }
        player1_turn = random.nextBoolean();
        if (player1_turn) {
            textfield.setText(player1Name+"'s turn");
        } else {
            textfield.setText(player2Name+"'s turn");
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X's turn");
        } else {
            player1_turn = false;
            textfield.setText("O's turn");
        }
    }

	
    public void check() {
        // check x win
        if (
                (buttons[0].getText().equals("X")) &&
                (buttons[1].getText().equals("X")) &&
                (buttons[2].getText().equals("X"))
        ) {
            xWins(0, 1, 2);
        }
        if (
                (buttons[3].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[5].getText().equals("X"))
        ) {
            xWins(3, 4, 5);
        }
        if (
                (buttons[6].getText().equals("X")) &&
                (buttons[7].getText().equals("X")) &&
                (buttons[8].getText().equals("X"))
        ) {
            xWins(6, 7, 8);
        }
        if (
                (buttons[0].getText().equals("X")) &&
                (buttons[3].getText().equals("X")) &&
                (buttons[6].getText().equals("X"))
        ) {
            xWins(0, 3, 6);
        }
        if (
                (buttons[1].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[7].getText().equals("X"))
        ) {
            xWins(1, 4, 7);
        }
        if (
                (buttons[2].getText().equals("X")) &&
                (buttons[5].getText().equals("X")) &&
                (buttons[8].getText().equals("X"))
        ) {
            xWins(2, 5, 8);
        }
        if (
                (buttons[0].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[8].getText().equals("X"))
        ) {
            xWins(0, 4, 8);
        }
        if (
                (buttons[2].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[6].getText().equals("X"))
        ) {
            xWins(2, 4, 6);
        }
        // check o wins
        if (
                (buttons[0].getText().equals("O")) &&
                (buttons[1].getText().equals("O")) &&
                (buttons[2].getText().equals("O"))
        ) {
            oWins(0, 1, 2);
        }
        if (
                (buttons[3].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[5].getText().equals("O"))
        ) {
            oWins(3, 4, 5);
        }
        if (
                (buttons[6].getText().equals("O")) &&
                (buttons[7].getText().equals("O")) &&
                (buttons[8].getText().equals("O"))
        ) {
            oWins(6, 7, 8);
        }
        if (
                (buttons[0].getText().equals("O")) &&
                (buttons[3].getText().equals("O")) &&
                (buttons[6].getText().equals("O"))
        ) {
            oWins(0, 3, 6);
        }
        if (
                (buttons[1].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[7].getText().equals("O"))
        ) {
            oWins(1, 4, 7);
        }
        if (
                (buttons[2].getText().equals("O")) &&
                (buttons[5].getText().equals("O")) &&
                (buttons[8].getText().equals("O"))
        ) {
            oWins(2, 5, 8);
        }
        if (
                (buttons[0].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[8].getText().equals("O"))
        ) {
            oWins(0, 4, 8);
        }
        if (
                (buttons[2].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[6].getText().equals("O"))
        ) {
            oWins(2, 4, 6);
        }

        // Check for a tie
        boolean tie = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals("")) {
                tie = false;
                break;
            }
        }

        if (tie) {
            textfield.setText("It's a Tie!");
            disableButtons();
            return;
        }
    }

	  private void disableButtons() {
	        for (int i = 0; i < 9; i++) {
	            buttons[i].setEnabled(false);
	        }
	    }
	
	public void xWins(int a, int b, int c) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText(player1Name+" WINS");
	}
	
	public void oWins(int a, int b, int c) {
		
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText(player2Name+" WINS");
	}
	
	
	

}