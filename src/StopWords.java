import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;
/*
    Удаление шумовых слов
*/

//TODO не работает, когда шумые слова повторяются друг за другом
public class StopWords {
    private String[] defaultStopWords = {"i", "a", "about", "an", "are", "was", "as", "at", "be", "by", "com", "for", "from", "in", "is", "it", "of",
            "so", "the", "to", "will", "and"};

    private static ArrayList<String> stopWords = new ArrayList<>();

    private StopWords() {
        stopWords.addAll(asList(defaultStopWords));
    }

    StopWords(String fileName) {
        this();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while (bufferedReader.ready()) {
                stopWords.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] removeStopPunctuation(String[] words) {
        for (int i = 0; i < words.length; i++)
            words[i] = words[i].replaceAll("[^a-zA-Z]", "");
        return words;
    }

    private String[] removeStopWords(String[] words) {
        ArrayList<String> wordsList = new ArrayList<>();
        for (String word : words) {
            if (!stopWords.contains(word.toLowerCase()))
                wordsList.add(word);
        }
        return wordsList.toArray(new String[0]);
    }

    public String[] clear(String[] words) {
        words = removeStopPunctuation(words);
        words = removeStopWords(words);
        return words;
    }
}
