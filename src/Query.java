import java.sql.Connection;

/*
Конструктор запроса
 */
public class Query {

    String query;

    Query(String query){
        this.query = query;
    }

    Query(){
        query = "SELECT column " +
                "FROM table " +
                "WHERE param ";
    }

    void paramByName(String name, String value){
        query = query.replaceAll("param", name + " like " + value);
    }

    void tableByName(String table){
        query = query.replaceAll("table", table);
    }

    void columnByName(String column){
        query = query.replaceAll("column", column);
    }

    public String toString(){
        return query;
    }
}
