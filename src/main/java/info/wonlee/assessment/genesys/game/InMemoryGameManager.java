package info.wonlee.assessment.genesys.game;
/***************************************************************
 * Copyright (c) 2020 Errigal Inc.
 *
 * This software is the confidential and proprietary information
 * of Errigal, Inc.  You shall not disclose such confidential
 * information and shall use it only in accordance with the
 * license agreement you entered into with Errigal.
 *
 ***************************************************************/

import info.wonlee.assessment.genesys.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public class InMemoryGameManager implements GameManager {
    private final Map<Player, String> playerGameMap = new ConcurrentHashMap<>();
    private final Map<String, Game> uuidGameMap = new ConcurrentHashMap<>();

    @Override
    public Game createGame(Player player1, Player player2) {
        String gameUuid = UUID.randomUUID().toString();
        playerGameMap.put(player1, gameUuid);
        playerGameMap.put(player2, gameUuid);
        Game game = new Game();
        game.setUuid(gameUuid);
        uuidGameMap.put(gameUuid, game);
        return game;
    }

    @Override
    public Game gameStatus(Player player) {
        final String gameUuid = playerGameMap.get(player);
        return uuidGameMap.get(gameUuid);
    }
}
