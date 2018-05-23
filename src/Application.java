//import edu.stanford.nlp.ling.HasWord;
//import edu.stanford.nlp.pipeline.Annotation;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import edu.stanford.nlp.process.DocumentPreprocessor;


import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;


import java.io.File;
import java.io.IOException;

import java.sql.SQLException;
import java.util.*;


/*ПРИМЕРЫ
*    When was born 'Fred Astaire'?
*    How old are 'Fred Astaire'?
*
* */
public class Application {

    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        LogicApplication l = new LogicApplication();
        while(true) {
            try {
                System.out.print(">>");
                String str = in.nextLine().trim();

                if(str.equalsIgnoreCase("exit"))
                    return;
                else
                    l.start(str);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void findPartOfSpeech(String text) {
        // разбиение на части речи
        POSModel model = new POSModelLoader().load(new File("C:\\OpenNLP Models", "en-pos-maxent.bin"));
        POSTaggerME taggerME = new POSTaggerME(model);
        String[] tokens = WhitespaceTokenizer.INSTANCE.tokenize(text);
        String[] tags = taggerME.tag(tokens);
        for (int i = 0; i < tokens.length; i++) {
            System.out.print(tokens[i] + "[" + tags[i] + "]");
        }
    }

    private static void findName(String sentence) {
        //поиск именованных объектов
        try {
            Tokenizer t = SimpleTokenizer.INSTANCE;
            TokenNameFinderModel model = new TokenNameFinderModel(new File("C:\\OpenNLP Models", "en-ner-person.bin"));
            NameFinderME finderME = new NameFinderME(model);
            String[] tokens = t.tokenize(sentence);
            Span[] names = finderME.find(tokens);
            System.out.println(Arrays.toString(Span.spansToStrings(names, tokens)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
