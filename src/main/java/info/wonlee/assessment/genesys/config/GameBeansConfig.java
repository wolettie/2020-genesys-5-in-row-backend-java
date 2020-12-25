package info.wonlee.assessment.genesys.config;

import info.wonlee.assessment.genesys.game.manager.GameManager;
import info.wonlee.assessment.genesys.game.manager.InMemoryGameManager;
import info.wonlee.assessment.genesys.player.InMemoryPlayerQueue;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ConditionalOnProperty} is used for fine tune strategy.
 * If we force to use the same strategy across the beans,
 * using {@link org.springframework.context.annotation.Profile} and create config class per profile would be more simple.
 *
 *
 * User: wonlee
 * Date: 23/12/2020
 */

@Configuration
public class GameBeansConfig {
    @Bean(name = "playerQueue")
    @ConditionalOnProperty(value = "playerQueue.strategy", havingValue = "inMemory")
    public PlayerQueue inMemoryPlayerQueue(GameManager gameManager) {
        return new InMemoryPlayerQueue(gameManager);
    }

    @Bean(name = "gameManager")
    @ConditionalOnProperty(value = "gameManagement.strategy", havingValue = "inMemory")
    public GameManager inMemoryGameManager() {
        return new InMemoryGameManager();
    }
}
