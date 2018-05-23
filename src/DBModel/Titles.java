package DBModel;

public class Titles {

    public String generateTitleNameEN(String titleId){
        Query query = new Query();
        query.columnByName("title");
        query.tableByName("title_akas");
        query.paramByName("titleid", "'" + titleId + "'", true);
        query.paramByName("isoriginaltitle", "1", false);
        return query.toString();
    }

    public String generateTitleGenres(String titleName){
        Query query = new Query();
        query.columnByName("genres");
        query.tableByName("title_bacisc");
        query.paramByName("primarytitle", "'" + titleName + "'", true);
        return query.toString();
    }

    public String generateTitleYearCreate(String titleName){
        Query query = new Query();
        query.columnByName("startyear");
        query.tableByName("title_bacisc");
        query.paramByName("primarytitle", "'" + titleName + "'", true);
        return query.toString();
    }

    public String generateTitleRunTime(String titleName){
        Query query = new Query();
        query.columnByName("runtimeminutes");
        query.tableByName("title_bacisc");
        query.paramByName("primarytitle", "'" + titleName + "'", true);
        return query.toString();
    }

    public String generateTitleRaitings(String titleId){
        Query query = new Query();
        query.columnByName("averagerating");
        query.tableByName("title_ratings");
        query.paramByName("tconst", "'" + titleId + "'", true);
        return query.toString();
    }




}
