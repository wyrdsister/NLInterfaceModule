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
    private String[] defaultStopWords = {"i", "a", "about", "an", "are", "as", "at", "be", "by", "com", "for", "from", "in", "is", "it", "of",
            "so", "the", "to", "will", "and"};

    private String[] StopPunctuation = {".", ",", "@", "/", "?", "!", "'"};

    private static ArrayList<String> stopWords = new ArrayList<>();

    StopWords() {
        stopWords.addAll(asList(defaultStopWords));
    }

    StopWords(String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while (bufferedReader.ready()) {
                stopWords.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] removeStopPunctuation(String[] words){
        ArrayList<String> tokens = new ArrayList<>(asList(words));
        for (int i = 0; i < words.length; i++) {
            if (asList(StopPunctuation).contains(tokens.get(i)));
            tokens.remove(i);
        }

        return words;
    }

    private String[] removeStopWords(String[] words) {
        ArrayList<String> tokens = new ArrayList<>(asList(words));
        for (String token : tokens) {
            List<String> t = Arrays.asList(token);
            for (int j = 0; j < t.size(); j++) {
                if (stopWords.contains(t.get(j)))
                    t.remove(j);
            }
            token = t.toString();
        }
        return tokens.toArray(new String[0]);
    }

    public String[] clear(String[] words){
        words = removeStopPunctuation(words);
        words = removeStopWords(words);
        return words;
    }

    public String[] removeStopWords_(String[] words) {
        for (String word : words) {
            ArrayList<Character> arrayChars = getArrayChar(word);
            for(int i = 0; i < arrayChars.size(); i++) {
                if (arrayChars.get(i).equals(stopWords))
                    arrayChars.remove(i);
            }
            word = arrayChars.toString();
        }
        return words;
    }

    private ArrayList<Character> getArrayChar(String word){
        ArrayList<Character> chars = new ArrayList<Character>();
        for (char c : word.toCharArray()) {
            chars.add(c);
        }
        return chars;
    }
}
