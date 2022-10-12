import java.sql.*;

public class Database {
    private Connection conn;
    private String teamNumber;
    private String sectionNumber;
    private String dbName;
    private String dbConnectionString;
    private dbsetup myCredentials;

    public Database() {
        conn = null;
        teamNumber = "65";
        sectionNumber = "905";
        dbName = "csce331_" + sectionNumber + "_" + teamNumber;
        dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        myCredentials = new dbsetup();

        // Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbsetup.user, dbsetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
    }

    public void close() {
        // closing the connection
        try {
            conn.close();
            System.out.println("Connection Closed.");
        } catch (Exception e) {
            System.out.println("Connection NOT Closed.");
        }
    }

    public ResultSet executeQuery(String s) {
        Statement stmt = createStatement();
        ResultSet ans = null;
        try {
            ans = stmt.executeQuery(s);
        } catch (Exception e) {
            System.out.println("Error occured with executing query.");
        }
        return ans;
    }

    public void executeUpdate(String s){
        Statement stmt = createStatement();
        ResultSet ans = null;
        try {
            stmt.executeUpdate(s);
        } catch (Exception e) {
            System.out.println("Error occured with executing query.");
        }
    }

    public Statement createStatement() {
        Statement temp = null;
        try {
            temp = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println("Error occured while creating statement.");
        }
        return temp;
    }

    public void updateInventory(String ingredients) {
        ResultSet inv = executeQuery("SELECT * FROM inventory ORDER BY item_id");
        String[] ings = ingredients.split(",");
        String query = "UPDATE inventory SET itemcount = ";
        String query2 = " WHERE item_id=";
        int nextCount;
        for(String s:ings){
            try{
                switch(s){
                    case "bun":
                        inv.absolute(1);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 1);
                        break;
                    case "txt":
                        inv.absolute(2);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 2);
                        break;
                    case "pty":
                        inv.absolute(3);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 3);
                        break;
                    case "bbp":
                        inv.absolute(4);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 4);
                        break;
                    case "bcn":
                        inv.absolute(5);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 5);
                        break;
                    

                }
            }catch(Exception e){}
        }
    }

}
