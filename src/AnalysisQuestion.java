import javafx.beans.binding.MapBinding;
import javafx.collections.ObservableMap;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/*
    Анализ вопроса
 */

public class AnalysisQuestion {

    private String question;
    private ArrayList<String> main_focus;
    private String[][] elementTree;


    AnalysisQuestion(String _question) {
        question = _question;
        main_focus = new ArrayList<>();
    }

    public String getQ() {
        return question;
    }

    public String[] getF() {
        return main_focus.toArray(new String[0]);
    }

    private String[] tokenizeQ() {
        return WhitespaceTokenizer.INSTANCE.tokenize(question);
    }

    public String[] clearQ() {
        StopWords sw = new StopWords("C:\\Users\\Моя госпожа\\Documents\\GitHub\\NLInterfaceModule\\materials\\stopwords.txt");
        String[] tokens = tokenizeQ();
        findNameQ();
        return sw.removeStopWords(tokens);
    }

    private void findNameQ() {
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
    }

    private Parser createParserModel() {
        String fileLocation = "C:\\Users\\catamorphism\\Documents\\GitHub\\NLInterfaceModule\\materials\\en-parser-chunking.bin";
        try {
            InputStream modelInputStream = new FileInputStream(fileLocation);
            ParserModel model = new ParserModel(modelInputStream);
            return ParserFactory.create(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTree(){
        Parser parser = createParserModel();
        if (parser != null) {
            Parse sent[] = ParserTool.parseLine(question, parser, 1);
            for(Parse p : sent){
                p.showCodeTree();
            }
        }
    }

    private void getElementTree(){
        Parser parser = createParserModel();
        Parse tree[] = ParserTool.parseLine(question, parser, 1);
        for(Parse parse : tree){
            Parse element[] = parse.getChildren();
            elementTree = new String[element.length][element.length];
            for (Parse e : element){
                Parse tags[] = e.getTagNodes();
                for(Parse tag : tags){
                    elementTree[1][1] = Arrays.toString(new Parse[]{tag, Parse.parseParse(tag.getType())});
                }
            }
        }
    }

}
