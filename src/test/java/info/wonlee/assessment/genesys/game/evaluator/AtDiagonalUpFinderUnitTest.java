package info.wonlee.assessment.genesys.game.evaluator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: wonlee
 * Date: 25/12/2020
 */

public class AtDiagonalUpFinderUnitTest {
    AtDiagonalUpFinder atDiagonalUpFinder = new AtDiagonalUpFinder();

    // 1, 2, 2, 2, 2
    // 2, 1, 2, 2, 2
    // 2, 2, 2, 2, 2
    // 2, 2, 2, 1, 2
    // 2, 2, 2, 2, 1
    int[][] sampleBoard1 = new int[][] {
            new int[]{2,2,2,2,1},
            new int[]{2,2,2,1,2},
            new int[]{2,2,2,2,2},
            new int[]{2,1,2,2,2},
            new int[]{1,2,2,2,2},
    };

    @ParameterizedTest
    @ValueSource(strings = {
            "0,0", "1,1", "2,2", "3,3", "4,4",
    })
    void test_simple_board_1_success_test(String indicesString) {
        List<Integer> intList = Arrays.stream(indicesString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        Assertions.assertTrue(atDiagonalUpFinder.find(sampleBoard1, intList.get(0), intList.get(1)));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "0,1", "0,2", "0,3", "0,4",
            "1,0", "1,2", "1,3", "1,4",
            "2,0", "2,3", "2,3", "2,4",
            "3,0", "3,2", "3,4", "3,4",
            "4,0", "4,1", "4,2", "4,3",
    })
    void test_simple_board_1_failure_test(String indicesString) {
        List<Integer> intList = Arrays.stream(indicesString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        Assertions.assertFalse(atDiagonalUpFinder.find(sampleBoard1, intList.get(0), intList.get(1)));
    }

}
