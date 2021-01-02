package info.wonlee.assessment.genesys.player;

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.game.manager.GameManager;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Slf4j
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
        log.info("registering player {}", player.getName());
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
    public Game playerDisconnected(Player player) {
        playerMap.remove(player.getUuid());
        return gameManager.playerDisconnected(player);
    }

    @Override
    public String checkin(Player player) {
        String responseCode;
        if (playerMap.containsKey(player.getUuid())) {
            playerMap.get(player.getUuid()).setLastCheckedIn(LocalDateTime.now());
            responseCode = "OK-CHECKED_IN";
        } else {
            responseCode = "ERR-PLAYER_NOT_FOUNT";
        }
        return responseCode;
    }

    @Override
    public Collection<Player> removeStallPlayer(int minutes) {
        List<Player> playerToRemove = new LinkedList<>();
        final LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(minutes);
        playerMap.forEach((uuid, player) -> {
            if (player.getLastCheckedIn().isBefore(oneMinuteAgo)) {
                playerToRemove.add(player);
            }
        });
        playerToRemove.forEach(player -> {
            gameManager.playerDisconnected(player);
            playerMap.remove(player.getUuid());
        });
        return playerToRemove;
    }

    @Override
    public long count() {
        return playerMap.size();
    }
}
