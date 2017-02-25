package db;

public class Database {
    public Database() {
        // YOUR CODE HERE
    }

    public String transact(String query) {
        String[] strarr = new String[1];
        strarr[0] = query;
        Parse.main(strarr);
        return "";
    }
}
