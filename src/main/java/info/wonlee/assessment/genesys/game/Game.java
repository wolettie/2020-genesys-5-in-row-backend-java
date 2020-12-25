package info.wonlee.assessment.genesys.game;

import info.wonlee.assessment.genesys.game.evaluator.IllegalMoveException;
import info.wonlee.assessment.genesys.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Data
@NoArgsConstructor
public class Game {
    private String uuid;

    private Player firstPlayer; // to be presented as 1 in board
    private Player secondPlayer; // to be presented as 2 in board

    private Player currentPlayer;

    private Player winner;

    private int[][] board = new int[9][6]; // column & row, where row index:0 means bottom of column

    private LocalDateTime startedAt = LocalDateTime.now();
    private LocalDateTime lastMoveAt = LocalDateTime.now();

    public Game(Player player1, Player player2) {
        this.uuid = UUID.randomUUID().toString();
        final boolean shuffle = new Random().nextInt() % 2 == 0;
        this.firstPlayer = (shuffle) ? player1 : player2;
        this.secondPlayer = (shuffle) ? player2 : player1;
        this.currentPlayer = this.firstPlayer;
    }

    /**
     *
     * @param player who is requesting move
     * @param column number (starts from 0) where player is dropping disc
     * @return
     * @throws IllegalMoveException
     */
    public Game dropDisc(Player player, int column) throws IllegalMoveException {
        if (!currentPlayer.equals(player)) {
            throw new IllegalMoveException(
                    String.format("it is not turn of player %s (uuid: %s)", player.getUuid(), player.getUuid())
            );
        }
        if (winner != null) {
            throw new IllegalMoveException(
                    String.format("game is already over, winner was %s (uuid: %s)", winner.getUuid(), winner.getUuid())
            );
        }
        if (column < 0 || column >= board.length) {
            throw new IllegalMoveException(String.format("column %d is invalid", column));
        }

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
            throw new IllegalMoveException(String.format("column %d is already filled", column));
        } else {
            currentPlayer = (currentPlayer.equals(firstPlayer)) ? secondPlayer : firstPlayer;
            lastMoveAt = LocalDateTime.now();
        }

        return this;
    }
}
