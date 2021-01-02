package info.wonlee.assessment.genesys.game.manager;

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.game.evaluator.IllegalMoveException;
import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.game.evaluator.GameEvaluator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Component
public class InMemoryGameManager implements GameManager {
    private final Map<Player, Game> playerGameMap = new ConcurrentHashMap<>();
    private final GameEvaluator gameEvaluateService;

    public InMemoryGameManager(GameEvaluator gameEvaluateService) {
        this.gameEvaluateService = gameEvaluateService;
    }

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
        return gameEvaluateService.dropDisc(game, player, column);
    }

    @Override
    public Game playerDisconnected(Player player) {
        Game game = playerGameMap.get(player);
        if (game != null && game.getWinner() == null) {
            game.setWinner((game.getFirstPlayer().equals(player)) ? game.getSecondPlayer() : game.getFirstPlayer());
            game.setWinningCode("PLDISCON");
        }
        return game;
    }
}
