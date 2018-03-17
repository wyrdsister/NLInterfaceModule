import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;
import opennlp.tools.lemmatizer.LemmaSample;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/*
    Анализ вопроса
 */

public class AnalysisQuestion {

    private String question;
    private ArrayList<String> main_focus;



    AnalysisQuestion(String _question){
        question = _question;
        main_focus = new ArrayList<>();
    }

    public String getQ(){
        return question;
    }

    public String[] getF(){
        return main_focus.toArray(new String[0]);
    }

    private String [] tokenizeQ(){
        return WhitespaceTokenizer.INSTANCE.tokenize(question);
    }

    public String[] clearQ(){
        StopWords sw = new StopWords("C:\\Users\\Моя госпожа\\Documents\\GitHub\\NLInterfaceModule\\materials\\stopwords.txt");
        String[] tokens = tokenizeQ();
        findNameQ();
        return sw.removeStopWords(tokens);
    }

    private String[] findNameQ(){
        String[] tokens = null;
        try {
            TokenNameFinderModel model = new TokenNameFinderModel(new File("C:\\OpenNLP Models", "en-ner-person.bin"));
            NameFinderME finderME = new NameFinderME(model);
            tokens = tokenizeQ();
            Span[] names = finderME.find(tokens);
            main_focus.add(Arrays.toString(Span.spansToStrings(names, tokens)));
            //TODO удалить эти имена из списка


        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }



    //что-то не так с нужным классом
    // найти другой способ или подумать об необходимости данной затеи
    private void  lemmatize(String word, String postag) {

    }


}
