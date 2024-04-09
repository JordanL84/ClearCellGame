package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Department of Computer Science, UMCP
 */

public class ClearCellGame extends Game {
	
	private Random random;
	private int score;
	
	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.random = random;
		score = 0;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		if (board == null)
			return true;
		//Checks if each cell in the last row is empty
		for (int i = 0; i < board[0].length; i++) 
			if (board[board.length-1][i] != BoardCell.EMPTY)
				return true;
		return false;
	}

	public int getScore() {
		return score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		if (!(isGameOver())) {
			for (int row = board.length-1; row > 0; row--) {
				for (int col = 0; col < board[0].length; col++) 
					board[row][col] = board[row-1][col]; //board cell is moved one down
			}
			//New row of random BoardCell objects added to top
			for (int i = 0; i < board[0].length; i++) 
				board[0][i] = BoardCell.getNonEmptyRandomBoardCell(random);
		}
	}

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {
		BoardCell selected = board[rowIndex][colIndex];
		
		//Right
		for (int i = colIndex; i < board[0].length;i++) {
			if (board[rowIndex][i] == selected) {
				board[rowIndex][i] = BoardCell.EMPTY;
				score++;
			}
			else
				break;
		}
		
		//Left
		for (int i = colIndex-1; i >= 0; i--) {
			if (board[rowIndex][i] == selected) {
				board[rowIndex][i] = BoardCell.EMPTY;
				score++;
			}
			else
				break;
		}
		
		//up
		for (int i = rowIndex-1; i >= 0; i--) {
			if (board[i][colIndex] == selected) {
				board[i][colIndex] = BoardCell.EMPTY;
				score++;
			}
			else
				break;
		}
		
		//down
		for (int i = rowIndex+1; i < board.length; i++) {
			if (board[i][colIndex] == selected) {
				board[i][colIndex] = BoardCell.EMPTY;
				score++;
			}
			else
				break;
		}
		
		//diagonal right up
		int row = rowIndex-1;
		int col = colIndex+1;
		boolean target = true;
		while (row >= 0 && col < board[0].length && target) {
			if (board[row][col] == selected) {
				board[row][col] = BoardCell.EMPTY;
				score++;
				row--;
				col++;
			}
			else
				target = false;
		}
		
		//diagonal left up
		row = rowIndex-1;
		col = colIndex-1;
		target = true;
		while (row >= 0 && col >= 0 && target) {
			if (board[row][col] == selected) {
				board[row][col] = BoardCell.EMPTY;
				score++;
				row--;
				col--;
			}
			else
				target = false;
		}
		
		//diagonal right down
		row = rowIndex+1;
		col = colIndex+1;
		target = true;
		while (row < board.length && col < board[0].length && target) {
			if (board[row][col] == selected) {
				board[row][col] = BoardCell.EMPTY;
				score++;
				row++;
				col++;
			}
			else
				target = false;
		}
		
		//diagonal left down
		row = rowIndex+1;
		col = colIndex-1;
		target = true;
		while (row < board.length && col >= 0 && target) {
			if (board[row][col] == selected) {
				board[row][col] = BoardCell.EMPTY;
				score++;
				row++;
				col--;
			}
			else
				target = false;
		}
		
		//Collapsing empty rows
		boolean clear = false;
		for (int r = 0; r < board.length-1; r++) {
			boolean empty = true;
			for (BoardCell cell : board[r]) {
				if (cell != BoardCell.EMPTY) {
					empty = false;
					break;
				}
			}
			if (empty) {
				clear = true;
				for (int i = r; i < board.length-1; i++) {
					for (int k = 0; k < board[0].length; k++) 
						board[i][k] = board[i+1][k];
				}
			}
		}
		
		//if there was at least one empty row collapsed
		if (clear) {
			for (int i = 0; i < board[0].length; i++) 
				board[board.length-1][i] = BoardCell.EMPTY;
		}
	}
}