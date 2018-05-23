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

    private String pathDirectory = "C:\\Users\\catamorphism\\Documents\\GitHub\\NLInterfaceModule\\materials";

    private Map<String, String> elementsOfTree;

    private StopWords stopWords;
    private Parser parser;
    private NameFinderME nameFinder;

/*
* what
what's
when
when's
where
where's
which
while
who
who's
whom
why
why's
* */


    AnalysisQuestion(String _question) {
        question = _question;
        focusWords = new ArrayList<>();
        TagQuestion = null;
        supportWords = new HashMap<>();
        elementsOfTree = new HashMap<>();

        stopWords = createStopWordsParser();
        parser = createParserModel();
        nameFinder = createNameParser();
    }

    private NameFinderME createNameParser() {
        File modelName = new File(pathDirectory, "en-ner-person.bin");
        TokenNameFinderModel model = null;
        try {
            model = new TokenNameFinderModel(modelName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new NameFinderME(model);
    }

    private StopWords createStopWordsParser() {
        File StopWords = new File(pathDirectory, "stopwords.txt");
        return new StopWords(StopWords.getAbsolutePath());
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

    public String getQ() {
        return question;
    }

    private String[] tokenizeQ() {
        return WhitespaceTokenizer.INSTANCE.tokenize(question);
    }

    private String[] clearStopWordsQ() {
        String[] tokens = tokenizeQ();
        return stopWords.clear(tokens);
    }

    private String clearWord(String str) {
        return stopWords.clear(str);
    }

    private void findNameQ() {
        /*разбиваем вопрос на токены и очищаем от "шумных слов"*/
        String[] tokens = clearStopWordsQ();
        /*проводим сам поиск*/
        Span[] names = nameFinder.find(tokens);
        /*записываем имя в supportWords*/
        for (Span name : names) {
            supportWords.put("person", tokens[name.getStart()] + " " + tokens[name.getStart() + 1]);
        }
    }

    private void findFilmQ() {
        for (String e : elementsOfTree.keySet()) {
            String nameFilm = "";
            if (e.contains("NNP") || e.contains("NNPS")) {
                nameFilm += (elementsOfTree.get(e) + " ");
            }
            supportWords.put("film", nameFilm.trim());
        }
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
    private void getElementsTree() {
        question = Arrays.toString(clearStopWordsQ());
        if (parser != null) {
            Parse tree[] = ParserTool.parseLine(question, parser, 1);
            for (Parse parse : tree) {
                Parse element[] = parse.getChildren();
                for (Parse e : element) {
                    Parse tags[] = e.getTagNodes();
                    for (Parse tag : tags) {
                        if (elementsOfTree.containsKey(tag.getType()))
                            elementsOfTree.put(tag.getType() + " " + elementsOfTree.size(), tag.toString());
                        else
                            elementsOfTree.put(tag.getType(), tag.toString());
                    }
                }
            }
        }
        /*
         * очистим слова от лишних знаков пунттуации
         */
        for (Map.Entry e : elementsOfTree.entrySet()) {
            elementsOfTree.put(e.getKey().toString(), clearWord(e.getValue().toString()));
        }
    }

    /*
     * определение правила обработки для элементов дерева
     * */
    private void applyRulesForTreeElement() {
        for (Map.Entry<String, String> e : elementsOfTree.entrySet()) {
            Rules(e.getKey(), e.getValue());
        }
    }

    private void setTagQuestion(String tag) {
        TagQuestion = tag;
    }

    private void Rules(String type, String tag) {
        if (tag.equalsIgnoreCase("what")) {
            processWhatQuestion();
        }
        if (tag.equalsIgnoreCase("who")) {
            focusWords.add("person");
            processWhoQuestion();
        }
    }


    /*
     * Обработка вопроса Кто
     */
    private void processWhoQuestion() {
        boolean manyNames = false;
        if (elementsOfTree.containsKey("NN")) {
            String tagQuestion = elementsOfTree.get("NN").toLowerCase();
            switch (tagQuestion) {
                case "director":
                    focusWords.add("person");
                    setTagQuestion("directors");
                    break;
                case "writer":
                    focusWords.add("person");
                    setTagQuestion("writers");
                    break;
                case "played":
                    focusWords.add("person");
                    setTagQuestion("primaryname");
                    manyNames = true;
                    break;
            }
            if (manyNames) {
                findNameQ();
                for (Map.Entry e : elementsOfTree.entrySet()) {
                    if (supportWords.get("person").contains(e.getValue().toString())){
                        elementsOfTree.remove(e.getKey());
                    }
                }
                findFilmQ();
            } else
                findNameQ();
        } else {
            focusWords.add("person");
            setTagQuestion("primaryprofession");
            findNameQ();
        }


    }

    /*
     * Обработка вопроса Что | Какой
     */
    private void processWhatQuestion() {
        boolean namePerson = false;
        if (elementsOfTree.containsKey(".")) {
            String tagQuestion = elementsOfTree.get(".").toLowerCase();
            switch (tagQuestion) {
                case "rating":
                    focusWords.add("number");
                    setTagQuestion("averagerating");
                    break;
                case "genres":
                    focusWords.add("list");
                    setTagQuestion("genres");
                    break;
                case "cast":
                    focusWords.add("list");
                    setTagQuestion("cast");
                    break;
                case "star":
                    focusWords.add("list");
                    namePerson = true;
                    setTagQuestion("knownfortitles");
                default:
                    focusWords.add("film");
                    supportWords.put("language", elementsOfTree.get("."));
                    setTagQuestion("language");
                    break;
            }
            if (namePerson)
                findNameQ();
            else
                findFilmQ();
        } else if (elementsOfTree.containsKey("JJ")) {
            String tagQuestion = elementsOfTree.get("JJ").toLowerCase();
            for (String e : elementsOfTree.keySet()) {
                if (e.contains("NN")) {
                    switch (elementsOfTree.get(e).toLowerCase()) {
                        case "rating":
                            supportWords.put("power", elementsOfTree.get("JJ"));
                            setTagQuestion("averagerating");
                            break;
                        case "votes":
                            supportWords.put("power", elementsOfTree.get("JJ"));
                            setTagQuestion("numvotes");
                            break;
                        default:
                            setTagQuestion("no tag");
                            break;
                    }
                }
            }
            focusWords.add("film");
            findFilmQ();
        }
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

    public void analysis(){
        getElementsTree();
        applyRulesForTreeElement();
    }

}
