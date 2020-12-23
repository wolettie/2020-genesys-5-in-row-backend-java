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

import info.wonlee.assessment.genesys.game.InMemoryGameManager;
import info.wonlee.assessment.genesys.player.InMemoryPlayerQueue;
import info.wonlee.assessment.genesys.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@ExtendWith(MockitoExtension.class)
public class InMemoryGameManagementUnitTest {
    InMemoryGameManager inMemoryGameManager;
    InMemoryPlayerQueue inMemoryPlayerQueue;

    @BeforeEach
    public void testSetup() {
        inMemoryGameManager = new InMemoryGameManager();
        inMemoryPlayerQueue = new InMemoryPlayerQueue(inMemoryGameManager);
    }

    @Test
    @DisplayName("Simple Entry Test")
    public void test_simple_entry_test() {
        final Player player = inMemoryPlayerQueue.register(new Player());
        Assertions.assertNotNull(player.getUuid());
    }

    @Test
    @DisplayName("Match Check")
    public void test_single_entry() {
        final Player player1 = inMemoryPlayerQueue.register(new Player());
        final Player player2 = inMemoryPlayerQueue.register(new Player());
        Assertions.assertNotNull(player1.getUuid());
        Assertions.assertNotNull(player2.getUuid());
        Assertions.assertNotNull(inMemoryGameManager.gameStatus(player1));
        Assertions.assertNotNull(inMemoryGameManager.gameStatus(player2));
    }
}
