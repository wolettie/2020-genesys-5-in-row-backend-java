package info.wonlee.assessment.genesys.game;

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
}
