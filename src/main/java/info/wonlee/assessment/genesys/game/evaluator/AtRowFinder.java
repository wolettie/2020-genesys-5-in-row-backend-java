package info.wonlee.assessment.genesys.game.evaluator;

import org.springframework.stereotype.Component;

/**
 * Find 5 in a row at the specified row
 * User: wonlee
 * Date: 25/12/2020
 */

@Component
public class AtRowFinder implements InARowFinder {
    @Override
    public boolean find(final int[][] board, final int columnIndex, final int rowIndex) {
        int targetNumber = board[columnIndex][rowIndex]; // the number must lining 5 in a row, 1 or 2

        int matchCount = 0;
        for (int i = columnIndex; i >=0; i--) { // scroll left the row
            if (board[i][rowIndex] == targetNumber) {
                matchCount++;
            } else {
                break;
            }
        }
        for (int i = columnIndex + 1; i < board.length; i++) { // scroll right the row
            if (board[i][rowIndex] == targetNumber) {
                matchCount++;
            } else {
                break;
            }
        }

        return matchCount >= 5;
    }
}
