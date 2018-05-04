public class GeneratorQuery {

    String generateQueryBirthYear(String namePerson) {
        Query query = new Query();
        query.columnByName("birthyear");
        query.tableByName("names_basics");
        query.paramByName("primaryname", namePerson);
        return query.toString();
    }

}
