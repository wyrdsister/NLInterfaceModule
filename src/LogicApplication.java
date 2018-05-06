import DBModel.Names;

import java.sql.SQLException;
import java.util.ArrayList;

public class LogicApplication {
    AnalysisQuestion anl_q;
    AnalysisAnswer anl_a;
    AccessDB accessDB;

    LogicApplication() {


    }

    public void start(String questrion) throws SQLException {
        anl_q = new AnalysisQuestion(questrion);
        anl_q.findNameQ();
        anl_q.getElementTree();
        anl_q.setTagQuestion();
        ArrayList<String> res = getAnswerFromDB(getQuery(), new String[]{anl_q.getTagQuestion()});
        anl_a = new AnalysisAnswer(anl_q.getTagQuestion());
        anl_a.formatAnswer();
        System.out.println(getAnswer(res, anl_a.getAnswerFormat()));
    }

    private ArrayList<String> getAnswerFromDB(String query, String[] column) throws SQLException {
        accessDB = new AccessDB();
        return accessDB.startQuery(query, column);

    }

    private String getQuery() {
        if (anl_q.getTagQuestion().equalsIgnoreCase("birthyear")) {
            return new Names().generateBirthYear(anl_q.getSupportWords().get(0));
        }
        if (anl_q.getTagQuestion().equalsIgnoreCase("deathyear")) {
            return new Names().generateDeathYear(anl_q.getSupportWords().get(0));
        }
        return null;
    }

    private String getAnswer(ArrayList<String> result, String answer) {
        answer = answer.replace("NAME", anl_q.getSupportWords().get(0));
        answer = answer.replace("ANSWER", result.get(0));
        return answer;
    }

}
