//Design Pattern: Utility Class
package src.view;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FuzzySearchConvertor {

    private static final String ABC = "abcdefghijklmnopqrstuvwxyz";
    private static final Map<String, Integer> dictionary = new HashMap<>();
    private static String DICTIONARY_VALUES = "";

    /**
     * Private constructor since class is a static helper class
     */
    private FuzzySearchConvertor() {
    }

    /**
     * Used within the GameDatabaseLoader to add valid words to the list of allowed dictionary values for fuzzy searching
     *
     * @param word String that is either a mechanic, name, or category
     */
    public static void addDictionaryValue(String word) {
        DICTIONARY_VALUES += word + ",";
    }

    /**
     * Populates the HashMap dictionary with the values from DICTIONARY_VALUES
     */
    public static void initializeFuzzySearch() {
        Stream.of(DICTIONARY_VALUES.toLowerCase().split(",")).forEach((word) -> dictionary.compute(word, (k, v) -> v == null ? 1 : v + 1));
    }

    /**
     * Function which calls necessary private helper functions to convert a word to its "fuzzy" counterpart up to 2 letters incorrect
     *
     * @param word String to be corrected
     * @return String word corrected fuzzily
     */
    public static String fuzzyCorrect(String word) {
        Optional<String> e1 = known(getSringStream(word)).max(Comparator.comparingInt(a -> dictionary.get(a)));
        if (e1.isPresent()) return dictionary.containsKey(word) ? word : e1.get();
        Optional<String> e2 = known(getSringStream(word).map(FuzzySearchConvertor::getSringStream).flatMap((x) -> x)).max(Comparator.comparingInt(a -> dictionary.get(a)));
        return (e2.orElse(word));
    }

    /**
     * Helper function which creates a Stream<String> which contains the potential errors (deletions, replacements, insertions, or transpositions)
     *
     * @param word String to be corrected
     * @return Stream of potential options
     */
    private static Stream<String> getSringStream(String word) {
        Stream<String> deletes = IntStream.range(0, word.length()).mapToObj((i) -> word.substring(0, i) + word.substring(i + 1));
        Stream<String> replaces = IntStream.range(0, word.length()).boxed().flatMap((i) -> ABC.chars().mapToObj((c) -> word.substring(0, i) + (char) c + word.substring(i + 1)));
        Stream<String> inserts = IntStream.range(0, word.length() + 1).boxed().flatMap((i) -> ABC.chars().mapToObj((c) -> word.substring(0, i) + (char) c + word.substring(i)));
        Stream<String> transposes = IntStream.range(0, word.length() - 1).mapToObj((i) -> word.substring(0, i) + word.substring(i + 1, i + 2) + word.charAt(i) + word.substring(i + 2));
        return Stream.of(deletes, replaces, inserts, transposes).flatMap((x) -> x);
    }

    private static Stream<String> known(Stream<String> words) {
        return words.filter((word) -> dictionary.containsKey(word));
    }

}
