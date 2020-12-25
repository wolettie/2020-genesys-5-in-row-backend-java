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

public class AtRowFinderUnitTest {
    AtRowFinder atRowFinder = new AtRowFinder();

    // 0, 2, 2, 2, 2, 2, 0
    // 1, 1, 2, 2, 2, 2, 0
    int[][] sampleBoard1 = new int[][] {
            new int[]{1,0},
            new int[]{1,2},
            new int[]{2,2},
            new int[]{2,2},
            new int[]{2,2},
            new int[]{2,2},
            new int[]{0,0},
    };

    @ParameterizedTest
    @ValueSource(strings = {
            "1,1", "2,1", "3,1", "4,1", "5,1",
    })
    void test_simple_5_in_a_row_found(String indicesString) {
        List<Integer> intList = Arrays.stream(indicesString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        Assertions.assertTrue(atRowFinder.find(sampleBoard1, intList.get(0), intList.get(1)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0,0", "0,1", "1,0", "2,0", "3,0", "4,0", "5,0", "6,0", "6,1",
    })
    void test_simple_5_in_a_row_not_found_from_middle(String indicesString) {
        List<Integer> intList = Arrays.stream(indicesString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        Assertions.assertFalse(atRowFinder.find(sampleBoard1, intList.get(0), intList.get(1)));
    }

}
