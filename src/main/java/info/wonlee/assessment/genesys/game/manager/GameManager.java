package info.wonlee.assessment.genesys.game.manager;

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.game.evaluator.IllegalMoveException;
import info.wonlee.assessment.genesys.player.Player;

/**
 * This interface allows us to use different strategies of how to manage game instances.
 *
 * User: wonlee
 * Date: 23/12/2020
 */

public interface GameManager {
    Game createGame(Player player1, Player player2);
    Game gameStatus(Player player);
    Game dropDisc(Player player, int column) throws IllegalMoveException;

    /**
     * @param player who disconnected and set to loser
     * @return updated game object
     */
    Game playerDisconnected(Player player);
}
