package be.sjer.one16words.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationFinderTest {
    private final int targetLength = 5;
    @Test
    void emptySetAndTargetLengthZeroResultsInEmptySet() {
        Set<String> words = new HashSet<>();
        int targetLength = 0;
        Set<String> expectedCombinations = new HashSet<>();

        Set<String> actualCombinations = CombinationFinder.findTargetLengthCombinations(words, targetLength);

        assertThat(actualCombinations).hasSameSizeAs(expectedCombinations);
    }
    @Test
    void singleWordMatchingTargetLengthResultsInEmptySet() {
        Set<String> words = new HashSet<>();
        words.add("hello");
        Set<String> emptySet = new HashSet<>();
        Set<String> actualCombinations = CombinationFinder.findTargetLengthCombinations(words, targetLength);
        assertThat(actualCombinations).isEqualTo(emptySet);
    }
    @Test
    void multipleWordsMakingWordFromTargetSet() {
        Set<String> words = new HashSet<>();
        words.add("ld");
        words.add("world");
        words.add("wor");
        words.add("w");
        words.add("or");
        Set<String> expectedCombinations = new HashSet<>();
        expectedCombinations.add("w+or+ld=world");
        expectedCombinations.add("wor+ld=world");

        Set<String> actualCombinations = CombinationFinder.findTargetLengthCombinations(words, targetLength);

        assertThat(actualCombinations).containsAll(expectedCombinations);
    }
    @Test
    void targetLengthLongerThanLongestWordResultsInEmptySet() {
        Set<String> words = new HashSet<>();
        words.add("ld");
        words.add("world");
        words.add("wor");
        int length = 10;
        Set<String> expectedCombinations = new HashSet<>();

        Set<String> actualCombinations = CombinationFinder.findTargetLengthCombinations(words, length);

        assertThat(actualCombinations).hasSameSizeAs(expectedCombinations);
    }
    @Test
    void validPrefixButNoCombinationsResultsInEmptySet() {
        Set<String> words = new HashSet<>();
        words.add("coding");
        words.add("co");
        words.add("ing");
        int length = 6;
        Set<String> expectedCombinations = new HashSet<>();

        Set<String> actualCombinations = CombinationFinder.findTargetLengthCombinations(words, length);

        assertThat(actualCombinations).hasSameSizeAs(expectedCombinations);
    }
}