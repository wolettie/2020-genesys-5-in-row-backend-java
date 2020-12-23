package info.wonlee.assessment.genesys;
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
import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@SpringBootTest
public class InMemoryGameManagementIntegrationTest {
    @Autowired
    GameManager gameManager;
    @Autowired
    PlayerQueue playerQueue;

    @Test
    @DisplayName("Simple Entry Test")
    public void test_simple_entry_test() {
        final Player player = playerQueue.register(new Player());
        Assertions.assertNotNull(player.getUuid());
    }

    @Test
    @DisplayName("Match Check")
    public void test_single_entry() {
        final Player player1 = playerQueue.register(new Player());
        final Player player2 = playerQueue.register(new Player());
        Assertions.assertNotNull(player1.getUuid());
        Assertions.assertNotNull(player2.getUuid());
        Assertions.assertNotNull(gameManager.gameStatus(player1));
        Assertions.assertNotNull(gameManager.gameStatus(player2));
    }
}
