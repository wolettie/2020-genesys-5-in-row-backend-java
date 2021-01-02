package info.wonlee.assessment.genesys.service;

import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * User: wonlee
 * Date: 31/12/2020
 */

@Slf4j
@Service
public class GameCleaningService {
    private final PlayerQueue playerQueue;
    private final int cleanupMinutes;

    public GameCleaningService(PlayerQueue playerQueue,
                               @Value("${player.cleanup.minutes}") Integer minutes) {
        this.playerQueue = playerQueue;
        this.cleanupMinutes = minutes;
    }

    @Scheduled(fixedDelay = 15000)
    private void checkGameStatus() {
        final Collection<Player> players = playerQueue.removeStallPlayer(cleanupMinutes);
        log.info("currently {} players registered, {} players removed: {}",
            playerQueue.count(),
            players.size(),
            players.stream().map(
                    it -> String.format("{%s: %s}", it.getUuid(), it.getName())
            ).collect(Collectors.joining(","))
        );

    }
}
