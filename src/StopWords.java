import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
/*
    Удаление шумовых слов
*/

//TODO не работает, когда шумые слова повторяются друг за другом
public class StopWords {
    private String[] defaultStopWords = {"i", "a", "about", "an", "are", "as", "at", "be", "by", "com", "for", "from", "in", "is", "it", "of",
            "so", "the", "to", "will", "and"};

    private static HashSet stopWords = new HashSet();

    StopWords() {
        stopWords.addAll(Arrays.asList(defaultStopWords));
    }

    StopWords(String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while(bufferedReader.ready()) {
                stopWords.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] removeStopWords(String[] words) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(words));
        for (int i = 0; i < tokens.size(); i++) {
            if (stopWords.contains(tokens.get(i)))
                tokens.remove(i);
        }
        return tokens.toArray(new String[tokens.size()]);
    }
}
