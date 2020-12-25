package info.wonlee.assessment.genesys.game.evaluator;

import org.springframework.stereotype.Component;

/**
 * Find 5 in a row at the specified column
 * User: wonlee
 * Date: 25/12/2020
 */

@Component
public class AtColumnFinder implements InARowFinder {
    @Override
    public boolean find(final int[][] board, final int columnIndex, final int rowIndex) {
        int targetNumber = board[columnIndex][rowIndex]; // the number must lining 5 in a row, 1 or 2
        int[] theColumns = board[columnIndex];

        int matchCount = 0;
        for (int i = rowIndex; i >=0; i--) { // scroll down the column
            if (theColumns[i] == targetNumber) {
                matchCount++;
            } else {
                break;
            }
        }

        return matchCount >= 5;
    }
}
