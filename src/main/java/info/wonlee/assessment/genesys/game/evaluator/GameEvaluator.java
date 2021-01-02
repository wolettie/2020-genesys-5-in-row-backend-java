package info.wonlee.assessment.genesys.game.evaluator;

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.player.Player;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User: wonlee
 * Date: 25/12/2020
 */

@Component
public class GameEvaluator {
    private final List<InARowFinder> finderList;

    public GameEvaluator(List<InARowFinder> finderList) {
        this.finderList = finderList;
    }

    /**
     *
     * @param player who is requesting move
     * @param column number (starts from 0) where player is dropping disc
     * @return
     * @throws IllegalMoveException
     */
    public Game dropDisc(Game game, Player player, int column) throws IllegalMoveException {
        if (!game.getCurrentPlayer().equals(player)) {
            throw new IllegalMoveException(
                    String.format("it is not turn of player %s (uuid: %s)", player.getUuid(), player.getUuid())
            );
        }
        final Player winner = game.getWinner();
        if (winner != null) {
            throw new IllegalMoveException(
                    String.format("game is already over, winner was %s (uuid: %s)", winner.getUuid(), winner.getUuid())
            );
        }
        final int[][] board = game.getBoard();
        if (column < 0 || column >= board.length) {
            throw new IllegalMoveException(String.format("column %d is invalid", column));
        }

        final Player firstPlayer = game.getFirstPlayer();
        final Player secondPlayer = game.getSecondPlayer();
        final Player currentPlayer = game.getCurrentPlayer();
        int[] row = board[column];
        int rowIndex = -1;
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 0) {
                row[i] = (player.equals(firstPlayer)) ? 1 : 2;
                rowIndex = i;
                break;
            }
        }
        if (rowIndex < 0) {
            throw new IllegalMoveException(String.format("column %d is already filled", column + 1));
        } else {
            game.setCurrentPlayer((currentPlayer.equals(firstPlayer)) ? secondPlayer : firstPlayer);
            game.setLastMoveAt(LocalDateTime.now());
        }

        for (InARowFinder finder: finderList) {
            final boolean winningMove = finder.find(board, column, rowIndex);
            if (winningMove) {
                game.setWinner(player);
                game.setWinningCode(
                        String.format("MOVE-%s-%d-%d", finder.getClass().getSimpleName(), rowIndex + 1, column + 1)
                );
                break;
            }
        }

        return game;
    }
}
