package info.wonlee.assessment.genesys.player;

import info.wonlee.assessment.genesys.game.Game;

import java.util.Collection;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public interface PlayerQueue {
    Player register(Player player);
    Player findByUuid(String uuid);
    long count();

    /**
     * Checkin user
     * @param player who is checked in
     * @return code that describe users status, OK-CHECKED_IN for OK
     * ERR prefix suggest issue
     */
    String checkin(Player player);

    /**
     * Remove any players from game if they are determined to be inactive
     * @param minutes that user can be inactive
     * @return list of players determined to be inactive
     */
    Collection<Player> removeStallPlayer(int minutes);

    /**
     *
     * @param player player who declare disconnected
     * @return Game result
     */
    Game playerDisconnected(Player player);
}
