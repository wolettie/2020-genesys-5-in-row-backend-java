package info.wonlee.assessment.genesys.player;

import info.wonlee.assessment.genesys.game.manager.GameManager;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public class InMemoryPlayerQueue implements PlayerQueue {
    private final GameManager gameManager;
    private final Queue<Player> playerQueue;
    private final Map<String, Player> playerMap;

    public InMemoryPlayerQueue(GameManager gameManager) {
        this.gameManager = gameManager;
        playerQueue = new ConcurrentLinkedQueue<>();
        playerMap = new ConcurrentHashMap<>();
    }

    @Override
    public Player register(Player player) {
        String uuid = UUID.randomUUID().toString();
        player.setUuid(uuid);
        Player opponent = null;
        while (!playerQueue.isEmpty()) { // search opponent while queue is not
            opponent = playerQueue.poll();
            if (playerMap.containsKey(opponent.getUuid())) {
                gameManager.createGame(player, opponent);
                break;
            }
        }
        if (opponent == null) {
            playerQueue.add(player);
        }
        playerMap.put(uuid, player);
        return player;
    }

    @Override
    public Player findByUuid(String uuid) {
        return playerMap.get(uuid);
    }

    @Override
    public void playerDisconnected(Player player) {
        playerMap.remove(player.getUuid());
        gameManager.playerDisconnected(player);
    }
}
