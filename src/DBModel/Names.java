package DBModel;

import java.time.Year;

/*
* Запросы, связанные с личностями.
* */

public class Names {

    public String generateBirthYear(String namePerson){
        Query query = new Query();
        query.columnByName("birthyear");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'", true);
        return query.toString();
    }

    public String generateDeathYear(String namePerson){
        Query query = new Query();
        query.columnByName("deathyear");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'", true);
        return query.toString();
    }

    public String generateAge(String namePerson) {
        Query query = new Query();
        query.columnByName("deathyear" +
                ", " + Year.now() + "-birthyear as age");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'", true);
        return query.toString();
    }

    /*выводит id фильмов*/

    public String generateTitlesOfPerson(String namePerson) {
        Query query = new Query();
        query.columnByName("knownfortitles");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'", true);
        return query.toString();
    }

    public String professionOfName(String namePerson){
        Query query = new Query("");
        query.columnByName("primaryprofession");
        query.tableByName("names_basics");
        query.paramByName("primaryname", "'" + namePerson + "'", true);
        return query.toString();
    }

}
