package info.wonlee.assessment.genesys.game.evaluator;

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

/**
 * User: wonlee
 * Date: 25/12/2020
 */

@ExtendWith(MockitoExtension.class)
public class GameEvaluatorUnitTest {
    InARowFinder finder = new AtColumnFinder();
    GameEvaluator gameEvaluator = new GameEvaluator(Arrays.asList(new InARowFinder[]{finder}));

    Player player1;
    Player player2;

    Game game;

    @BeforeEach
    public void testSetup() {
        player1 = new Player();
        player2 = new Player();

        player1.setUuid(UUID.randomUUID().toString());
        player2.setUuid(UUID.randomUUID().toString());
        player1.setName("player1");
        player2.setName("player2");

        game = new Game();
        game.setFirstPlayer(player1);
        game.setSecondPlayer(player2);
        game.setCurrentPlayer(player1);
    }


    @Test
    public void test_wrong_player_move() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            gameEvaluator.dropDisc(game, player2, 0);
        });

        Assertions.assertTrue(exception.getMessage().contains("it is not turn"));
    }

    @Test
    public void test_minus_column_number() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            gameEvaluator.dropDisc(game, player1, -1);
        });

        Assertions.assertEquals("column -1 is invalid", exception.getMessage());
    }

    @Test
    public void test_excess_column_number() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            gameEvaluator.dropDisc(game, player1, 2000);
        });

        Assertions.assertEquals("column 2000 is invalid", exception.getMessage());
    }

    @Test
    public void test_overfill_column_number() throws IllegalMoveException {
        gameEvaluator.dropDisc(game, player1, 3);
        gameEvaluator.dropDisc(game, player2, 3);
        gameEvaluator.dropDisc(game, player1, 3);
        gameEvaluator.dropDisc(game, player2, 3);
        gameEvaluator.dropDisc(game, player1, 3);
        gameEvaluator.dropDisc(game, player2, 3);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            gameEvaluator.dropDisc(game, player1, 3); //7th
        });

        Assertions.assertEquals("column 4 is already filled", exception.getMessage());
    }
}
