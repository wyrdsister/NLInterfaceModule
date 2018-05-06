package DBModel;

public class Titles {

    public String generateTitleName(String titleId){
        Query query = new Query();
        query.columnByName("title");
        query.tableByName("title_akas");
        query.paramByName("titleid", "'" + titleId + "'", true);
        query.paramByName("isoriginaltitle", "1", false);
        return query.toString();
    }
}
