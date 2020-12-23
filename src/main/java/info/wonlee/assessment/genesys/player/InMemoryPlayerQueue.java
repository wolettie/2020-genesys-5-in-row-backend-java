package info.wonlee.assessment.genesys.player;
/***************************************************************
 * Copyright (c) 2020 Errigal Inc.
 *
 * This software is the confidential and proprietary information
 * of Errigal, Inc.  You shall not disclose such confidential
 * information and shall use it only in accordance with the
 * license agreement you entered into with Errigal.
 *
 ***************************************************************/

import info.wonlee.assessment.genesys.game.manager.GameManager;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public class InMemoryPlayerQueue implements PlayerQueue {
    private final Queue<Player> playerQueue;
    private final GameManager gameManager;

    public InMemoryPlayerQueue(GameManager gameManager) {
        this.gameManager = gameManager;
        playerQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public Player register(Player player) {
        String uuid = UUID.randomUUID().toString();
        player.setUuid(uuid);
        final Player opponent = playerQueue.poll();
        if (opponent == null) {
            playerQueue.add(player);
        } else {
            gameManager.createGame(player, opponent);
        }
        return player;
    }
}
