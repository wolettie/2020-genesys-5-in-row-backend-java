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

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.game.evaluator.IllegalMoveException;
import info.wonlee.assessment.genesys.game.manager.InMemoryGameManager;
import info.wonlee.assessment.genesys.player.InMemoryPlayerQueue;
import info.wonlee.assessment.genesys.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@ExtendWith(MockitoExtension.class)
public class InMemoryGameManagerUnitTest {
    InMemoryGameManager inMemoryGameManager;
    InMemoryPlayerQueue inMemoryPlayerQueue;

    Player player1;
    Player player2;

    @BeforeEach
    public void testSetup() {
        inMemoryGameManager = new InMemoryGameManager();
        inMemoryPlayerQueue = new InMemoryPlayerQueue(inMemoryGameManager);
        player1 = inMemoryPlayerQueue.register(new Player());
        player2 = inMemoryPlayerQueue.register(new Player());
    }

    @Test void test_share_the_same_game() {
        Assertions.assertEquals(
                inMemoryGameManager.gameStatus(player1),
                inMemoryGameManager.gameStatus(player2)
        );
    }

    @Test
    public void test_wrong_player_move() {
        Game game = inMemoryGameManager.gameStatus(player1);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            inMemoryGameManager.dropDisc(game.getSecondPlayer(), 0);
        });

        Assertions.assertTrue(exception.getMessage().contains("it is not turn"));
    }

    @Test
    public void test_minus_column_number() {
        Game game = inMemoryGameManager.gameStatus(player1);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            inMemoryGameManager.dropDisc(game.getFirstPlayer(), -1);
        });

        Assertions.assertEquals("column -1 is invalid", exception.getMessage());
    }

    @Test
    public void test_excess_column_number() {
        Game game = inMemoryGameManager.gameStatus(player1);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            inMemoryGameManager.dropDisc(game.getFirstPlayer(), 2000);
        });

        Assertions.assertEquals("column 2000 is invalid", exception.getMessage());
    }

    @Test
    public void test_overfill_column_number() throws IllegalMoveException {
        Game game = inMemoryGameManager.gameStatus(player1);
        inMemoryGameManager.dropDisc(game.getFirstPlayer(), 3);
        inMemoryGameManager.dropDisc(game.getSecondPlayer(), 3);
        inMemoryGameManager.dropDisc(game.getFirstPlayer(), 3);
        inMemoryGameManager.dropDisc(game.getSecondPlayer(), 3);
        inMemoryGameManager.dropDisc(game.getFirstPlayer(), 3);
        inMemoryGameManager.dropDisc(game.getSecondPlayer(), 3); // 6th

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            inMemoryGameManager.dropDisc(game.getFirstPlayer(), 3); // 7th
        });

        Assertions.assertEquals("column 3 is already filled", exception.getMessage());
    }
}
