package info.wonlee.assessment.genesys.config;
/***************************************************************
 * Copyright (c) 2020 Errigal Inc.
 *
 * This software is the confidential and proprietary information
 * of Errigal, Inc.  You shall not disclose such confidential
 * information and shall use it only in accordance with the
 * license agreement you entered into with Errigal.
 *
 ***************************************************************/

import info.wonlee.assessment.genesys.game.GameManager;
import info.wonlee.assessment.genesys.game.InMemoryGameManager;
import info.wonlee.assessment.genesys.player.InMemoryPlayerQueue;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Configuration
public class GameBeansConfig {
    @Bean(name = "playerQueue")
    @ConditionalOnProperty(value = "game.management.strategy", havingValue = "inMemory")
    public PlayerQueue inMemoryPlayerQueue(GameManager gameManager) {
        return new InMemoryPlayerQueue(gameManager);
    }

    @Bean(name = "gameManager")
    @ConditionalOnProperty(value = "game.management.strategy", havingValue = "inMemory")
    public GameManager inMemoryGameManager() {
        return new InMemoryGameManager();
    }
}
