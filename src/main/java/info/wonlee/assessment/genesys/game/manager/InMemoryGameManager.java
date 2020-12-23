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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public class InMemoryGameManager implements GameManager {
    private final Map<Player, Game> playerGameMap = new ConcurrentHashMap<>();

    @Override
    public Game createGame(Player player1, Player player2) {
        Game game = new Game(player1, player2);
        playerGameMap.put(player1, game);
        playerGameMap.put(player2, game);
        return game;
    }

    @Override
    public Game gameStatus(Player player) {
        return playerGameMap.get(player);
    }

    @Override
    public Game dropDisc(Player player, int column) throws IllegalMoveException {
        Game game = playerGameMap.get(player);
        return game.dropDisc(player, column);
    }

    @Override
    public Game playerDisconnect(Player player) {
        Game game = playerGameMap.get(player);
        game.setWinner((game.getFirstPlayer().equals(player)) ? game.getSecondPlayer() : game.getFirstPlayer());
        return game;
    }
}
