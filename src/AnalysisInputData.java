import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AnalysisInputData {

    String[] questionWords = {"who", "when", "what", "why", "which", "where", "how"};
    String[] simpleQuestionWords = {"did", "do", "does", "is", "am", "are", "have", "has", "will"};
    String text;
    enum type {type1, type2, type3};

    AnalysisInputData(String text) {
        this.text = text;
    }

    public String processText() {
        ArrayList<String> _text = splitText();
        if (_text.size() == 1) {
            type _t = defineType(_text.get(0));
            return _text.get(0);
        }
        if (_text.size() > 1) {

        } else {

        }
        return null;
    }

    private type defineType(String question){
        if(question.equals(questionWords)){
            return type.type1;
        }if(question.equals(simpleQuestionWords)){
            return type.type2;
        }else {
            return type.type3;
        }
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

}
