import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class AnalysisInputData {

    private String[] questionWords = {"who", "when", "what", "why", "which", "where", "how"};
    private String[] simpleQuestionWords = {"did", "do", "does", "is", "am", "are", "have", "has", "will"};
    private String type;
    private String text;


    AnalysisInputData(String text) {
        this.text = text;
        processText();
    }

    private void processText() {
        ArrayList<String> _text = splitText();
        if (_text.size() == 1) {
            type = defineType(_text.get(0));
            text =  _text.get(0);
        }
        if (_text.size() > 1) {

        } else {

        }

    }

    private String defineType(String first) {
        if (first.equalsIgnoreCase(Arrays.toString(questionWords)))
            return defineTypeQuestionWord(first);
        if (first.equalsIgnoreCase(Arrays.toString(simpleQuestionWords)))
            return defineTypeSimpleQuestion(first);
        return "";
    }

    private String defineTypeSimpleQuestion(String first) {
        return "";
    }

    private String defineTypeQuestionWord(String first) {
        if (first.equalsIgnoreCase(("Who"))) {
            return "who";
        }
        if (first.equalsIgnoreCase(("What"))) {
            return "what";
        }
        return "";

    }


    private ArrayList<String> splitText() {
        Locale currentLocale = new Locale("en", "US");
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(currentLocale);
        sentenceIterator.setText(text);

        int boundary = sentenceIterator.first();
        ArrayList<String> newText = new ArrayList<>();
        while (boundary != BreakIterator.DONE) {
            int begin = boundary;
            boundary = sentenceIterator.next();
            int end = boundary;
            if (end == BreakIterator.DONE)
                break;
            newText.add(text.substring(begin, end));
        }

        return newText;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
