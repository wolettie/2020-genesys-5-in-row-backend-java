package info.wonlee.assessment.genesys.game;

import info.wonlee.assessment.genesys.game.manager.InMemoryGameManager;
import info.wonlee.assessment.genesys.player.InMemoryPlayerQueue;
import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.game.evaluator.GameEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean
    GameEvaluator gameEvaluateService;

    @BeforeEach
    public void testSetup() {
        inMemoryGameManager = new InMemoryGameManager(gameEvaluateService);
        inMemoryPlayerQueue = new InMemoryPlayerQueue(inMemoryGameManager);
        player1 = inMemoryPlayerQueue.register(new Player());
        player2 = inMemoryPlayerQueue.register(new Player());
    }

    @Test
    void test_share_the_same_game() {
        Assertions.assertEquals(
                inMemoryGameManager.gameStatus(player1),
                inMemoryGameManager.gameStatus(player2)
        );
    }}
