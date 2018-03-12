import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
/*
    Удаление шумовых слов
*/

public class StopWords
{
    private String[] defaultStopWords = {"i", "a", "about", "an", "are", "as", "at", "be", "by", "com", "for", "from", "in", "is", "it", "of",
            "so", "the", "to", "will", "and"};

    private static HashSet stopWords = new HashSet();

    public StopWords() {
        stopWords.addAll(Arrays.asList(defaultStopWords));
    }

    public StopWords(String fileName){

    }

    public String[] removeStopWords(String[] words){
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(words));
        for (int i = 0; i < tokens.size(); i++){
            if(stopWords.contains(tokens.get(i)))
                tokens.remove(i);
        }
        return (String[])tokens.toArray(new String[tokens.size()]);
    }

}
