package be.sjer.one16words.util;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CombinationFinder {
    private CombinationFinder(){}

    public static Set<String> findTargetLengthCombinations(Set<String> words, int targetLength) {
        Set<String> targetWords = words.stream()
                .filter(word -> word.length() == targetLength)
                .collect(Collectors.toSet());

        Set<String> combinations = new TreeSet<>();
        Queue<String> queue = new LinkedList<>();

        for (String word : words) {
            queue.offer(word);
        }

        while (!queue.isEmpty()) {
            String currentCombination = queue.poll();

            for (String word : words) {
                String newCombination = currentCombination + " " + word;
                String strippedNewCombo = newCombination.replaceAll("\\s+", "");

                if (targetWords.contains(strippedNewCombo)) {
                    combinations.add(buildFinalCombination(newCombination, strippedNewCombo));
                } else if (newCombination.length() < targetLength && isPrefixInSet(targetWords, strippedNewCombo)) {
                    queue.offer(newCombination);
                }
            }
        }
        return combinations;
    }

        public static boolean isPrefixInSet(Set <String> set, String prefix) {
            return set.stream()
                    .anyMatch(str -> str.startsWith(prefix));
        }

        public static String buildFinalCombination(String combination, String strippedCombo){
            StringBuilder sb = new StringBuilder();
            String[] words = combination.split(" ");
            for (int i = 0; i < words.length; i++) {
                if (i < words.length - 1) {
                    sb.append(words[i]).append("+");
                }
            }
            sb.append(words[words.length-1]).append("=").append(strippedCombo);
            return sb.toString();
        }
}