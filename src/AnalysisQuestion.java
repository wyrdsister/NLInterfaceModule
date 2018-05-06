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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/*
    Анализ вопроса
 */

public class AnalysisQuestion {

    private String question;
    private ArrayList<String> focusWords;
    private String TagQuestion;
    private ArrayList<String> supportWords;


    AnalysisQuestion(String _question) {
        question = _question;
        focusWords = new ArrayList<>();
        TagQuestion = null;
        supportWords = new ArrayList<>();
    }

    public String getQ() {
        return question;
    }

    public String[] getF() {
        return focusWords.toArray(new String[0]);
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

    public void findNameQ() {
//        String[] tokens = null;
//        try {
//            TokenNameFinderModel model = new TokenNameFinderModel(new File("C:\\Users\\catamorphism\\Documents\\GitHub\\NLInterfaceModule\\materials", "en-ner-person.bin"));
//            NameFinderME finderME = new NameFinderME(model);
//            tokens = tokenizeQ();
//            Span[] names = finderME.find(tokens);
        String namePerson = (question.substring(question.indexOf("'"), question.lastIndexOf("'") + 1));
        namePerson = namePerson.replaceAll("'", "");
        supportWords.add(namePerson);
        System.out.println(supportWords);


//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

    public void createTree() {
        Parser parser = createParserModel();
        if (parser != null) {
            Parse sent[] = ParserTool.parseLine(question, parser, 1);
            for (Parse p : sent) {
                p.show();
            }
        }
    }

    public void getElementTree() {
        Parser parser = createParserModel();
        Parse tree[] = ParserTool.parseLine(question, parser, 1);
        for (Parse parse : tree) {
            Parse element[] = parse.getChildren();
            for (Parse e : element) {
                Parse tags[] = e.getTagNodes();
                for (Parse tag : tags) {
                    System.out.println(tag + " " + tag.getType() + " " + tag.getText());
                    RulesForFocus(tag.toString(), tag.getType());
                }
            }
        }
    }


    public void setTagQuestion() {
        for (String element : focusWords) {
            //TODO реагирует на регистр
            if (element.contains("When")) {
                for (String word : supportWords) {
                    if (word.equalsIgnoreCase("born"))
                        TagQuestion = "birthyear";
                    if (word.equalsIgnoreCase("dead"))
                        TagQuestion = "deathyear";
                }

            }
        }
    }

    private void RulesForFocus(String tag, String type) {
        if (type.equals("WDT"))
            focusWords.add(tag);
        if (type.equals("WRB"))
            focusWords.add(tag);
        if (type.equals("NN"))
            focusWords.add(tag);
        if (type.equals("VBN") || type.equals("JJ"))
            supportWords.add(tag);
    }

    public String getTagQuestion() {
        return TagQuestion;
    }

    public ArrayList<String> getFocusWords() {
        return focusWords;
    }

    public ArrayList<String> getSupportWords() {
        return supportWords;
    }


}
