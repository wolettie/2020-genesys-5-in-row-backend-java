package info.wonlee.assessment.genesys.game.evaluator;

/**
 * This interface is to simply find 5 in a row
 * Validation of the dropped disk is NOT part of this class, it must be done before.
 *
 * Write any class implement this to be pure function.
 *
 * User: wonlee
 * Date: 25/12/2020
 */

public interface InARowFinder {
    /**
     *
     * @param board board of game
     * @param columnIndex column that the player dropped a disc
     * @param rowIndex row that a dropped disc stays
     * @return true if 5 in a row exists
     */
    boolean find(final int[][] board, final int columnIndex, final int rowIndex);
}
