package info.wonlee.assessment.genesys.game.manager;
/***************************************************************
 * Copyright (c) 2020 Errigal Inc.
 *
 * This software is the confidential and proprietary information
 * of Errigal, Inc.  You shall not disclose such confidential
 * information and shall use it only in accordance with the
 * license agreement you entered into with Errigal.
 *
 ***************************************************************/

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.game.evaluator.IllegalMoveException;
import info.wonlee.assessment.genesys.player.Player;

/**
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
    Game playerDisconnect(Player player);
}
