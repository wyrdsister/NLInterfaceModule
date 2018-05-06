package DBModel;

import java.sql.Connection;

/*
Конструктор запроса
 */
public class Query {

    String query;

    Query(String query) {
        this.query = query;
    }

    Query() {
        query = "SELECT column " +
                "FROM table " +
                "WHERE param ";
    }

    void paramByName(String name, String value, boolean isText) {
        if(query.contains("param")) {
            if (isText)
                query = query.replaceAll("param", name + " like " + value);
            else
                query = query.replaceAll("param", name + "=" + value);
        } else {
            if (isText)
                query += " AND "+ name + " like " + value;
            else
                query += " AND " + name + "=" + value;
        }
    }

    void tableByName(String table) {
        query = query.replaceAll("table", table);
    }

    void columnByName(String column) {
        query = query.replaceAll("column", column);
    }

    public String toString() {
        return query;
    }
}
