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
import java.util.HashMap;
import java.util.Map;

/*
    Анализ вопроса
 */

public class AnalysisQuestion {

    private String question;
    private ArrayList<String> focusWords;
    private String TagQuestion;
    private Map<String, String> supportWords;

    String pathDirectory = "C:\\Users\\Моя госпожа\\Documents\\GitHub\\NLInterfaceModule\\materials";

    private Map<String, String> elementsOfTree;


    AnalysisQuestion(String _question) {
        question = _question;
        focusWords = new ArrayList<>();
        TagQuestion = null;
        supportWords = new HashMap<>();
        elementsOfTree = new HashMap<>();
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

    public String[] clearStopWordsQ() {
        File StopWords = new File(pathDirectory, "stopwords.txt");
        StopWords sw = new StopWords(StopWords.getAbsolutePath());
        String[] tokens = tokenizeQ();
        return sw.clear(tokens);

    }

    public void findNameQ() {
        /*разбиваем вопрос на токены и очищаем от "шумных слов"*/
        String[] tokens = clearStopWordsQ();
        /*создаем модель для поиска имен*/
        try {
            File modelName = new File(pathDirectory, "en-ner-person.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(modelName);
            NameFinderME nameFinder = new NameFinderME(model);
            /*проводим сам поиск*/
            Span[] names = nameFinder.find(tokens);
            /*записываем имя в supportWords*/
            for (Span name : names) {
                supportWords.put("name", tokens[name.getStart()] + " " + tokens[name.getStart() + 1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * создание модели для построения синтаксического дерева
     * */
    private Parser createParserModel() {
        try {
            InputStream modelInputStream = new FileInputStream(new File(pathDirectory, "en-parser-chunking.bin"));
            ParserModel model = new ParserModel(modelInputStream);
            return ParserFactory.create(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * отображение дерева
     * */
    public void createTree() {
        Parser parser = createParserModel();
        if (parser != null) {
            Parse sent[] = ParserTool.parseLine(question, parser, 1);
            for (Parse p : sent) {
                p.show();
            }
        }
    }

    /*
     * запись элементов дерева с тэгом и значением в Map-у
     * */
    public void getElementsTree() {
        Parser parser = createParserModel();
        if (parser != null) {
            Parse tree[] = ParserTool.parseLine(question, parser, 1);
            for (Parse parse : tree) {
                Parse element[] = parse.getChildren();
                for (Parse e : element) {
                    Parse tags[] = e.getTagNodes();
                    for (Parse tag : tags) {
//                    System.out.println(tag + " " + tag.getType() + " " + tag.getText());
                        elementsOfTree.put(tag.getType(), tag.toString());
                    }
                }
            }
        }
//        System.out.print(elementsOfTree);
    }

    /*
     * применение правил для элементов дерева
     * */
    private void applyRulesForTreeElement() {
        getElementsTree();
        for (Map.Entry<String, String> e : elementsOfTree.entrySet()) {
            RulesForFocus(e.getValue(), e.getKey());
            RulesForSupport(e.getValue(), e.getKey());
        }
    }

    private void RulesForSupport(String tag, String type) {
        if (type.equals("VR"))
            supportWords.put("condition-" + supportWords.size() + 1, tag);

    }


    public void setTagQuestion() {
        for (String element : focusWords) {
            //TODO реагирует на регистр
            if (element.toLowerCase().equalsIgnoreCase("when")) {
//                for (String word : supportWords) {
//                    if (word.equalsIgnoreCase("born"))
//                        TagQuestion = "birthyear";
//                    if (word.equalsIgnoreCase("dead"))
//                        TagQuestion = "deathyear";
            }
        }
//            if (element.equalsIgnoreCase("how")) {
//                for (String word : supportWords) {
//                    if (word.equalsIgnoreCase("old"))
//                        TagQuestion = "age";
//                }
//            }
//        }
    }


    private void RulesForFocus(String tag, String type) {
        if (type.equals("WDT"))
            focusWords.add(tag);
        if (type.equals("WRB"))
            focusWords.add(tag);
        if (type.equals("NN"))
            focusWords.add(tag);

    }

    public String getTagQuestion() {
        return TagQuestion;
    }

    public ArrayList<String> getFocusWords() {
        return focusWords;
    }

    public Map<String, String> getSupportWords() {
        return supportWords;
    }


}
