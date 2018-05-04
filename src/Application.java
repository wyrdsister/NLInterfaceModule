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
import java.io.StringReader;
import java.util.*;

public class Application {

    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        System.out.print(">>");
        String str = in.nextLine();
        AnalysisQuestion anl_qst = new AnalysisQuestion(str);
        /*for (String t :
                anl_qst.clearQ()) {
            System.out.println(t);
        }
        for (String s :
                anl_qst.getF()) {
            System.out.println(s);
        }*/
//        AnalysisInputData anl_data = new AnalysisInputData(str);
//        System.out.println(anl_data.processText());
        anl_qst.findNameQ();
        anl_qst.getElementTree();
        anl_qst.setTagQuestion();
        anl_qst.formatAnswer();
    }

//    private static void createTreeAndFindMeanSentence(String text) {
        //не работает из аннотатора tokenize и др
//        Properties properties = new Properties();
//        properties.setProperty("annotators", "tokenize,ssplit,parse");
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
//        Annotation annotator = new Annotation(text);
//        pipeline.annotate(annotator);
//        pipeline.prettyPrint(annotator, System.out);
//    }

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

    private static void findPathOfSentences(String text) {
        // поиск фрагментов текста
        String tokens[] = text.split("\\s+");
        for (String token :
                tokens) {
            System.out.println(token);
        }
    }

//    private static void findSentences(String text) {
//         поиск предложений
//        StringReader reader = new StringReader(text);
//        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
//        List<String> sentences = new LinkedList<String>();
//        for (List<HasWord> element :
//                dp) {
//            StringBuilder sentence = new StringBuilder();
//            List<HasWord> hasWordList = element;
//            for (HasWord token :
//                    hasWordList) {
//                sentence.append(token).append(" ");
//            }
//            sentences.add(sentence.toString());
//        }
//        for (String sent :
//                sentences) {
//            System.out.println(sent);
//        }
//    }
}
