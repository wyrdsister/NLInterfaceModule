import DBModel.Names;

import java.sql.SQLException;
import java.util.ArrayList;

public class LogicApplication {
    AnalysisQuestion anl_q;
    AnalysisAnswer anl_a;
    AccessDB accessDB;

    /*Типы ответов
    * LIST_PERSONS - список чего-либо
    * DATE         - год или дата
    * NAME         - название
    * COUNT        - количество чего-либо*/
    enum AnswerType {LIST, DATE, NAME, COUNT};

    /*Типы вопросов
    * */


    LogicApplication() {


    }

    public void start(String questrion) throws SQLException {
        anl_q = new AnalysisQuestion(questrion);
        anl_q.analysis();

//        ArrayList<String> res = getAnswerFromDB(getQuery(), anl_q.getTagQuestion());
//        anl_a = new AnalysisAnswer(anl_q.getTagQuestion());
//        anl_a.formatAnswer();
//        Map<String, String> result = new HashMap<>();
//        result.put("NAME", anl_q.getSupportWords().get(0));
//        result.put("ANSWER", res.get(0));
//        System.out.println(anl_a.getAnswer(result));
    }

    private ArrayList<String> getAnswerFromDB(String query, String column) throws SQLException {
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
        if (anl_q.getTagQuestion().equalsIgnoreCase("age")) {
            return new Names().generateAge(anl_q.getSupportWords().get(0));
        }
        return null;
    }



}
