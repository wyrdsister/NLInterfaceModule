import java.util.ArrayList;
import java.util.Map;

public class AnalysisAnswer {


    private String TagQuestion;
    private String AnswerFormat;
    private ArrayList<String> fields;

    AnalysisAnswer(String tag) {
        this.TagQuestion = tag;
        AnswerFormat = "";
        fields = new ArrayList<>();
    }

    public void formatAnswer() {

        if (TagQuestion.equalsIgnoreCase("birthyear")) {
            AnswerFormat = "NAME" + " was born in " + "ANSWER" + ".";
            fields.add("NAME");
            fields.add("ANSWER");
        }
        if (TagQuestion.equalsIgnoreCase("deathyear")) {
            AnswerFormat = "NAME" + " was dead in " + "ANSWER" + ".";
            fields.add("NAME");
            fields.add("ANSWER");
        }
        if (TagQuestion.equalsIgnoreCase("age")) {
            AnswerFormat = "NAME" + " is " + "ANSWER" + ".";
            fields.add("NAME");
            fields.add("ANSWER");
        }
    }

    public String getAnswerFormat(){
        return AnswerFormat;
    }

    public String getAnswer(Map<String, String> result) {
        String answer = AnswerFormat;
        for (String field : fields){
            answer = answer.replaceAll(field, result.get(field));
        }
        return answer.equals(AnswerFormat) ? "Not answer" : answer;
    }

}
