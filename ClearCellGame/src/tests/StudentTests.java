package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.BoardCell;
import model.ClearCellGame;
import model.Game;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
	
	@Test
	public void testGame() {
		int maxRows = 8, maxCols = 8, strategy = 1;
		Game ccGame = new ClearCellGame(maxRows, maxCols, new Random(1L), strategy);

		ccGame.setBoardWithColor(BoardCell.BLUE);
		ccGame.setRowWithColor(maxRows - 1, BoardCell.EMPTY);
		ccGame.setRowWithColor(1, BoardCell.YELLOW);
		ccGame.setBoardCell(1, maxCols - 1, BoardCell.RED);
		ccGame.setRowWithColor(3, BoardCell.GREEN);
		ccGame.setRowWithColor(6, BoardCell.RED);
		
		assertEquals(8, ccGame.getMaxRows());
		assertEquals(8, ccGame.getMaxCols());
		assertEquals(BoardCell.GREEN, ccGame.getBoardCell(3, 3));
	}
	
	@Test
	public void testClearCellGame1() {
		int maxRows = 8, maxCols = 8, strategy = 1;
		Game ccGame = new ClearCellGame(maxRows, maxCols, new Random(1L), strategy);

		ccGame.setBoardWithColor(BoardCell.BLUE);
		ccGame.setRowWithColor(maxRows - 1, BoardCell.EMPTY);
		ccGame.setRowWithColor(1, BoardCell.YELLOW);
		ccGame.setBoardCell(1, maxCols - 1, BoardCell.RED);
		ccGame.setRowWithColor(3, BoardCell.GREEN);
		ccGame.setRowWithColor(6, BoardCell.RED);
		ccGame.setColWithColor(4, BoardCell.YELLOW);
		ccGame.setColWithColor(3, BoardCell.YELLOW);
		
		ccGame.processCell(1, 3);
		ccGame.processCell(4, 2);
		assertEquals(21, ccGame.getScore());
		
		//System.out.println(getBoardStr(ccGame));
		Game answerGame = new ClearCellGame(maxRows, maxCols, new Random(1L), strategy);
		answerGame.setRowWithColor(0, BoardCell.BLUE);
		//answerGame.setRowWithColor(1, BoardCell.EMPTY);
		answerGame.setRowWithColor(2, BoardCell.EMPTY);
		answerGame.setRowWithColor(3, BoardCell.GREEN);
		answerGame.setRowWithColor(2, BoardCell.BLUE);
		answerGame.setRowWithColor(4, BoardCell.EMPTY);
		answerGame.setRowWithColor(5, BoardCell.BLUE);
		answerGame.setRowWithColor(6, BoardCell.RED);
		answerGame.setColWithColor(3, BoardCell.EMPTY);
		answerGame.setColWithColor(4, BoardCell.YELLOW);
		answerGame.setBoardCell(0, 4, BoardCell.EMPTY);
		answerGame.setBoardCell(1,  7, BoardCell.RED);
		answerGame.setBoardCell(1, 4, BoardCell.EMPTY);
		answerGame.setBoardCell(2, 4, BoardCell.EMPTY);
		answerGame.setBoardCell(5, 1, BoardCell.EMPTY);
		answerGame.setBoardCell(5, 2, BoardCell.EMPTY);
		answerGame.setBoardCell(4, 5, BoardCell.BLUE);
		answerGame.setBoardCell(4, 6, BoardCell.BLUE);
		answerGame.setBoardCell(4, 7, BoardCell.BLUE);
		//System.out.println(getBoardStr(answerGame));
		
		assertEquals(getBoardStr(answerGame), getBoardStr(ccGame));
	}
	
	public void testClearCellGame2() {
		int maxRows = 4, maxCols = 4, strategy = 1;
		Game ccGame = new ClearCellGame(maxRows, maxCols, new Random(1L), strategy);
		ccGame.setRowWithColor(2, BoardCell.BLUE);
		ccGame.nextAnimationStep();
		
		Game answerGame = new ClearCellGame(maxRows, maxCols, new Random(1L), strategy);
		answerGame.setRowWithColor(3, BoardCell.BLUE);
		
		assertEquals(getBoardStr(answerGame), getBoardStr(ccGame));
	}
	
	private static String getBoardStr(Game game) {
		int maxRows = game.getMaxRows(), maxCols = game.getMaxCols();

		String answer = "Board(Rows: " + maxRows + ", Columns: " + maxCols + ")\n";
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				answer += game.getBoardCell(row, col).getName();
			}
			answer += "\n";
		}

		return answer;
	}
}
