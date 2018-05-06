package DBModel;

import java.time.Year;

public class Names {

    public String generateBirthYear(String namePerson){
        Query query = new Query();
        query.columnByName("birthyear");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'");
        return query.toString();
    }

    public String generateDeathYear(String namePerson){
        Query query = new Query();
        query.columnByName("deathyear");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'");
        return query.toString();
    }

    public String generateAge(String namePerson) {
        Query query = new Query();
        query.columnByName("deathyear" +
                ", " + Year.now() + "-birthyear as Age");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'");
        return query.toString();
    }

    public String generateTitles(String namePerson) {
        Query query = new Query();
        query.columnByName("knownfortitles");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'");
        return query.toString();
    }

}
