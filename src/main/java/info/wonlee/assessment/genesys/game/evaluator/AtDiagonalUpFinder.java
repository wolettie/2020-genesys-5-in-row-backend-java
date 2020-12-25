package info.wonlee.assessment.genesys.game.evaluator;

import org.springframework.stereotype.Component;

/**
 * Find 5 in a row at to diagonal from bottom left to top right
 * User: wonlee
 * Date: 25/12/2020
 */

@Component
public class AtDiagonalUpFinder implements InARowFinder {
    @Override
    public boolean find(final int[][] board, final int columnIndex, final int rowIndex) {
        int matchCount = 0;
        matchCount += runThrough(true, board, columnIndex, rowIndex);
        matchCount += runThrough(false, board, columnIndex, rowIndex);

        return matchCount >= 5;
    }

    private int runThrough(final boolean assent, final int[][] board, final int columnIndex, final int rowIndex) {
        int targetNumber = board[columnIndex][rowIndex]; // the number must lining 5 in a row, 1 or 2

        int numOfOp = (assent) ? 0 : 1; // we run both assent & descent so we hit the same index twice if this is simply set 0 all the time.
        int multiplier = (assent) ? 1 : -1;

        int matchCount = 0;
        while(true) {

            final int ci = columnIndex + (numOfOp * multiplier);
            final int ri = rowIndex + (numOfOp * multiplier);
            if (ci < 0 || ci >= board.length || ri < 0 || ri >= board[ci].length) {
                break; // one of index pointing outside board;
            }

            if (targetNumber != board[ci][ri]) {
                break; // wrong number;
            }

            numOfOp++;
            matchCount++;
        }

        return matchCount;
    }
}
