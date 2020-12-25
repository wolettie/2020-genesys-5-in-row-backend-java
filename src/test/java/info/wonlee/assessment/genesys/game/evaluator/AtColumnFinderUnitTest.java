package info.wonlee.assessment.genesys.game.evaluator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: wonlee
 * Date: 25/12/2020
 */

public class AtColumnFinderUnitTest {
    AtColumnFinder atColumnFinder = new AtColumnFinder();

    @ParameterizedTest
    @ValueSource(strings = {
            "1,1,1,1,1",
            "2,1,1,1,1,1",
            "2,2,2,2,2",
    })
    void test_simple_5_in_a_row_found(String columnString) {
        List<Integer> intList = Arrays.stream(columnString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        int[] straightBoard = new int[intList.size()];
        IntStream.range(0, intList.size()).forEach(index -> straightBoard[index] = intList.get(index));

        int[][] board = new int[][]{straightBoard};
        Assertions.assertTrue(atColumnFinder.find(board ,0,intList.size() - 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1,1,1,1,2",
            "2,2,1,1,1,1",
            "1,2,2,2,2",
    })
    void test_simple_5_in_a_row_not_found(String columnString) {
        List<Integer> intList = Arrays.stream(columnString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        int[] straightBoard = new int[intList.size()];
        IntStream.range(0, intList.size()).forEach(index -> straightBoard[index] = intList.get(index));

        int[][] board = new int[][]{straightBoard};
        Assertions.assertFalse(atColumnFinder.find(board ,0,intList.size() - 1));
    }

}
