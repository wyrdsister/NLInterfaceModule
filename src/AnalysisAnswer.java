import java.util.ArrayList;

public class AnalysisAnswer {


    private String TagQuestion;
    private String AnswerFormat;

    AnalysisAnswer(String tag) {
        this.TagQuestion = tag;
        AnswerFormat = "";
    }

    public void formatAnswer() {

        if (TagQuestion.equalsIgnoreCase("birthyear")) {
            AnswerFormat = "NAME" + " was born in " + "ANSWER" + ".";
        }
        if (TagQuestion.equalsIgnoreCase("deathyear")) {
            AnswerFormat = "NAME" + " was dead in " + "ANSWER" + ".";
        }
        if (TagQuestion.equalsIgnoreCase("age")) {
        }
    }

    public String getAnswerFormat(){
        return AnswerFormat;
    }


}
